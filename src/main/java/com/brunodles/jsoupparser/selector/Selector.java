package com.brunodles.jsoupparser.selector;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Selector {
    String value();
}
