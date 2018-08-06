package com.brunodles.alchemist.nested;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmutation;
import com.brunodles.alchemist.transformers.TransformerFor;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(Nested.class)
public class NestedTransmutation<OUTPUT> implements Transmutation<AnnotationInvocation<Nested, List<Element>>,
        List<OUTPUT>> {

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<Nested, List<Element>> value) {
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (Element element : value.result) {
            Object object = value.proxyHandler.alchemist.parseElement(element, value.getMethodRealReturnType());
            result.add((OUTPUT) object);
        }
        return result;
    }
}
