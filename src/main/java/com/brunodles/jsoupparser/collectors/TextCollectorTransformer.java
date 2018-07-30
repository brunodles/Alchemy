package com.brunodles.jsoupparser.collectors;

import com.brunodles.jsoupparser.AnnotationInvocation;
import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.transformers.TransformerFor;
import org.jsoup.nodes.Element;

@TransformerFor(TextCollector.class)
public class TextCollectorTransformer implements Transformer<AnnotationInvocation<TextCollector, Element>, String> {

    @Override
    public String transform(AnnotationInvocation<TextCollector, Element> value) {
        return value.result.text();
    }
}
