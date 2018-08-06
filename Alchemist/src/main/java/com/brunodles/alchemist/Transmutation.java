package com.brunodles.alchemist;

@FunctionalInterface
public interface Transmutation<INPUT, OUTPUT> {
    OUTPUT transform(INPUT value);
}
