package com.brunodles.jsoupparser;

@FunctionalInterface
public interface Transformer<INPUT, OUTPUT> {
    OUTPUT transform(INPUT value);
}
