package com.brunodles.jsoupparser.smallanotation;

import com.brunodles.jsoupparser.Transformer;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

final class WrapperTransformer<ANNOTATION extends Annotation, INPUT, OUTPUT>
        implements Transformer<AnnotationInvocation<ANNOTATION, List<INPUT>>, List<OUTPUT>> {

    private final Transformer<AnnotationInvocation<ANNOTATION, INPUT>, OUTPUT> transformer;

    WrapperTransformer(Transformer<AnnotationInvocation<ANNOTATION, INPUT>, OUTPUT> transformer) {
        this.transformer = transformer;
    }

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<ANNOTATION, List<INPUT>> value) {
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (INPUT input : value.result)
            result.add(transformer.transform(new AnnotationInvocation<>(value, value.annotation, input)));
        return result;
    }
}
