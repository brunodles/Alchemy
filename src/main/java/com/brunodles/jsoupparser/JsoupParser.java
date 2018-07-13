package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.exceptions.ResolverException;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class JsoupParser {

    private final UriResolver uriResolver;

    public JsoupParser() {
        uriResolver = null;
    }

    public JsoupParser(UriResolver uriResolver) {
        this.uriResolver = uriResolver;
    }

    @NotNull
    public <T> T parseUrl(@NotNull String url, @NotNull Class<T> interfaceClass) {
        final String html;
        try {
            html = uriResolver.htmlGet(url);
        } catch (Exception e) {
            throw new ResolverException(url, e);
        }
        return parseHtml(html, interfaceClass);
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
