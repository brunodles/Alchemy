package com.brunodles.alchemist.collectors;

import java.lang.annotation.*;

/**
 * Collects attributes of elements on HTML.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AttrCollector {

    /**
     * This value will be used to collect a attribute of an HTML element.
     *
     * @return The name of the attribute to be collected.
     */
    String value();
}
