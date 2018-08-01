package com.brunodles.alchemist.collectors;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transformer;
import com.brunodles.alchemist.transformers.TransformerFor;
import org.jsoup.nodes.Element;

@TransformerFor(TextCollector.class)
public class TextCollectorTransformer implements Transformer<AnnotationInvocation<TextCollector, Element>, String> {

    @Override
    public String transform(AnnotationInvocation<TextCollector, Element> value) {
        return value.result.text();
    }
}
