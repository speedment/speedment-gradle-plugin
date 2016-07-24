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
