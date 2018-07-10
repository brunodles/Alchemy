package com.brunodles.jsoupparser.exceptions;

/**
 * This exception is thrown when field is not annotated with {@link com.brunodles.jsoupparser.CssSelector}
 */
public class MissingSelectorException extends RuntimeException {
    public MissingSelectorException(String methodName) {
        super("Failed to get \"" + methodName + "\". Looks like it doesn't have CssSelector annotation.");
    }
}
