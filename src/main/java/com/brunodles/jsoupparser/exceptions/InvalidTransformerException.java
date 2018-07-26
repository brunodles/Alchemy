package com.brunodles.jsoupparser.exceptions;

import com.brunodles.jsoupparser.Transformer;

/**
 * This exception is thrown when JsoupParserTests can't create a instance of a {@link Transformer}.
 * It may happen for few reasons, you Transformer may have: <i>private constructor</i> or <i>constructor parameter</i>.
 * <p>
 * Fixing: make sure your Transformer is accessible outside it's package and does not need any parameter to be instantiated.
 */
public class InvalidTransformerException extends RuntimeException {

    public InvalidTransformerException(String methodName, Class<? extends Transformer> parserClass, Throwable cause) {
        super("Failed to get \"" + methodName + "\". Can't create an instance of \"" + parserClass.getSimpleName() + "\".", cause);
    }
}
