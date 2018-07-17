package com.brunodles.jsoupparser.bigannotation.collectors;

import com.brunodles.jsoupparser.ElementCollector;
import com.brunodles.jsoupparser.JsoupParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.reflect.Method;

public class AttrFollowUrlElementCollector implements ElementCollector<Object> {

    private final AttrElementCollector attrElementCollector = new AttrElementCollector();

    @Nullable
    @Override
    public Object collect(@NotNull JsoupParser jsoupParser, @NotNull Element value, @NotNull Method method) {
        final String url = attrElementCollector.collect(jsoupParser, value, method);
        if (url == null)
            return null;
        return jsoupParser.parseUrl(url, method.getReturnType());
    }
}
