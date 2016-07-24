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

import com.speedment.component.ComponentConstructor;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentConstructorsProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentConstructorsProvider.class);
    public static final String COMPONENT_CONSTRUCTORS_PROPERTY = "speedmentComponentConstructors";

    private final List<ComponentConstructor<?>> componentConstructors;

    public ComponentConstructorsProvider(List<ComponentConstructor<?>> componentConstructors) {
        this.componentConstructors = componentConstructors;
    }

    public static ComponentConstructorsProvider create(Project project) {
        List<?> constructors = PluginUtils.getExtraProperty(project, COMPONENT_CONSTRUCTORS_PROPERTY, Collections.emptyList(), List.class);
        List<ComponentConstructor<?>> sanitizedConstructors = new ArrayList<>(constructors.size());
        constructors.forEach(constructor -> {
            if (constructor instanceof ComponentConstructor) {
                sanitizedConstructors.add((ComponentConstructor) constructor);
            } else {
                LOGGER.warn("Class {} is not valid component constructor! It's going to be ignored!", constructor.getClass().getName());
            }
        });
        return new ComponentConstructorsProvider(sanitizedConstructors);
    }

    public List<ComponentConstructor<?>> getComponentConstructors() {
        return componentConstructors;
    }

    @Override
    public String toString() {
        return "[" + String.join(", ", componentConstructors.stream().map(cc -> cc.getClass().getName()).collect(Collectors.toList())) + "]";
    }
}
