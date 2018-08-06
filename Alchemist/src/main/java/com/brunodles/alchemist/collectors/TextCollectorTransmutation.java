package com.brunodles.alchemist.collectors;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmutation;
import org.jsoup.nodes.Element;

public class TextCollectorTransmutation implements Transmutation<AnnotationInvocation<TextCollector, Element>, String> {

    @Override
    public String transform(AnnotationInvocation<TextCollector, Element> value) {
        return value.result.text();
    }
}
