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


import com.speedment.runtime.core.Speedment;

public class SpeedmentInitializer {

    public static Speedment initialize(ConfigFileProvider config, ComponentConstructorsProvider componentConstructorsProvider) {



        final DefaultSpeedmentApplicationLifecycle lifecycle = new DefaultSpeedmentApplicationLifecycle(config.getAccessibleFileOrNull());
        componentConstructorsProvider.getComponentConstructors().forEach(lifecycle::with);
        return lifecycle.build();
    }
}
