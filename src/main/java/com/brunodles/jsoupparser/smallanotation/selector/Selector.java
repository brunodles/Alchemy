package com.brunodles.jsoupparser.smallanotation.selector;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Selector {
    String value();
}
