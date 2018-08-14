package com.brunodles.alchemist.regex;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Regex {

    /**
     * Provide a regex to be used to extract the content.
     *
     * <p>The <b>Group 1</b> will be used to extract the content.
     *
     * @return a regex string
     */
    String value();
}
