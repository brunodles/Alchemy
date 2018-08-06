package com.brunodles.alchemist.doubles;

import com.brunodles.alchemist.Transmutation;
import com.brunodles.alchemist.transformers.TransformerFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TransformerFor(TransmutationWithError.Annotation.class)
public class TransmutationWithError implements Transmutation {
    @Override
    public Object transform(Object value) {
        throw new RuntimeException("Error here");
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Annotation {
    }
}
