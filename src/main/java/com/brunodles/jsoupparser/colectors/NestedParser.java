package com.brunodles.jsoupparser.colectors;

import com.brunodles.jsoupparser.ElementParser;
import com.brunodles.jsoupparser.JsoupParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.reflect.Method;

public class NestedParser implements ElementParser<Object> {

    @Nullable
    @Override
    public Object parse(@NotNull JsoupParser jsoupParser, @NotNull Element value, @NotNull Method method) {
        return jsoupParser.parseElement(value, method.getReturnType());
    }
}
