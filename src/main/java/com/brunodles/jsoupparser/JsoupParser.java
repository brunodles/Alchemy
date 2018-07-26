package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.exceptions.ResolverException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.reflect.Proxy;

import static com.brunodles.jsoupparser.JsoupParser.Builder.defaultTransformers;
import static com.brunodles.jsoupparser.JsoupParser.Builder.defaultUriResolver;

@SuppressWarnings("unchecked")
public class JsoupParser {

    final MethodInvocationHandler invocationHandler;
    private final UriResolver uriResolver;
    private final ClassLoader classLoader;

    public JsoupParser() {
        this(new AnnotationInvocationHandler(defaultTransformers()), defaultUriResolver(), null);
    }

    private JsoupParser(@NotNull MethodInvocationHandler invocationHandler, @NotNull UriResolver uriResolver, @Nullable ClassLoader classLoader) {
        this.invocationHandler = invocationHandler;
        this.uriResolver = uriResolver;

        if (classLoader == null)
            this.classLoader = this.getClass().getClassLoader();
        else
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

    public static class Builder {
        private Transformers transformers;
        private ClassLoader classLoader;
        private UriResolver uriResolver;

        public Builder transformers(Transformers builder) {
            this.transformers = builder;
            return this;
        }

        public Builder classLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public Builder uriResolver(UriResolver uriResolver) {
            this.uriResolver = uriResolver;
            return this;
        }

        public JsoupParser build() {
            if (uriResolver == null)
                uriResolver = defaultUriResolver();
            if (transformers == null)
                transformers = defaultTransformers();
            return new JsoupParser(new AnnotationInvocationHandler(transformers), uriResolver, classLoader);
        }

        @NotNull
        static HttpResolver defaultUriResolver() {
            return new HttpResolver();
        }

        @NotNull
        static Transformers defaultTransformers() {
            return new Transformers.Builder().build();
        }
    }
}
