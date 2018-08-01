package com.brunodles.alchemist.collectors;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transformer;
import com.brunodles.alchemist.transformers.TransformerFor;
import org.jsoup.nodes.Element;

@TransformerFor(AttrCollector.class)
public class AttrCollectorTransformer implements Transformer<AnnotationInvocation<AttrCollector, Element>, String> {

    @Override
    public String transform(AnnotationInvocation<AttrCollector, Element> value) {
        return value.result.attr(value.annotation.value());
    }
}
