package com.brunodles.jsoupparser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.reflect.Method;

@FunctionalInterface
public interface ElementParser<T> {
    
    @Nullable
    T parse(@NotNull JsoupParser jsoupParser, @NotNull Element value, @NotNull Method method);
}
