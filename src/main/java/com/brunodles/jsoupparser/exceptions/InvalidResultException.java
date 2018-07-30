package com.brunodles.jsoupparser.exceptions;

/**
 * This exception is thrown when method result is void.
 */
public class InvalidResultException extends RuntimeException {

    private static final String MESSAGE = "Failed to get \"%s\". The method have void return.";

    public InvalidResultException(String methodName) {
        super(String.format(MESSAGE, methodName));
    }
}
