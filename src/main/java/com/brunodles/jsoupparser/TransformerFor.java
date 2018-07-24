package com.brunodles.jsoupparser;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransformerFor {
    Class<? extends Annotation> value();
}
