package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.transformers.TransformerFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TransformerFor(TransformerWithPrivateConstructor.Annotation.class)
public class TransformerWithPrivateConstructor implements Transformer {

    private TransformerWithPrivateConstructor() {
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
