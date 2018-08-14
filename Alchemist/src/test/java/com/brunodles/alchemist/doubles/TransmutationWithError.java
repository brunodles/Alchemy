package com.brunodles.alchemist.doubles;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.AnnotationTransmutation;
import com.brunodles.alchemist.Transmutation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class TransmutationWithError
        implements AnnotationTransmutation<TransmutationWithError.Annotation, Object, Object> {

    @Override
    public Object transform(AnnotationInvocation<Annotation, Object> value) {
        throw new RuntimeException("Error here");
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Annotation {
    }
}
