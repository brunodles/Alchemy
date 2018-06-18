package com.brunodles.jsoupparser.colectors;

import com.brunodles.jsoupparser.ElementParser;
import com.brunodles.jsoupparser.JsoupParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Method;

public class AttrFollowUrlElementParser implements ElementParser<Object> {
    @Nullable
    @Override
    public Object parse(@NotNull JsoupParser jsoupParser, @NotNull Element value, @NotNull Method method) {
        AttrElementParser.Settings settings = method.getAnnotation(AttrElementParser.Settings.class);
        try {
            return jsoupParser.parseUrl(value.attr(settings.attrName()), method.getReturnType());
        } catch (IOException e) {
            return null;
        }
    }
}
