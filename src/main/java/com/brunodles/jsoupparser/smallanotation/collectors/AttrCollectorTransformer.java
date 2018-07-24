package com.brunodles.jsoupparser.smallanotation.collectors;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.smallanotation.AnnotationInvocation;
import com.brunodles.jsoupparser.smallanotation.TransformerFor;
import org.jsoup.nodes.Element;

@TransformerFor(AttrCollector.class)
public class AttrCollectorTransformer implements Transformer<AnnotationInvocation<AttrCollector, Element>, String> {

    @Override
    public String transform(AnnotationInvocation<AttrCollector, Element> value) {
        return value.result.attr(value.annotation.value());
    }
}
