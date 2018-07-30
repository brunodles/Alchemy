package com.brunodles.jsoupparser.nested;

import com.brunodles.jsoupparser.AnnotationInvocation;
import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.TransformerFor;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(Nested.class)
public class NestedTransformer<OUTPUT> implements Transformer<AnnotationInvocation<Nested, List<Element>>,
        List<OUTPUT>> {

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<Nested, List<Element>> value) {
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (Element element : value.result) {
            Object object = value.proxyHandler.jsoupParser.parseElement(element, value.getMethodRealReturnType());
            result.add((OUTPUT) object);
        }
        return result;
    }
}
