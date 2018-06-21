package com.brunodles.jsoupparser;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

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

    private static class ProxyHandler implements InvocationHandler {

        private final JsoupParser jsoupParser;
        private final Element document;
        private final Class interfaceClass;
        private final HashMap<Method, Object> resultCache = new HashMap<>();

        ProxyHandler(JsoupParser jsoupParser, Element document, Class interfaceClass) {
            this.jsoupParser = jsoupParser;
            this.document = document;
            this.interfaceClass = interfaceClass;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) {
            if (resultCache.containsKey(method))
                return resultCache.get(method);
            if ("hashcode".equals(method.getName()))
                return this.hashCode();
            if ("toString".equals(method.getName()))
                return "Proxy for " + interfaceClass.getName();
            Object result = null;
            try {
                CssSelector cssSelector = method.getAnnotation(CssSelector.class);
                Element element = document.selectFirst(cssSelector.selector());
                Class<? extends ElementCollector> parserClass = cssSelector.parser();
                ElementCollector<?> parser = parserClass.newInstance();
                Transformer transformer = cssSelector.transformer().newInstance();
                result = transformer.parse(parser.parse(jsoupParser, element, method));
                resultCache.put(method, result);
            } catch (Exception e) {
                String message = String.format("Failed to parse \"%s\" of \"%s\"", method.getName(), interfaceClass.getCanonicalName());
                throw new RuntimeException(message, e);
            }
            return result;
        }
    }
}
