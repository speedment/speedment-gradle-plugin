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

import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtils {

    private static final String TARGET_DIRECTORY_CONFIG_PLACEHOLDER = "<do_not_change_this_will_replaced_by_test>";
    private static final String TEMP_DIRECTORY_PREFIX = "speedment_generation_test";
    private static final String CONFIG_FILE_PATTERN_NAME = "config_pattern.json";
    private static final String TEMP_CONFIG_FILE_PREFIX = "speedment_test_config";
    private static final String TEMP_CONFIG_FILE_SUFFIX = ".json";

    public static File createTempDirectory() {
        try {
            return Files.createTempDirectory(TEMP_DIRECTORY_PREFIX).toFile();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static File createTempConfigFile(File targetDirectory) {
        URL url = TestUtils.class.getClassLoader().getResource(CONFIG_FILE_PATTERN_NAME);
        Assert.assertNotNull(url);

        File configFile;
        try {
            configFile = Files.createTempFile(TEMP_CONFIG_FILE_PREFIX, TEMP_CONFIG_FILE_SUFFIX).toFile();
            Stream<String> patternLines = Files.lines(Paths.get(url.getPath()));
            List<String> lines = patternLines.map(l -> {
                if (l.contains(TARGET_DIRECTORY_CONFIG_PLACEHOLDER)) {
                    return l.replace(TARGET_DIRECTORY_CONFIG_PLACEHOLDER, targetDirectory.getAbsolutePath());
                }
                return l;
            }).collect(Collectors.toList());
            Files.write(configFile.toPath(), lines);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return configFile;
    }
}
