package com.brunodles.jsoupparser.withtransformer;

import com.brunodles.jsoupparser.AnnotationInvocation;
import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.transformers.TransformerFor;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(WithTransformer.class)
public class WithTransformerTransformer<INPUT, OUTPUT> implements
        Transformer<AnnotationInvocation<WithTransformer, List<INPUT>>, List<OUTPUT>> {

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<WithTransformer, List<INPUT>> value) {
        Transformer<INPUT, OUTPUT> transformer = null;
        try {
            Class<? extends Transformer> transformerClass = value.annotation.value();
            transformer = transformerClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (INPUT input : value.result)
            result.add(transformer.transform(input));
        return result;
    }
}
