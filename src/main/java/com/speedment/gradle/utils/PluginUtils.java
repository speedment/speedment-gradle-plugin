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

    @SuppressWarnings("unchecked")
    public static <T> T getExtraProperty(Project project, String name, T value, Class<T> returnType) {
        LOGGER.debug("Getting extra property " + name + " from project " + project.getName());
        ExtraPropertiesExtension ext = project.getExtensions().getExtraProperties();
        if (ext != null && ext.has(name)) {
            Object property = ext.get(name);
            if (property == null) {
                LOGGER.debug("Extra property {} is null.", name);
            } else if (!returnType.isInstance(property)) {
                LOGGER.warn("Extra property {} is not {}. It's value will be ignored!", name, returnType.getName());
            } else {
                return (T) property;
            }
        } else {
            LOGGER.debug("Extra property {} is not set.", name);
        }
        return value;
    }

    public static void setExtraProperty(Project project, String name, Object value) {
        LOGGER.debug("Setting extra property {}={} to project {}", name, value.toString(), project.getName());
        ExtraPropertiesExtension ext = project.getExtensions().getExtraProperties();
        if (ext != null) {
            if (ext.has(name)) {
                LOGGER.warn("Extra property {} is already set it will be overwritten.", name);
            }
            ext.set(name, value);
        } else {
            LOGGER.debug("Extra properties in project {} are not available.", project.getName());
        }
    }
}
