package com.brunodles.jsoupparser.collectors;

import com.brunodles.jsoupparser.ElementCollector;
import com.brunodles.jsoupparser.JsoupParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Method;

public class AttrFollowUrlElementCollector implements ElementCollector<Object> {
    @Nullable
    @Override
    public Object collect(@NotNull JsoupParser jsoupParser, @NotNull Element value, @NotNull Method method) {
        AttrElementCollector.Settings settings = method.getAnnotation(AttrElementCollector.Settings.class);
        try {
            return jsoupParser.parseUrl(value.attr(settings.attrName()), method.getReturnType());
        } catch (IOException e) {
            return null;
        }
    }
}
