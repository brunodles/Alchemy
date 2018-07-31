package com.brunodles.jsoupparser.exceptions;

public class ResolverException extends RuntimeException {
    public ResolverException(String url, Exception cause) {
        super("Failed to resolve \"" + url + "\".", cause);
    }
}
