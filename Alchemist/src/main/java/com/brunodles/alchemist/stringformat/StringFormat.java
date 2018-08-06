package com.brunodles.alchemist.stringformat;

import java.lang.annotation.*;

/**
 * Annotation used to format result into a string.
 *
 * <p>This also allows to prepend and append text to a result.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface StringFormat {

    /**
     * Provide a string to be used to format the previous result.
     *
     * @return string format
     */
    String value();
}
