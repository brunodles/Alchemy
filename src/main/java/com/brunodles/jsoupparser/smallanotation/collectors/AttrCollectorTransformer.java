package com.brunodles.jsoupparser.smallanotation.collectors;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.smallanotation.AnnotationInvocation;
import com.brunodles.jsoupparser.smallanotation.TransformerFor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(AttrCollector.class)
public class AttrCollectorTransformer implements Transformer<AnnotationInvocation<AttrCollector, Elements>, List<String>> {

    @Override
    public List<String> transform(AnnotationInvocation<AttrCollector, Elements> value) {
        ArrayList<String> result = new ArrayList<>();
        String attr = value.annotation.value();
        for (Element element : value.result)
            result.add(element.attr(attr));
        return result;
    }
}
