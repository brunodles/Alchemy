package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.transformers.TransformerFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TransformerFor(TransformerWithError.Annotation.class)
public class TransformerWithError implements Transformer {
    @Override
    public Object transform(Object value) {
        throw new RuntimeException("Error here");
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Annotation {
    }
}
