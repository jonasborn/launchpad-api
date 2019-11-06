package de.jonasborn.launchpad.api.util

import java.util.regex.Matcher
import java.util.regex.Pattern

class PathUtils {


    public static Pattern wildcardToRegex(String input) {
        input = input.replace("/", "\\/")
        input = input.replace("*", ".+")
        return Pattern.compile(input)
    }

    public static boolean matches(String path, String part) {
        def p = wildcardToRegex(part)
        return path.replaceAll(p, "").length() < 1
    }
}
