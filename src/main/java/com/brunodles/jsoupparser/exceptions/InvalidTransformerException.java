package com.brunodles.jsoupparser.exceptions;

import com.brunodles.jsoupparser.Transformer;

/**
 * This exception is thrown when JsoupParser can't create a instance of a {@link Transformer}.
 * It may happen for few reasons, you Transformer may have: <i>private constructor</i> or
 * <i>constructor parameter</i>.
 *
 * <p>Fixing: make sure your Transformer is accessible outside it's package and does not need any
 * parameter to be instantiated.
 */
public class InvalidTransformerException extends RuntimeException {

    private static final String MESSAGE = "Failed to get \"%s\". Can't create an instance of \"%s\".";

    public InvalidTransformerException(String methodName, Class<? extends Transformer> parserClass, Throwable cause) {
        super(String.format(MESSAGE, methodName, parserClass.getSimpleName()), cause);
    }
}
