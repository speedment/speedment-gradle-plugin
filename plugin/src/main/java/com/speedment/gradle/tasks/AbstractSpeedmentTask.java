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

import org.gradle.api.DefaultTask;

import java.io.File;

/**
 *
 * @author Sergio Figueras (sergio@yourecm.com)
 */
public abstract class AbstractSpeedmentTask extends DefaultTask {

    protected abstract File getGroovyLocation();

    protected abstract void javaTask();

    protected final boolean hasGroovyFile() {
        if (getGroovyLocation() == null) {
            final String err = "Specified .groovy-file is null.";
            getLogger().info(err);
            return false;
        } else if (!getGroovyLocation().exists()) {
            final String err = "The specified groovy-file '" + getGroovyLocation().getAbsolutePath() + "' does not exist.";
            getLogger().info(err);
            return false;
        } else if (!getGroovyLocation().canRead()) {
            final String err = "The specified groovy-file '" + getGroovyLocation().getAbsolutePath() + "' is not readable.";
            getLogger().info(err);
            return false;
        } else return true;
    }

}
