package com.brunodles.alchemist.doubles;

import com.brunodles.alchemist.Transmutation;

public class TransformToFloat implements Transmutation<String, Float> {
    @Override
    public Float transform(String value) {
        return Float.valueOf(value);
    }
}
