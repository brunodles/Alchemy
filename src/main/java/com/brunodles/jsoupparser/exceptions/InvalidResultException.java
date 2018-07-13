package com.brunodles.jsoupparser.exceptions;

/**
 * This exception is thrown when method result is void
 */
public class InvalidResultException extends RuntimeException {

    public InvalidResultException(String methodName) {
        super("Failed to get \"" + methodName + "\". The method have void return.");
    }
}
