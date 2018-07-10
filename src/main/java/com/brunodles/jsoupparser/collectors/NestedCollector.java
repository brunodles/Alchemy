package com.brunodles.jsoupparser.collectors;

import com.brunodles.jsoupparser.ElementCollector;
import com.brunodles.jsoupparser.JsoupParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.reflect.Method;

public class NestedCollector implements ElementCollector<Object> {

    @Nullable
    @Override
    public Object collect(@NotNull JsoupParser jsoupParser, @NotNull Element value, @NotNull Method method) {
        return jsoupParser.parseElement(value, method.getReturnType());
    }
}
