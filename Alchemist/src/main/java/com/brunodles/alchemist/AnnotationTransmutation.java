package com.brunodles.alchemist;

import java.lang.annotation.Annotation;

public interface AnnotationTransmutation<ANNOTATION extends Annotation, INPUT, OUTPUT>
        extends Transmutation<AnnotationInvocation<ANNOTATION, INPUT>, OUTPUT> {
}
