package com.brunodles.jsoupparser.smallanotation.withtype;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.smallanotation.AnnotationInvocation;
import com.brunodles.jsoupparser.smallanotation.TransformerFor;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(WithType.class)
public class WithTypeTransformer<INPUT, OUTPUT> implements Transformer<AnnotationInvocation<WithType, List<INPUT>>, List<OUTPUT>> {

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<WithType, List<INPUT>> value) {
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
