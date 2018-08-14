package com.brunodles.alchemist;

import java.lang.annotation.Annotation;

public interface AnnotationTransmutation<AnnotationT extends Annotation, INPUT, OUTPUT>
        extends Transmutation<AnnotationInvocation<AnnotationT, INPUT>, OUTPUT> {
}
