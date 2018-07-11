package com.brunodles.jsoupparser;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class JsoupParser {

    @NotNull
    public <T> T parseUrl(@NotNull String url, @NotNull Class<T> interfaceClass) throws IOException {
        final Document document = Jsoup.connect(url).get();
        return parseElement(document, interfaceClass);
    }

    public <T> T parseHtml(@NotNull String html, @NotNull Class<T> interfaceClass) {
        Document document = Jsoup.parse(html);
        return parseElement(document, interfaceClass);
    }

    public <T> T parseElement(@NotNull Element element, @NotNull Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{interfaceClass},
                new ProxyHandler(this, element, interfaceClass));
    }

}
