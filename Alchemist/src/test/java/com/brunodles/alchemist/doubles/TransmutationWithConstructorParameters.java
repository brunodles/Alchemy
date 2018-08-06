package com.brunodles.alchemist.doubles;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.AnnotationTransmutation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class TransmutationWithConstructorParameters implements
        AnnotationTransmutation<TransmutationWithConstructorParameters.Annotation, Object, Object> {

    public TransmutationWithConstructorParameters(String parameter) {
    }

    @Override
    public Object transform(AnnotationInvocation<Annotation, Object> value) {
        return null;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Annotation {
    }
}
