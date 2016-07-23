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
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginUtils.class);

    public static String getExtraProperty(Project project, String propertyName, String defaultValue) {
        LOGGER.debug("Getting extra property " + propertyName + " from project " + project.getName());
        ExtraPropertiesExtension ext = project.getExtensions().getExtraProperties();
        if (ext != null && ext.has(propertyName)) {
            Object property = ext.get(propertyName);
            if (property != null) {
                if (property instanceof String) {
                    return (String) property;
                } else {
                    LOGGER.warn("Extra property " + propertyName + " is not string. It's value will be ignored!");
                }
            } else {
                LOGGER.debug("Extra property " + propertyName + " is null.");
            }
        } else {
            LOGGER.debug("Extra property " + propertyName + " is not set.");
        }
        return defaultValue;
    }

    public static void setExtraProperty(Project project, String propertyName, String propertyValue) {
        LOGGER.debug("Setting extra property " + propertyName + "=" + propertyValue + " to project " + project.getName());
        ExtraPropertiesExtension ext = project.getExtensions().getExtraProperties();
        if (ext != null) {
            if (ext.has(propertyName)) {
                LOGGER.warn("Extra property " + propertyName + " is already set it will be overwritten.");
            }

            ext.set(propertyName, propertyValue);
        } else {
            LOGGER.debug("Extra properties in project " + project.getName() + " are not available.");
        }
    }
}
