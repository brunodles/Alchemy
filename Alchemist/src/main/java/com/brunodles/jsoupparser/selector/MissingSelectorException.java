package com.brunodles.jsoupparser.selector;

/**
 * This exception is thrown when field is not annotated.
 */
public class MissingSelectorException extends RuntimeException {

    private static final String MESSAGE = "Failed to get \"%s\". Looks like it doesn't have Selector annotation.";

    public MissingSelectorException(String methodName) {
        super(String.format(MESSAGE, methodName));
    }
}
