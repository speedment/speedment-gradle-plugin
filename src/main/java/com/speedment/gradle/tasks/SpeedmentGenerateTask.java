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

import com.speedment.gradle.utils.ComponentConstructorsProvider;
import com.speedment.gradle.utils.ConfigFileProvider;
import com.speedment.gradle.utils.SpeedmentInitializer;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.internal.immutable.ImmutableProject;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.Speedment;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sergio Figueras (sergio@yourecm.com)
 */
public class SpeedmentGenerateTask extends DefaultTask {
//
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedmentGenerateTask.class);
    public static final String SPEEDMENT_GENERATE_TASK_NAME = "speedment.Generate";

    @TaskAction
    public void validateConfigAndGenerateCode() {
        ConfigFileProvider config = ConfigFileProvider.create(getProject());
        ComponentConstructorsProvider componentConstructors = ComponentConstructorsProvider.create(getProject());
        LOGGER.info("Generating code using {} config file and {} component constructors.", config, componentConstructors);

        if (config.canAccess()) {
            generateCode(config, componentConstructors);
        } else {
            String message = String.format("To run %s task a valid config file has to be specified! File %s is not valid!", SPEEDMENT_GENERATE_TASK_NAME, config);
            throw new IllegalArgumentException(message);
        }
    }

    void generateCode(ConfigFileProvider config, ComponentConstructorsProvider componentConstructors) {
        Speedment speedment = SpeedmentInitializer.initialize(config, componentConstructors);

        final Project speedmentProject = DocumentTranscoder.load(config.toPath());
        final Project immutableProject = ImmutableProject.wrap(speedmentProject);
        speedment.getProjectComponent().setProject(immutableProject);
        speedment.getCodeGenerationComponent().getTranslatorManager().accept(immutableProject);
    }

    @Override
    public String getDescription() {
        return "Generates code.";
    }
}
