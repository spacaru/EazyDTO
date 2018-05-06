package com.norberth.config;

/**
 * @author Novanc Norberth
 * @version 1.0.0
 * Utility class for grouping certain string settings
 */
public abstract class ConverterConfig {

    private static String ESCAPED_NAME_DELIMITER = "\\.";
    private static String NAME_DELIMITER = ".";
    private static final String SEPARATOR = " ";

    public static String getEscapedNameDelimiter() {
        return ESCAPED_NAME_DELIMITER;
    }

    public static String getNameDelimiter() {
        return NAME_DELIMITER;
    }

    public static String getSEPARATOR() {
        return SEPARATOR;
    }

}
