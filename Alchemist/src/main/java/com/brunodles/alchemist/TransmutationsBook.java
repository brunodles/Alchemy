package com.brunodles.alchemist;

import com.brunodles.alchemist.collectors.AttrCollectorTransmutation;
import com.brunodles.alchemist.collectors.TextCollectorTransmutation;
import com.brunodles.alchemist.exceptions.TransformerException;
import com.brunodles.alchemist.navigate.NavigateTransmutation;
import com.brunodles.alchemist.nested.NestedTransmutation;
import com.brunodles.alchemist.regex.RegexTransmutation;
import com.brunodles.alchemist.selector.SelectorTransmutation;
import com.brunodles.alchemist.stringformat.StringFormatTransmutation;
import com.brunodles.alchemist.usevalueof.UseValueOfTransmutation;
import com.brunodles.alchemist.withtransformer.WithTransformerTransmutation;
import com.brunodles.glimmer.Glimmer;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class TransmutationsBook {

    private final Map<Class<? extends Annotation>, AnnotationTransmutation> transformerMap;

    private TransmutationsBook(Map<Class<? extends Annotation>, AnnotationTransmutation> transmutationMap) {
        this.transformerMap = transmutationMap;
    }

    /**
     * Returns a transformer for given annotation.<br> This method may throw a {@link RuntimeException} if the
     * transformer class is null.
     *
     * @param annotation The annotation class, this is a key value for the transformer
     * @return a transformer class
     */
    @NotNull
    public AnnotationTransmutation transmutationFor(Annotation annotation) {
        AnnotationTransmutation transformerClass = null;
        for (Map.Entry<Class<? extends Annotation>, AnnotationTransmutation> entry : transformerMap.entrySet()) {
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
        private final Map<Class<? extends Annotation>, AnnotationTransmutation> transformerMap;

        public Builder() {
            transformerMap = new HashMap<>();
            defaultTransformers();
        }

        private static Class getAnnotationClass(Class<? extends AnnotationTransmutation> targetClass) {
            try {
                return Glimmer.forClass(targetClass)
                        .getTypeForGenericInterface(AnnotationTransmutation.class)
                        .asParameterizedType()
                        .arguments()[0]
                        .asClass();
            } catch (Exception e) {
                return null;
            }
        }

        private void defaultTransformers() {
            add(new SelectorTransmutation());
            add(new TextCollectorTransmutation());
            add(new AttrCollectorTransmutation());
            add(new WithTransformerTransmutation());
            add(new NestedTransmutation());
            add(new NavigateTransmutation());
            add(new UseValueOfTransmutation());
            add(new StringFormatTransmutation());
            add(new RegexTransmutation());
        }

        /**
         * Add a Transmutation to a map of Transmutations.<br> This method will figure out the annotation to use it as a
         * key. With this you can override the default Transmutations.
         *
         * <p>The transmutation should implement this {@code AnnotationTransmutation<Annotation, Input ,Output>}.
         *
         * @param transformer A Transmutation class implementing the pattern annotation invocation
         * @return The current builder instance
         */
        @NotNull
        public Builder add(AnnotationTransmutation transformer) {
            if (transformer == null)
                throw new IllegalArgumentException("Null is not a valid transformer.");
            Class<? extends Annotation> targetAnnotation = getAnnotationClass(transformer.getClass());
            if (targetAnnotation == null)
                throw new IllegalArgumentException("Transmutation should follow these parameters: "
                        + "\"AnnotationTransmutation<Annotation, Input, Output>\"");

            transformerMap.put(targetAnnotation, transformer);
            return this;
        }

        @NotNull
        public TransmutationsBook build() {
            return new TransmutationsBook(Collections.unmodifiableMap(transformerMap));
        }
    }
}
