package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.transformers.NonTransformer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CssSelector {

    @NotNull
    String selector();

    @Nullable
    Class<? extends ElementParser> parser();

    @Nullable
    Class<? extends Transformer> transformer() default NonTransformer.class;

}
