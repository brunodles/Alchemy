package com.brunodles.alchemist.doubles;

import com.brunodles.alchemist.Transformer;

public class TransformToFloat implements Transformer<String, Float> {
    @Override
    public Float transform(String value) {
        return Float.valueOf(value);
    }
}
