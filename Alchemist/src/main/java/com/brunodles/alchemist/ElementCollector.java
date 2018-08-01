package com.brunodles.alchemist;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.reflect.Method;

@FunctionalInterface
public interface ElementCollector<T> {

    @Nullable
    T collect(@NotNull JsoupParser jsoupParser, @NotNull Element element, @NotNull Method method);
}
