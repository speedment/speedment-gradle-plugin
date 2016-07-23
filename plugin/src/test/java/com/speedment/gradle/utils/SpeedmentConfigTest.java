package com.speedment.gradle.utils;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.speedment.gradle.utils.SpeedmentConfig.CONFIG_PATH_PROPERTY;
import static com.speedment.internal.ui.UISession.DEFAULT_CONFIG_LOCATION;

public class SpeedmentConfigTest {

    private Project testProject;

    @Before
    public void prepareTestProject() {
        testProject = ProjectBuilder.builder().withName("testProject").build();
    }

    @Test
    public void testDefaultConfigPath() {
        SpeedmentConfig config = SpeedmentConfig.create(testProject);
        Assert.assertEquals(config.getPath(), DEFAULT_CONFIG_LOCATION);
    }

    @Test
    public void testCustomConfigPath() {
        String customConfigPath = "/a/b/c/config.json";
        PluginUtils.setExtraProperty(testProject, CONFIG_PATH_PROPERTY, customConfigPath);

        SpeedmentConfig config = SpeedmentConfig.create(testProject);
        Assert.assertEquals(customConfigPath, config.getPath());
    }
}
