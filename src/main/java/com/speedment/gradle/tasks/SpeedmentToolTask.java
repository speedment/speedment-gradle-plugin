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

import com.speedment.common.injector.Injector;
import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.MainApp;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sergio Figueras (sergio@yourecm.com)
 * @author Emil Forslund
 */
public class SpeedmentToolTask extends AbstractSpeedmentTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedmentToolTask.class);
    public static final String SPEEDMENT_TOOL_TASK_NAME = "speedment.tool";

    @Override
    protected void execute(Speedment speedment) {
        final Injector injector = speedment.getOrThrow(Injector.class);
        MainApp.setInjector(injector);

        if (hasConfigFile()) {
            Application.launch(MainApp.class, configLocation().toAbsolutePath().toString());
        } else {
            Application.launch(MainApp.class);
        }
    }

    @Override
    public String getDescription() {
        return "Opens Speedment Tool.";
    }
}