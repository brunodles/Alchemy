package com.brunodles.jsoupparser.exceptions;

import com.brunodles.jsoupparser.Transformer;

public class TransformerException extends RuntimeException {
    public TransformerException(String methodName, Class<? extends Transformer> transformerClass, Exception cause) {
        super("Failed to get \"" + methodName + "\" using \"" + transformerClass.getSimpleName() + "\".", cause);
    }
}
