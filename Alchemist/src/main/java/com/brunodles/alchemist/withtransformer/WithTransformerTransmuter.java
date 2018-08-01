package com.brunodles.alchemist.withtransformer;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmuter;
import com.brunodles.alchemist.exceptions.TransformerException;
import com.brunodles.alchemist.transformers.TransformerFor;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(WithTransformer.class)
public class WithTransformerTransmuter<INPUT, OUTPUT> implements
        Transmuter<AnnotationInvocation<WithTransformer, List<INPUT>>, List<OUTPUT>> {

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<WithTransformer, List<INPUT>> value) {
        Transmuter<INPUT, OUTPUT> transmuter = null;
        Class<? extends Transmuter> transformerClass = value.annotation.value();
        try {
            transmuter = transformerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw TransformerException.cantCreateTransformer(transformerClass, e);
        }
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (INPUT input : value.result)
            result.add(transmuter.transform(input));
        return result;
    }
}
