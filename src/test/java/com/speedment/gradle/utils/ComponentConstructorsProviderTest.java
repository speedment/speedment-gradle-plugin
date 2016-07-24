package com.speedment.gradle.utils;

import com.google.common.collect.ImmutableList;
import com.speedment.component.Component;
import com.speedment.component.ComponentConstructor;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.speedment.gradle.utils.ComponentConstructorsProvider.COMPONENT_CONSTRUCTORS_PROPERTY;

public class ComponentConstructorsProviderTest {

    private Project testProject;

    @Before
    public void prepareTestProject() {
        testProject = ProjectBuilder.builder().build();
    }

    @Test
    public void testValidComponentConstructor() {
        ComponentConstructor<Component> validComponentConstructor = speedment -> null;
        PluginUtils.setExtraProperty(testProject, COMPONENT_CONSTRUCTORS_PROPERTY, ImmutableList.of(validComponentConstructor));

        ComponentConstructorsProvider provider = ComponentConstructorsProvider.create(testProject);
        Assert.assertEquals(1, provider.getComponentConstructors().size());
    }

    @Test
    public void testInvalidComponentConstructor() {
        Object invalidComponentConstructor = new Object();
        PluginUtils.setExtraProperty(testProject, COMPONENT_CONSTRUCTORS_PROPERTY, ImmutableList.of(invalidComponentConstructor));

        ComponentConstructorsProvider provider = ComponentConstructorsProvider.create(testProject);
        Assert.assertEquals(0, provider.getComponentConstructors().size());
    }

    @Test
    public void testInvalidComponentConstructorParameter() {
        Object invalidComponentConstructorParameter = new Object();
        PluginUtils.setExtraProperty(testProject, COMPONENT_CONSTRUCTORS_PROPERTY, invalidComponentConstructorParameter);

        ComponentConstructorsProvider provider = ComponentConstructorsProvider.create(testProject);
        Assert.assertEquals(0, provider.getComponentConstructors().size());
    }
}
