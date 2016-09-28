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

import com.speedment.Speedment;
import com.speedment.gradle.utils.ComponentConstructorsProvider;
import com.speedment.gradle.utils.ConfigFileProvider;
import com.speedment.gradle.utils.SpeedmentInitializer;
import com.speedment.internal.ui.MainApp;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javafx.application.Application.launch;

/**
 * @author Sergio Figueras (sergio@yourecm.com)
 */
public class SpeedmentGuiTask extends DefaultTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedmentGuiTask.class);
    public static final String SPEEDMENT_GUI_TASK_NAME = "speedment.Gui";

    @TaskAction
    protected void openGui() {
        ConfigFileProvider config = ConfigFileProvider.create(getProject());
        ComponentConstructorsProvider componentConstructors = ComponentConstructorsProvider.create(getProject());
        LOGGER.info("Starting GUI using {} config file and {} component constructors.", config, componentConstructors);

        Speedment speedment = SpeedmentInitializer.initialize(config, componentConstructors);

        MainApp.setSpeedment(speedment);
        if (config.canAccess()) {
            launch(MainApp.class, config.getAbsolutePath());
        } else {
            launch(MainApp.class);
        }
    }

    @Override
    public String getDescription() {
        return "Opens Speedment GUI.";
    }
}
