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

import com.speedment.generator.translator.TranslatorManager;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.ProjectComponent;
import org.gradle.api.GradleScriptException;

/**
 * @author Sergio Figueras (sergio@yourecm.com)
 * @author Emil Forslund
 */
public class SpeedmentGenerateTask extends AbstractSpeedmentTask {

    public static final String SPEEDMENT_GENERATE_TASK_NAME = "speedment.Generate";

    @Override
    protected void execute(Speedment speedment) {
        getLogger().info("Generating code using JSON configuration file: '{}'.",
            configLocation().toAbsolutePath());

        assertHasConfigFile();
        try {
            final Project project = speedment.getOrThrow(ProjectComponent.class).getProject();
            speedment.getOrThrow(TranslatorManager.class).accept(project);
            // TODO: Check if the generated sources needs to be added somewhere for later tasks to find them
        } catch (final Exception ex) {
            final String err = "Error parsing configFile file.";
            getLogger().error(err);
            throw new GradleScriptException(err, ex);
        }
    }

    @Override
    public String getDescription() {
        return "Generates code.";
    }
}
