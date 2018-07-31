package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.transformers.TransformerFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TransformerFor(TransformerWithConstructorParameters.Annotation.class)
public class TransformerWithConstructorParameters implements Transformer {

    public TransformerWithConstructorParameters(String parameter) {
    }

    @Override
    public Object transform(Object value) {
        return null;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Annotation {
    }
}
