package com.brunodles.jsoupparser.smallanotation.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NestedCollector {
}
