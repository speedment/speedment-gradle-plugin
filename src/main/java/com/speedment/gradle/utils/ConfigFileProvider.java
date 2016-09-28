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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

import static com.speedment.internal.ui.UISession.DEFAULT_CONFIG_LOCATION;

public class ConfigFileProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFileProvider.class);
    public static final String CONFIG_PATH_PROPERTY = "speedmentConfigFile";

    private final File configFile;

    public ConfigFileProvider(String configPath) {
        this.configFile = new File(configPath);
    }

    public static ConfigFileProvider create(Project project) {
        String configPath = PluginUtils.getExtraProperty(project, CONFIG_PATH_PROPERTY, DEFAULT_CONFIG_LOCATION, String.class);
        return new ConfigFileProvider(configPath);
    }

    public boolean canAccess() {
        if (!configFile.exists()) {
            LOGGER.info("Config file {} does not exists!", configFile.getAbsolutePath());
            return false;
        } else if (!configFile.canRead()) {
            LOGGER.info("Config file {} is not readable!", configFile.getAbsolutePath());
            return false;
        }
        return true;
    }

    public String getPath() {
        return configFile.getPath();
    }

    public String getAbsolutePath() {
        return configFile.getAbsolutePath();
    }

    public Path toPath() {
        return configFile.toPath();
    }

    public File getAccessibleFileOrNull() {
        if (canAccess()) {
            return configFile;
        }
        return null;
    }

    @Override
    public String toString() {
        return configFile.getAbsolutePath();
    }
}
