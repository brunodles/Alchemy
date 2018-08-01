package com.brunodles.alchemist.selector;

import java.lang.annotation.*;

/**
 * Annotation that holds the path to a HTML element.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Selector {
    /**
     * Provide the css selector to the target element.
     *
     * @return a string of css selector
     */
    String value();
}
