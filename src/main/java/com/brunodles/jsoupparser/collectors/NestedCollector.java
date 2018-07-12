package com.brunodles.jsoupparser.collectors;

import com.brunodles.jsoupparser.ElementCollector;
import com.brunodles.jsoupparser.JsoupParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public class NestedCollector implements ElementCollector<Object> {

    @Nullable
    @Override
    public Object collect(@NotNull JsoupParser jsoupParser, @NotNull Element value, @NotNull Method method) {
        final Class<?> returnType = method.getReturnType();
        if (Collection.class.isAssignableFrom(returnType)) {
            ParameterizedType genericReturnType = (ParameterizedType) method.getGenericReturnType();
            final Class<?> returnClass = (Class<?>) genericReturnType.getActualTypeArguments()[0];
            return jsoupParser.parseElement(value, returnClass);
        }
        return jsoupParser.parseElement(value, returnType);
    }
}
