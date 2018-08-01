package com.brunodles.alchemist.transformers;

import com.brunodles.alchemist.Transmuter;
import com.brunodles.alchemist.collectors.AttrCollectorTransmuter;
import com.brunodles.alchemist.collectors.TextCollectorTransmuter;
import com.brunodles.alchemist.exceptions.TransformerException;
import com.brunodles.alchemist.navigate.NavigateTransmuter;
import com.brunodles.alchemist.nested.NestedTransmuter;
import com.brunodles.alchemist.selector.SelectorTransmuter;
import com.brunodles.alchemist.usevalueof.UseValueOfTransmuter;
import com.brunodles.alchemist.withtransformer.WithTransformerTransmuter;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Transformers {

    private final Map<Class<? extends Annotation>, Class<? extends Transmuter>> transformerMap;

    private Transformers(Map<Class<? extends Annotation>, Class<? extends Transmuter>> transformerMap) {
        this.transformerMap = transformerMap;
    }

    /**
     * Returns a transformer for given annotation.<br> This method may throw a {@link RuntimeException} if the
     * transformer class is null.
     *
     * @param annotation The annotation class, this is a key value for the transformer
     * @return a transformer class
     */
    @NotNull
    public Class<? extends Transmuter> transformerFor(Annotation annotation) {
        Class<? extends Transmuter> transformerClass = null;
        for (Map.Entry<Class<? extends Annotation>, Class<? extends Transmuter>> entry : transformerMap.entrySet()) {
            if (entry.getKey().isInstance(annotation)) {
                transformerClass = entry.getValue();
                break;
            }
        }
        if (transformerClass == null)
            throw TransformerException.missingTransformer(annotation);
        //new RuntimeException("Missing transformer exception");
        return transformerClass;
    }

    public static class Builder {
        private final Map<Class<? extends Annotation>, Class<? extends Transmuter>> transformerMap;

        public Builder() {
            transformerMap = new HashMap<>();
            defaultTransformers();
        }

        private void defaultTransformers() {
            add(SelectorTransmuter.class);
            add(TextCollectorTransmuter.class);
            add(AttrCollectorTransmuter.class);
            add(WithTransformerTransmuter.class);
            add(NestedTransmuter.class);
            add(NavigateTransmuter.class);
            add(UseValueOfTransmuter.class);
        }

        /**
         * Add a transformer to a map of transformers.<br> This method will use the value of {@link TransformerFor}
         * annotation as a key. With this you can override the default transformers.
         *
         * @param transformer A Transmuter class annotated with {@link TransformerFor}
         * @return The current builder instance
         */
        @NotNull
        public Builder add(Class<? extends Transmuter> transformer) {
            if (transformer == null)
                throw new IllegalArgumentException("Null is not a valid transformer.");
            TransformerFor transformerFor = transformer.getAnnotation(TransformerFor.class);
            if (transformerFor == null)
                throw new IllegalArgumentException("Transmuter is not annotated with \"TransformerFor\" annotation.");
            Class<? extends Annotation> targetAnnotation = transformerFor.value();
            transformerMap.put(targetAnnotation, transformer);
            return this;
        }

        @NotNull
        public Transformers build() {
            return new Transformers(Collections.unmodifiableMap(transformerMap));
        }
    }
}
