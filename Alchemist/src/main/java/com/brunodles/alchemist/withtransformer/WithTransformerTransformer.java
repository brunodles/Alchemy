package com.brunodles.alchemist.withtransformer;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transformer;
import com.brunodles.alchemist.exceptions.TransformerException;
import com.brunodles.alchemist.transformers.TransformerFor;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(WithTransformer.class)
public class WithTransformerTransformer<INPUT, OUTPUT> implements
        Transformer<AnnotationInvocation<WithTransformer, List<INPUT>>, List<OUTPUT>> {

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<WithTransformer, List<INPUT>> value) {
        Transformer<INPUT, OUTPUT> transformer = null;
        Class<? extends Transformer> transformerClass = value.annotation.value();
        try {
            transformer = transformerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw TransformerException.cantCreateTransformer(transformerClass, e);
        }
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (INPUT input : value.result)
            result.add(transformer.transform(input));
        return result;
    }
}
