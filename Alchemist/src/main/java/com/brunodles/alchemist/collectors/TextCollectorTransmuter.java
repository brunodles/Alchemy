package com.brunodles.alchemist.collectors;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmuter;
import com.brunodles.alchemist.transformers.TransformerFor;
import org.jsoup.nodes.Element;

@TransformerFor(TextCollector.class)
public class TextCollectorTransmuter implements Transmuter<AnnotationInvocation<TextCollector, Element>, String> {

    @Override
    public String transform(AnnotationInvocation<TextCollector, Element> value) {
        return value.result.text();
    }
}
