package com.brunodles.jsoupparser.smallanotation;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.smallanotation.collectors.AttrCollectorTransformer;
import com.brunodles.jsoupparser.smallanotation.collectors.TextCollectorTransformer;
import com.brunodles.jsoupparser.smallanotation.navigate.NavigateTransformer;
import com.brunodles.jsoupparser.smallanotation.nested.NestedTransformer;
import com.brunodles.jsoupparser.smallanotation.selector.SelectorTransformer;
import com.brunodles.jsoupparser.smallanotation.withtype.WihTypeTransformer;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class TransformersBuilder {

    private final Map<Class<? extends Annotation>, Class<? extends Transformer>> transformerMap;

    public TransformersBuilder() {
        transformerMap = new HashMap<>();
        defaultTransformers();
    }

    private void defaultTransformers() {
        add(SelectorTransformer.class);
        add(TextCollectorTransformer.class);
        add(AttrCollectorTransformer.class);
        add(WihTypeTransformer.class);
        add(NestedTransformer.class);
        add(NavigateTransformer.class);
    }

    public TransformersBuilder add(Class<? extends Transformer> transformer) {
        Class<? extends Annotation> annotation = transformer.getAnnotation(TransformerFor.class).value();
        transformerMap.put(annotation, transformer);
        return this;
    }

    Map<Class<? extends Annotation>, Class<? extends Transformer>> build() {
        return Collections.unmodifiableMap(transformerMap);
    }
}
