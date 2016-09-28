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
package com.speedment.gradle.tasks;

import com.speedment.gradle.plugins.SpeedmentPlugin;
import com.speedment.gradle.utils.ComponentConstructorsProvider;
import com.speedment.gradle.utils.ConfigFileProvider;
import com.speedment.gradle.utils.TestUtils;
import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class SpeedmentGenerateTest {

    private Project testProject;
    private File targetDirectory;
    private File configFile;

    @Before
    public void prepareTestProject() {
        testProject = ProjectBuilder.builder().build();
        new SpeedmentPlugin().apply(testProject);

        targetDirectory = TestUtils.createTempDirectory();
        configFile = TestUtils.createTempConfigFile(targetDirectory);
    }

    @After
    public void cleanTestProject() {
        try {
            FileUtils.forceDelete(configFile);
            FileUtils.forceDelete(targetDirectory);
        } catch (IOException ignored) {
        }
    }

    @Test
    public void generateCodeTest() {
        SpeedmentGenerateTask task = getTask(testProject);
        ConfigFileProvider config = new ConfigFileProvider(configFile.getAbsolutePath());
        ComponentConstructorsProvider componentConstructors = new ComponentConstructorsProvider(Collections.emptyList());

        task.generateCode(config, componentConstructors);

        Assert.assertTrue(targetDirectory.exists());
        Assert.assertTrue(new File(targetDirectory, "com").exists());
    }

    private SpeedmentGenerateTask getTask(Project project) {
        Set<Task> tasks = project.getTasksByName(SpeedmentGenerateTask.SPEEDMENT_GENERATE_TASK_NAME, false);
        Optional<Task> taskOptional = tasks.stream().findAny();
        return (SpeedmentGenerateTask) taskOptional.orElse(null);
    }
}
