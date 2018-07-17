package com.brunodles.jsoupparser.bigannotation.transformers;

import com.brunodles.jsoupparser.Transformer;

public class NonTransformer<T> implements Transformer<T, T> {

    @Override
    public T transform(T value) {
        return value;
    }
}
