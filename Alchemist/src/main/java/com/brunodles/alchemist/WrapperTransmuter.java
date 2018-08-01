package com.brunodles.alchemist;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

final class WrapperTransmuter<AnnotationT extends Annotation, InputT, OutputT>
        implements Transmuter<AnnotationInvocation<AnnotationT, List<InputT>>, List<OutputT>> {

    private final Transmuter<AnnotationInvocation<AnnotationT, InputT>, OutputT> transmuter;

    WrapperTransmuter(Transmuter<AnnotationInvocation<AnnotationT, InputT>, OutputT> transmuter) {
        this.transmuter = transmuter;
    }

    @Override
    public List<OutputT> transform(AnnotationInvocation<AnnotationT, List<InputT>> value) {
        ArrayList<OutputT> result = new ArrayList<>();
        for (InputT input : value.result)
            result.add(transmuter.transform(new AnnotationInvocation<>(value, value.annotation, input)));
        return result;
    }
}
