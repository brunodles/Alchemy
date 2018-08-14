package com.brunodles.alchemist.collectors;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.AnnotationTransmutation;
import org.jsoup.nodes.Element;

public class AttrCollectorTransmutation implements AnnotationTransmutation<AttrCollector, Element, String> {

    @Override
    public String transform(AnnotationInvocation<AttrCollector, Element> value) {
        return value.result.attr(value.annotation.value());
    }
}
