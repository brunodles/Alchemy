package com.brunodles.alchemist.exceptions;

public class ResultException extends RuntimeException {

    private static final String MESSAGE_VOID_RETURN = "Called method should not have void as return.";
    private static final String MESSAGE_CANT_CREATE = "Can't create an instance of \"%s\".";

    private ResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ResultException voidReturn() {
        return new ResultException(MESSAGE_VOID_RETURN, null);
    }

    public static ResultException cantCreate(String returnClassName, Throwable cause) {
        return new ResultException(String.format(MESSAGE_CANT_CREATE, returnClassName), cause);
    }
}
