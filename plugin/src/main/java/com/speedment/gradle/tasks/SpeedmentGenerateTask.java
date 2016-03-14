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
package com.speedment.gradle.tasks;

import com.speedment.internal.core.platform.SpeedmentFactory;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import com.speedment.Speedment;
import com.speedment.config.Project;
import com.speedment.internal.core.code.MainGenerator;
import com.speedment.internal.core.config.utils.GroovyParser;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Sergio Figueras (sergio@yourecm.com)
 */
public class SpeedmentGenerateTask extends AbstractSpeedmentTask {

    @Input
    protected String groovyFile = com.speedment.internal.ui.UISession.DEFAULT_GROOVY_LOCATION;

    @TaskAction
    protected void javaTask(){
        Speedment speedment = SpeedmentFactory.newSpeedmentInstance();
        if (hasGroovyFile()) {
            try {
                final Project p = GroovyParser.projectFromGroovy(speedment, getGroovyLocation().toPath());
                new MainGenerator(speedment).accept(p);
            } catch (IOException ex) {
                getLogger().error("IOException thrown when parsing Groovy-file.");
            }
        } else {
            getLogger().error("To run speedment:generate a valid .groovy-file need to be specified.");
        }
    }

    @Override
    protected File getGroovyLocation() {
        return new File(groovyFile);
    }

    @Override
    public String getDescription() {
        return "Runs Speedment generate.";
    }
}
