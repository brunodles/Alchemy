package com.brunodles.jsoupparser.smallanotation.collectors;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AttrCollector {
    String value();
}
