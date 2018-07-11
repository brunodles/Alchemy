package com.brunodles.jsoupparser.collectors;

import com.brunodles.jsoupparser.ElementCollector;
import com.brunodles.jsoupparser.JsoupParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class AttrElementCollector implements ElementCollector<String> {

    @Nullable
    @Override
    public String collect(@NotNull JsoupParser jsoupParser, @NotNull Element value, @NotNull Method method) {
        Settings settings = method.getAnnotation(Settings.class);
        if (settings == null)
            throw new SettingsNotFoundException(method.getName());
        return value.attr(settings.attrName());
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Settings {

        @NotNull
        String attrName();
    }

    public static class SettingsNotFoundException extends RuntimeException {
        SettingsNotFoundException(String methodName) {
            super("Failed to collect attr for \"" + methodName + "\", missing Settings annotation.");
        }
    }
}
