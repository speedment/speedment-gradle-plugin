package com.speedment.gradle.utils;

/**
 * Utility class used when working with strings.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class StringUtils {

    public static boolean isBlank(String in) {
        return in == null || in.isEmpty();
    }

    public static boolean isNotBlank(String in) {
        return !isBlank(in);
    }

    private StringUtils() {}
}
