package com.brunodles.alchemist;

@FunctionalInterface
public interface Transmuter<INPUT, OUTPUT> {
    OUTPUT transform(INPUT value);
}
