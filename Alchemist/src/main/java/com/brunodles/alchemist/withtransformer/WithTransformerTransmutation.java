package com.brunodles.alchemist.withtransformer;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmutation;
import com.brunodles.alchemist.exceptions.TransformerException;

import java.util.ArrayList;
import java.util.List;

public class WithTransformerTransmutation<INPUT, OUTPUT> implements
        Transmutation<AnnotationInvocation<WithTransformer, List<INPUT>>, List<OUTPUT>> {

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<WithTransformer, List<INPUT>> value) {
        Transmutation<INPUT, OUTPUT> transmutation = null;
        Class<? extends Transmutation> transformerClass = value.annotation.value();
        try {
            transmutation = transformerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw TransformerException.cantCreateTransformer(transformerClass, e);
        }
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (INPUT input : value.result)
            result.add(transmutation.transform(input));
        return result;
    }
}
