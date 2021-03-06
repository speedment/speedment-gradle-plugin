/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.gradle.plugins;

import com.speedment.gradle.tasks.SpeedmentGenerateTask;
import com.speedment.gradle.tasks.SpeedmentGuiTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 *
 * @author Sergio Figueras (sergio@yourecm.com)
 */
public class SpeedmentPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        target.getTasks().create(SpeedmentGuiTask.SPEEDMENT_GUI_TASK_NAME, SpeedmentGuiTask.class);
        target.getTasks().create(SpeedmentGenerateTask.SPEEDMENT_GENERATE_TASK_NAME, SpeedmentGenerateTask.class);
    }

}