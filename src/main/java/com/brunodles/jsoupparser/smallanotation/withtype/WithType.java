package com.brunodles.jsoupparser.smallanotation.withtype;

import com.brunodles.jsoupparser.Transformer;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithType {
    Class<? extends Transformer> value();
}
