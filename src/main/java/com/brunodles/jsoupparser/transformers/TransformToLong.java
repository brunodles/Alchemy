package com.brunodles.jsoupparser.transformers;

import com.brunodles.jsoupparser.Transformer;

public class TransformToLong implements Transformer<String, Long> {
    @Override
    public Long transform(String value) {
        return Long.valueOf(value);
    }
}
