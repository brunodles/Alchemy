package com.brunodles.jsoupparser.transformers;

import com.brunodles.jsoupparser.Transformer;

import java.lang.annotation.Annotation;

import static java.lang.String.format;

public class TransformerException extends RuntimeException {

    private static final String MESSAGE_MISSING_TRANSFORMER = "Missing transformer for \"%s\".";

    private TransformerException(String message, Throwable cause) {
        super(message, cause);
    }

    public static TransformerException failedToGet(String methodName, Class<? extends Transformer> transformerClass,
                                                   Exception cause) {
        return new TransformerException("Failed to get \"" + methodName + "\" using \""
                + transformerClass.getSimpleName() + "\".", cause);
    }

    public static TransformerException missingTransformer(Annotation annotation) {
        return new TransformerException(format(MESSAGE_MISSING_TRANSFORMER,
                annotation.getClass().getSimpleName()), null);
    }
}
