package com.brunodles.alchemist.transformers;

import com.brunodles.alchemist.Transmutation;
import com.brunodles.alchemist.collectors.AttrCollectorTransmutation;
import com.brunodles.alchemist.collectors.TextCollectorTransmutation;
import com.brunodles.alchemist.exceptions.TransformerException;
import com.brunodles.alchemist.navigate.NavigateTransmutation;
import com.brunodles.alchemist.nested.NestedTransmutation;
import com.brunodles.alchemist.selector.SelectorTransmutation;
import com.brunodles.alchemist.usevalueof.UseValueOfTransmutation;
import com.brunodles.alchemist.withtransformer.WithTransformerTransmutation;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class TransmutationsBook {

    private final Map<Class<? extends Annotation>, Class<? extends Transmutation>> transformerMap;

    private TransmutationsBook(Map<Class<? extends Annotation>, Class<? extends Transmutation>> transmutationMap) {
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
    public Class<? extends Transmutation> transmutationFor(Annotation annotation) {
        Class<? extends Transmutation> transformerClass = null;
        for (Map.Entry<Class<? extends Annotation>, Class<? extends Transmutation>> entry : transformerMap.entrySet()) {
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
        private final Map<Class<? extends Annotation>, Class<? extends Transmutation>> transformerMap;

        public Builder() {
            transformerMap = new HashMap<>();
            defaultTransformers();
        }

        private static Class getAnnotationClass(Class targetClass) {
            try {
                Type[] genericInterfaces = targetClass.getGenericInterfaces();
                ParameterizedType type = (ParameterizedType) genericInterfaces[0];
                Type[] actualTypeArguments = type.getActualTypeArguments();
                ParameterizedType annotationInvocationType = (ParameterizedType) actualTypeArguments[0];
                return (Class) annotationInvocationType.getActualTypeArguments()[0];
            } catch (Exception e) {
                return null;
            }
        }

        private void defaultTransformers() {
            add(SelectorTransmutation.class);
            add(TextCollectorTransmutation.class);
            add(AttrCollectorTransmutation.class);
            add(WithTransformerTransmutation.class);
            add(NestedTransmutation.class);
            add(NavigateTransmutation.class);
            add(UseValueOfTransmutation.class);
        }

        /**
         * Add a Transmutation to a map of Transmutations.<br> This method will figure out the annotation to use it as a
         * key. With this you can override the default Transmutations.
         *
         * <p>The transmutation should implement this {@code Transmutation<AnnotationInvocation<Annotation, Input>,
         * Output>}.
         *
         * @param transformer A Transmutation class implementing the pattern annotation invocation
         * @return The current builder instance
         */
        @NotNull
        public Builder add(Class<? extends Transmutation> transformer) {
            if (transformer == null)
                throw new IllegalArgumentException("Null is not a valid transformer.");
            Class<? extends Annotation> targetAnnotation = getAnnotationClass(transformer);
            if (targetAnnotation == null)
                throw new IllegalArgumentException("Transmutation should follow these parameters: " +
                        "\"Transmutation<AnnotationInvocation<Annotation, Input>, Output>\"");

            transformerMap.put(targetAnnotation, transformer);
            return this;
        }

        @NotNull
        public TransmutationsBook build() {
            return new TransmutationsBook(Collections.unmodifiableMap(transformerMap));
        }
    }
}