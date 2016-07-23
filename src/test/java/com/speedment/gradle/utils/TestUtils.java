package com.speedment.gradle.utils;

import org.apache.commons.lang.Validate;

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
        Validate.notNull(url);

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
