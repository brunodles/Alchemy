package com.brunodles.jsoupparser.smallanotation.collectors;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.smallanotation.AnnotationInvocation;
import com.brunodles.jsoupparser.smallanotation.TransformerFor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(TextCollector.class)
public class TextCollectorTransformer implements Transformer<AnnotationInvocation<TextCollector, Elements>, List<String>> {

    @Override
    public List<String> transform(AnnotationInvocation<TextCollector, Elements> value) {
        ArrayList<String> result = new ArrayList<>();
        for (Element element : value.result)
            result.add(element.text());
        return result;
    }
}
