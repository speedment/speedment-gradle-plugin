package com.speedment.gradle.tasks;

import com.speedment.gradle.plugins.SpeedmentPlugin;
import com.speedment.gradle.utils.SpeedmentConfig;
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
        SpeedmentConfig config = new SpeedmentConfig(configFile.getAbsolutePath());

        task.generateCode(config);

        Assert.assertTrue(targetDirectory.exists());
        Assert.assertTrue(new File(targetDirectory, "com").exists());
    }

    private SpeedmentGenerateTask getTask(Project project) {
        Set<Task> tasks = project.getTasksByName(SpeedmentGenerateTask.SPEEDMENT_GENERATE_TASK_NAME, false);
        Optional<Task> taskOptional = tasks.stream().findAny();
        return (SpeedmentGenerateTask) taskOptional.orElse(null);
    }
}
