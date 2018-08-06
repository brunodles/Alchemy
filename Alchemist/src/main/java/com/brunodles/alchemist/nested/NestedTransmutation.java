package com.brunodles.alchemist.nested;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.AnnotationTransmutation;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class NestedTransmutation<OUTPUT> implements AnnotationTransmutation<Nested, List<Element>, List<OUTPUT>> {

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
