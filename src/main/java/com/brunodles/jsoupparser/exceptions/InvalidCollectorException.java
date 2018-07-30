package com.brunodles.jsoupparser.exceptions;

import com.brunodles.jsoupparser.ElementCollector;

/**
 * This exception is thrown when JsoupParser can't create a instance of a {@link ElementCollector}.
 * It may happen for few reasons, you collector may have: <i>private constructor</i> or
 * <i>constructor parameter</i>.
 *
 * <p>Fixing: make sure your Collector is accessible outside it's package and does not need any parameter
 * to be instantiated.
 */
public class InvalidCollectorException extends RuntimeException {

    private static final String MESSAGE = "Failed to get \"%s\". Can't create an instance of \"%s\".";

    public InvalidCollectorException(String methodName, Class<? extends ElementCollector> parserClass,
                                     Throwable cause) {
        super(String.format(MESSAGE, methodName, parserClass.getSimpleName()), cause);
    }
}
