package com.brunodles.jsoupparser.exceptions;

public class ResultException extends RuntimeException {
    public ResultException(String methodName, String returnClassName, Throwable cause) {
        super("Failed to get \"" + methodName + "\". Can't create an instance of \"" + returnClassName + "\".", cause);
    }
}
