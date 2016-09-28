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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.speedment.gradle.utils.ConfigFileProvider.CONFIG_PATH_PROPERTY;
import static com.speedment.internal.ui.UISession.DEFAULT_CONFIG_LOCATION;

public class ConfigFileProviderTest {

    private Project testProject;

    @Before
    public void prepareTestProject() {
        testProject = ProjectBuilder.builder().build();
    }

    @Test
    public void testDefaultConfigPath() {
        ConfigFileProvider config = ConfigFileProvider.create(testProject);
        Assert.assertEquals(config.getPath(), DEFAULT_CONFIG_LOCATION);
    }

    @Test
    public void testCustomConfigPath() {
        String customConfigPath = "/a/b/c/config.json";
        PluginUtils.setExtraProperty(testProject, CONFIG_PATH_PROPERTY, customConfigPath);

        ConfigFileProvider config = ConfigFileProvider.create(testProject);
        Assert.assertEquals(customConfigPath, config.getPath());
    }
}
