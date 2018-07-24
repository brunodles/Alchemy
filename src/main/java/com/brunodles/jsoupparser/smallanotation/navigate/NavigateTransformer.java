package com.brunodles.jsoupparser.smallanotation.navigate;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.smallanotation.AnnotationInvocation;
import com.brunodles.jsoupparser.smallanotation.TransformerFor;
import org.jsoup.nodes.Element;

@TransformerFor(Navigate.class)
public class NavigateTransformer implements Transformer<AnnotationInvocation<Navigate, String>, Element> {

    @Override
    public Element transform(AnnotationInvocation<Navigate, String> value) {
        return value.proxyHandler.jsoupParser.parseUrl(value.result, Element.class);
    }
}
