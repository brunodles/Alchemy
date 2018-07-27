package com.brunodles.jsoupparser;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

final class WrapperTransformer<AnnotationT extends Annotation, InputT, OutputT>
        implements Transformer<AnnotationInvocation<AnnotationT, List<InputT>>, List<OutputT>> {

    private final Transformer<AnnotationInvocation<AnnotationT, InputT>, OutputT> transformer;

    WrapperTransformer(Transformer<AnnotationInvocation<AnnotationT, InputT>, OutputT> transformer) {
        this.transformer = transformer;
    }

    @Override
    public List<OutputT> transform(AnnotationInvocation<AnnotationT, List<InputT>> value) {
        ArrayList<OutputT> result = new ArrayList<>();
        for (InputT input : value.result)
            result.add(transformer.transform(new AnnotationInvocation<>(value, value.annotation, input)));
        return result;
    }
}
