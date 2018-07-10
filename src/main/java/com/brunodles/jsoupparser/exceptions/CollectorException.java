package com.brunodles.jsoupparser.exceptions;

import com.brunodles.jsoupparser.ElementCollector;

public class CollectorException extends RuntimeException {

    public CollectorException(String methodName, Class<? extends ElementCollector> collectorClass, Exception cause) {
        super("Failed to get \"" + methodName + "\" using collector \"" + collectorClass.getSimpleName() + "\".", cause);
    }
}
