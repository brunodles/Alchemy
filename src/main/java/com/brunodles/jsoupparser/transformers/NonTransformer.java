package com.brunodles.jsoupparser.transformers;

import com.brunodles.jsoupparser.Transformer;

public class NonTransformer<T> implements Transformer<T, T> {

    @Override
    public T parse(T value) {
        return value;
    }
}
