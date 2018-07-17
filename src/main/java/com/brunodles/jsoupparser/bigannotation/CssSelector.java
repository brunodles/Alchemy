package com.brunodles.jsoupparser.bigannotation;

import com.brunodles.jsoupparser.ElementCollector;
import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.bigannotation.transformers.NonTransformer;
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
    Class<? extends ElementCollector> collector();

    @Nullable
    Class<? extends Transformer> transformer() default NonTransformer.class;

}
