package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.exceptions.ResolverException;
import com.brunodles.jsoupparser.methodinvocation.BigInvocationHandler;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class JsoupParser {

    final MethodInvocationHandler invocationHandler;
    private final UriResolver uriResolver;

    public JsoupParser() {
        this(null);
    }

    public JsoupParser(UriResolver uriResolver) {
        this(uriResolver, new BigInvocationHandler.Factory());
    }

    public JsoupParser(UriResolver uriResolver, MethodInvocationHandler invocationHandler) {
        this.uriResolver = uriResolver;
        this.invocationHandler = invocationHandler;
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
