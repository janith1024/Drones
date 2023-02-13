package com.janith1024.util;

public class StringUtil {

    public static boolean allowedOnlyLettersNumbersDashUnderscore(final String input) {
        return input != null && input.matches("^[a-zA-Z0-9-_]*$");
    }

    public static boolean allowedOnlyUpperLettersNumbersUnderscore(final String input) {
        return input != null && input.matches("^[A-Z0-9_]*$");
    }
}
