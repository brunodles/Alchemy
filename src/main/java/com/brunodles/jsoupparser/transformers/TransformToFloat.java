package com.brunodles.jsoupparser.transformers;

import com.brunodles.jsoupparser.Transformer;

public class TransformToFloat implements Transformer<String, Float> {
    @Override
    public Float parse(String value) {
        return Float.valueOf(value);
    }
}
