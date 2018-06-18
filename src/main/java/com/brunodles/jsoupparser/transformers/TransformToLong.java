package com.brunodles.jsoupparser.transformers;

import com.brunodles.jsoupparser.Transformer;

public class TransformToLong implements Transformer<String, Long> {
    @Override
    public Long parse(String value) {
        return Long.valueOf(value);
    }
}
