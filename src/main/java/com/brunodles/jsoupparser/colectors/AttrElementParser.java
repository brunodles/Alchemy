package com.brunodles.jsoupparser.colectors;

import com.brunodles.jsoupparser.ElementParser;
import com.brunodles.jsoupparser.JsoupParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class AttrElementParser implements ElementParser<String> {

    @Nullable
    @Override
    public String parse(@NotNull JsoupParser jsoupParser, @NotNull Element value, @NotNull Method method) {
        Settings annotation = method.getAnnotation(Settings.class);
        return value.attr(annotation.attrName());
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Settings {

        @NotNull
        String attrName();
    }
}
