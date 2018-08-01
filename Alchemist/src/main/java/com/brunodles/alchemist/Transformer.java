package com.brunodles.alchemist;

@FunctionalInterface
public interface Transformer<INPUT, OUTPUT> {
    OUTPUT transform(INPUT value);
}
