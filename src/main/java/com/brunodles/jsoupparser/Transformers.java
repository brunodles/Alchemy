package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.collectors.AttrCollectorTransformer;
import com.brunodles.jsoupparser.collectors.TextCollectorTransformer;
import com.brunodles.jsoupparser.navigate.NavigateTransformer;
import com.brunodles.jsoupparser.nested.NestedTransformer;
import com.brunodles.jsoupparser.selector.SelectorTransformer;
import com.brunodles.jsoupparser.withtype.WithTypeTransformer;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Transformers {

    private final Map<Class<? extends Annotation>, Class<? extends Transformer>> transformerMap;

    private Transformers(Map<Class<? extends Annotation>, Class<? extends Transformer>> transformerMap) {
        this.transformerMap = transformerMap;
    }

    @NotNull
    public Class<? extends Transformer> transformerFor(Annotation annotation) {
        Class<? extends Transformer> transformerClass = null;
        for (Map.Entry<Class<? extends Annotation>, Class<? extends Transformer>> entry : transformerMap.entrySet()) {
            if (entry.getKey().isInstance(annotation)) {
                transformerClass = entry.getValue();
                break;
            }
        }
        if (transformerClass == null)
            throw new RuntimeException("Missing transformers exception");
        return transformerClass;
    }

    public static class Builder {
        private final Map<Class<? extends Annotation>, Class<? extends Transformer>> transformerMap;

        public Builder() {
            transformerMap = new HashMap<>();
            defaultTransformers();
        }

        private void defaultTransformers() {
            add(SelectorTransformer.class);
            add(TextCollectorTransformer.class);
            add(AttrCollectorTransformer.class);
            add(WithTypeTransformer.class);
            add(NestedTransformer.class);
            add(NavigateTransformer.class);
        }

        @NotNull
        public Builder add(@NotNull Class<? extends Transformer> transformer) {
            if (transformer == null)
                throw new RuntimeException("Null is not a valid transformer.");
            Class<? extends Annotation> annotation = transformer.getAnnotation(TransformerFor.class).value();
            transformerMap.put(annotation, transformer);
            return this;
        }

        @NotNull
        public Transformers build() {
            return new Transformers(Collections.unmodifiableMap(transformerMap));
        }
    }
}
