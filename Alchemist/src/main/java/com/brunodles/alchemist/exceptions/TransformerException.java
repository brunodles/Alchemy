package com.brunodles.alchemist.exceptions;

import com.brunodles.alchemist.Transformer;

import java.lang.annotation.Annotation;

import static java.lang.String.format;

public class TransformerException extends RuntimeException {

    private static final String MESSAGE_MISSING_TRANSFORMER = "Missing transformer for \"%s\".";
    private static final String MESSAGE_CANT_CREATE = "Can't create \"%s\". "
            + "Check if it have private constructor or if it's constructor have parameters.";
    private static final String MESSAGE_CANT_TRANSFORM = "The transformer \"%s\" can't transform \"%s\".";

    private TransformerException(String message, Throwable cause) {
        super(message, cause);
    }

    public static TransformerException cantCreateTransformer(Class<? extends Transformer> transformerClass,
            Exception cause) {
        return new TransformerException(format(MESSAGE_CANT_CREATE, transformerClass.getSimpleName()), cause);
    }

    public static TransformerException missingTransformer(Annotation annotation) {
        return new TransformerException(format(MESSAGE_MISSING_TRANSFORMER,
                annotation.getClass().getSimpleName()), null);
    }

    public static TransformerException cantTransform(Object input, Class<? extends Transformer> transformerClass,
            Exception cause) {
        return new TransformerException(format(MESSAGE_CANT_TRANSFORM, transformerClass.getSimpleName(), input),
                cause);
    }
}
