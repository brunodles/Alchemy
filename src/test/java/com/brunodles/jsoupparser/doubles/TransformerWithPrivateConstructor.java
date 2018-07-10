package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.Transformer;

public class TransformerWithPrivateConstructor implements Transformer {

    private TransformerWithPrivateConstructor() {
    }

    @Override
    public Object transform(Object value) {
        return null;
    }
}
