package com.brunodles.alchemist.selector;

/**
 * This exception is thrown when JsoupParser can't find the element on the page.
 */
public class InvalidSelectorException extends RuntimeException {
    public InvalidSelectorException(String methodName, String selector) {
        super("Failed to get \"" + methodName + "\". Can't find the selector \"" + selector + "\".");
    }
}
