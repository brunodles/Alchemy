package com.brunodles.jsoupparser.smallanotation.nestedcollector;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Nested {
}
