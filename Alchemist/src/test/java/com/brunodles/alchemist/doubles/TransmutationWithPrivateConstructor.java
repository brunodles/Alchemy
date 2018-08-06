package com.brunodles.alchemist.doubles;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.AnnotationTransmutation;
import com.brunodles.alchemist.Transmutation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class TransmutationWithPrivateConstructor
        implements AnnotationTransmutation<TransmutationWithPrivateConstructor.Annotation, Object, Object> {

    private TransmutationWithPrivateConstructor() {
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
