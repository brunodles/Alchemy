package com.brunodles.jsoupparser.withtransformer;

import com.brunodles.jsoupparser.Transformer;

import java.lang.annotation.*;

/**
 * Use a simple transformer to transform the object.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithTransformer {

    /**
     * Provide a class of the transformer.
     *
     * @return transformer class
     */
    Class<? extends Transformer<String, ?>> value();
}
