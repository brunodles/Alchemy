package com.brunodles.alchemist.transformers;

import java.lang.annotation.*;

/**
 * This annotation helps to declare for what annotation a transformer is used.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransformerFor {
    /**
     * Target the annotation that will be used for a transformer.
     *
     * @return the annotation class.
     */
    Class<? extends Annotation> value();
}
