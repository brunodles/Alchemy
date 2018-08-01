package com.brunodles.alchemist.doubles;

import com.brunodles.alchemist.Transformer;
import com.brunodles.alchemist.transformers.TransformerFor;

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
