package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.Transformer;

public class TransformToFloat implements Transformer<String, Float> {
    @Override
    public Float transform(String value) {
        return Float.valueOf(value);
    }
}
