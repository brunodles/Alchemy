package com.brunodles.alchemist;

import com.brunodles.alchemist.exceptions.ResolverException;
import com.brunodles.alchemist.transformers.Transformers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.reflect.Proxy;

import static com.brunodles.alchemist.Alchemist.Builder.defaultTransformers;
import static com.brunodles.alchemist.Alchemist.Builder.defaultUriResolver;

@SuppressWarnings("unchecked")
public class Alchemist {

    final MethodInvocationHandler invocationHandler;
    private final UriFetcher uriFetcher;
    private final ClassLoader classLoader;

    public Alchemist() {
        this(new MethodToAnnotationInvocationHandler(defaultTransformers()), defaultUriResolver(), null);
    }

    private Alchemist(@NotNull MethodInvocationHandler invocationHandler,
            @NotNull UriFetcher uriFetcher, @Nullable ClassLoader classLoader) {
        this.invocationHandler = invocationHandler;
        this.uriFetcher = uriFetcher;

        if (classLoader == null)
            this.classLoader = this.getClass().getClassLoader();
        else
            this.classLoader = classLoader;
    }

    /**
     * Parses the content from {@code url} into the {@code interfaceClass}.
     *
     * @param url            a url for the page wanted
     * @param interfaceClass an interface with method annotated with transformers
     * @param <T>            Returning Type
     * @return A proxy to read the page content.
     */
    @NotNull
    public <T> T parseUrl(@NotNull String url, @NotNull Class<T> interfaceClass) {
        final String html;
        try {
            html = uriFetcher.htmlGet(url);
        } catch (Exception e) {
            throw new ResolverException(url, e);
        }
        return parseHtml(html, interfaceClass);
    }

    /**
     * Parses an {@code html} into {@code interfaceClass}.
     *
     * @param html           a html page to be used
     * @param interfaceClass an interface with method annotated with transformers
     * @param <T>            Returning Type
     * @return A proxy to read the {@code html} content
     */
    public <T> T parseHtml(@NotNull String html, @NotNull Class<T> interfaceClass) {
        Document document = Jsoup.parse(html);
        if (Element.class.isAssignableFrom(interfaceClass))
            return (T) document;
        return parseElement(document, interfaceClass);
    }

    /**
     * Parses the {@code element} into {@code interfaceClass}. This method is used internally.
     *
     * @param element        a jSoup Element
     * @param interfaceClass an interface with method annotated with transformers
     * @param <T>            Returning Type
     * @return A proxy to read the {@code element}
     */
    public <T> T parseElement(@NotNull Element element, @NotNull Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                classLoader,
                new Class[]{interfaceClass},
                new ProxyHandler(this, element, interfaceClass));
    }

    /**
     * A builder for {@link Alchemist}.<br> This builder will let change some configurations of Alchemist: <ul>
     * <li>{@link Transmuter}s - this will allow you to add your custom annotations</li> <li>{@link ClassLoader} - in
     * some environments Alchemist can't find the interface class</li> <li>{@link UriFetcher} - provide how to fetch
     * URI. With this you can create caches, change how to follow redirects.</li> </ul>
     */
    public static class Builder {
        private Transformers transformers;
        private ClassLoader classLoader;
        private UriFetcher uriFetcher;

        @NotNull
        static HttpFetcher defaultUriResolver() {
            return new HttpFetcher();
        }

        @NotNull
        static Transformers defaultTransformers() {
            return new Transformers.Builder().build();
        }

        public Builder transformers(Transformers builder) {
            this.transformers = builder;
            return this;
        }

        public Builder classLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public Builder uriResolver(UriFetcher uriFetcher) {
            this.uriFetcher = uriFetcher;
            return this;
        }

        /**
         * Build the Alchemist, using custom configurations.
         *
         * @return A Alchemist that will use the parameters passed into this builder.
         */
        public Alchemist build() {
            if (uriFetcher == null)
                uriFetcher = defaultUriResolver();
            if (transformers == null)
                transformers = defaultTransformers();
            return new Alchemist(new MethodToAnnotationInvocationHandler(transformers), uriFetcher, classLoader);
        }
    }
}
