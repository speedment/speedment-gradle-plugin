/**
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.gradle.utils;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;

public class ComponentConstructorsProviderTest {

    private Project testProject;

    @Before
    public void prepareTestProject() {
        testProject = ProjectBuilder.builder().build();
    }
//
//    @Test
//    public void testValidComponentConstructor() {
//        ComponentConstructor<Component> validComponentConstructor = speedment -> null;
//        PluginUtils.setExtraProperty(testProject, COMPONENT_CONSTRUCTORS_PROPERTY, ImmutableList.of(validComponentConstructor));
//
//        ComponentConstructorsProvider provider = ComponentConstructorsProvider.create(testProject);
//        Assert.assertEquals(1, provider.getComponentConstructors().size());
//    }
//
//    @Test
//    public void testInvalidComponentConstructor() {
//        Object invalidComponentConstructor = new Object();
//        PluginUtils.setExtraProperty(testProject, COMPONENT_CONSTRUCTORS_PROPERTY, ImmutableList.of(invalidComponentConstructor));
//
//        ComponentConstructorsProvider provider = ComponentConstructorsProvider.create(testProject);
//        Assert.assertEquals(0, provider.getComponentConstructors().size());
//    }
//
//    @Test
//    public void testInvalidComponentConstructorParameter() {
//        Object invalidComponentConstructorParameter = new Object();
//        PluginUtils.setExtraProperty(testProject, COMPONENT_CONSTRUCTORS_PROPERTY, invalidComponentConstructorParameter);
//
//        ComponentConstructorsProvider provider = ComponentConstructorsProvider.create(testProject);
//        Assert.assertEquals(0, provider.getComponentConstructors().size());
//    }
}
