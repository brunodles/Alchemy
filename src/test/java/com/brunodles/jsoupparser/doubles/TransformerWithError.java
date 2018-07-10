package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.Transformer;

public class TransformerWithError implements Transformer {
    @Override
    public Object transform(Object value) {
        throw new RuntimeException("Error here");
    }
}
