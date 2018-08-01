package com.brunodles.alchemist.doubles;

import com.brunodles.alchemist.Transmuter;

public class TransformToFloat implements Transmuter<String, Float> {
    @Override
    public Float transform(String value) {
        return Float.valueOf(value);
    }
}
