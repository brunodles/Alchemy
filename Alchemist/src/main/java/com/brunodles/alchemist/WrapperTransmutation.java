package com.brunodles.alchemist;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

final class WrapperTransmutation<AnnotationT extends Annotation, InputT, OutputT>
        implements Transmutation<AnnotationInvocation<AnnotationT, List<InputT>>, List<OutputT>> {

    private final Transmutation<AnnotationInvocation<AnnotationT, InputT>, OutputT> transmutation;

    WrapperTransmutation(Transmutation<AnnotationInvocation<AnnotationT, InputT>, OutputT> transmutation) {
        this.transmutation = transmutation;
    }

    @Override
    public List<OutputT> transform(AnnotationInvocation<AnnotationT, List<InputT>> value) {
        ArrayList<OutputT> result = new ArrayList<>();
        for (InputT input : value.result)
            result.add(transmutation.transform(new AnnotationInvocation<>(value, value.annotation, input)));
        return result;
    }
}
