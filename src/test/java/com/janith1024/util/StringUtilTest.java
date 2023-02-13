package com.janith1024.util;

import junit.framework.TestCase;

public class StringUtilTest extends TestCase {

    public void testAllowedOnlyLettersNumbersDashUnderscore() {
        assertTrue(StringUtil.allowedOnlyLettersNumbersDashUnderscore("null"));
        assertTrue(StringUtil.allowedOnlyLettersNumbersDashUnderscore("null-ert"));
        assertTrue(StringUtil.allowedOnlyLettersNumbersDashUnderscore("naAull-ert_234"));
        assertFalse(StringUtil.allowedOnlyLettersNumbersDashUnderscore("naAull-ert_234."));
    }

    public void testAllowedOnlyUpperLettersNumbersUnderscore() {
        assertTrue(StringUtil.allowedOnlyUpperLettersNumbersUnderscore("NULL_123"));
        assertTrue(StringUtil.allowedOnlyUpperLettersNumbersUnderscore("NUL234_"));
        assertTrue(StringUtil.allowedOnlyUpperLettersNumbersUnderscore("123_5"));
        assertTrue(StringUtil.allowedOnlyUpperLettersNumbersUnderscore("AN_RJ_56"));
        assertFalse(StringUtil.allowedOnlyUpperLettersNumbersUnderscore("ASDFdgsgf_123"));
        assertFalse(StringUtil.allowedOnlyUpperLettersNumbersUnderscore("?EF"));
    }
}