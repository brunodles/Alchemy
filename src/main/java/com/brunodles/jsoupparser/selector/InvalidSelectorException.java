package com.brunodles.jsoupparser.selector;

/**
 * This exception is thrown when JsoupParserTests can't find the element on the page.
 */
public class InvalidSelectorException extends RuntimeException {
    public InvalidSelectorException(String methodName, String selector) {
        super("Failed to get \"" + methodName + "\". Can't find the selector \"" + selector + "\".");
    }
}
