package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.bigannotation.BigInvocationHandler;
import com.brunodles.jsoupparser.exceptions.ResolverException;
import com.brunodles.jsoupparser.smallanotation.selector.Selector;
import com.brunodles.jsoupparser.smallanotation.selector.SelectorTransformer;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JsoupParser {

    public final Map<Class<? extends Annotation>, Class<? extends Transformer>> transformerMap;
    final MethodInvocationHandler invocationHandler;
    private final UriResolver uriResolver;
    private ClassLoader classLoader;

    public JsoupParser() {
        this(null);
    }

    public JsoupParser(UriResolver uriResolver) {
        this(uriResolver, new BigInvocationHandler.Factory());
    }

    public JsoupParser(UriResolver uriResolver, MethodInvocationHandler invocationHandler) {
        this.uriResolver = uriResolver;
        this.invocationHandler = invocationHandler;
        classLoader = this.getClass().getClassLoader();
        Map<Class<? extends Annotation>, Class<? extends Transformer>> transfomers = new HashMap<>();
        transfomers.put(Selector.class, SelectorTransformer.class);
        transformerMap = Collections.unmodifiableMap(transfomers);
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
        return parseElement(document, interfaceClass);
    }

    public <T> T parseElement(@NotNull Element element, @NotNull Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                classLoader,
                new Class[]{interfaceClass},
                new ProxyHandler(this, element, interfaceClass));
    }

}
