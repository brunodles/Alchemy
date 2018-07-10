package com.brunodles.jsoupparser.exceptions;

import com.brunodles.jsoupparser.ElementCollector;

/**
 * This exception is thrown when JsoupParser can't create a instance of a {@link ElementCollector}.
 * It may happen for few reasons, you collector may have: <i>private constructor</i> or <i>constructor parameter</i>.
 * <p>
 * Fixing: make sure your Collector is accessible outside it's package and does not need any parameter to be instantiated.
 */
public class InvalidCollectorException extends RuntimeException {

    public InvalidCollectorException(String methodName, Class<? extends ElementCollector> parserClass, Throwable cause) {
        super("Failed to get \"" + methodName + "\". Can't create a instance of \"" + parserClass.getSimpleName() + "\".", cause);
    }
}
