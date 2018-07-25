package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.exceptions.ResolverException;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class JsoupParser {

    final MethodInvocationHandler invocationHandler;
    private final UriResolver uriResolver;
    private ClassLoader classLoader;

    public JsoupParser() {
        this(null);
    }

    public JsoupParser(UriResolver uriResolver) {
        this(uriResolver, new AnnotationInvocationHandler());
    }

    public JsoupParser(UriResolver uriResolver, MethodInvocationHandler invocationHandler) {
        this.uriResolver = uriResolver;
        this.invocationHandler = invocationHandler;
        classLoader = this.getClass().getClassLoader();
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
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
        if (Element.class.isAssignableFrom(interfaceClass))
            return (T) document;
        return parseElement(document, interfaceClass);
    }

    public <T> T parseElement(@NotNull Element element, @NotNull Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                classLoader,
                new Class[]{interfaceClass},
                new ProxyHandler(this, element, interfaceClass));
    }

}
