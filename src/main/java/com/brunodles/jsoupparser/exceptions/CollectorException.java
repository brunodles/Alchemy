package com.brunodles.jsoupparser.exceptions;

import com.brunodles.jsoupparser.ElementCollector;

public class CollectorException extends RuntimeException {

    private static final String MESSAGE = "Failed to get \"%s\" using collector \"%s\".";

    public CollectorException(String methodName, Class<? extends ElementCollector> collectorClass, Exception cause) {
        super(String.format(MESSAGE, methodName, collectorClass.getSimpleName()), cause);
    }
}
