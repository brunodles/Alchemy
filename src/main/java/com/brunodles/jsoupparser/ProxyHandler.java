package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.exceptions.*;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

class ProxyHandler implements InvocationHandler {

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
        final String methodName = method.getName();
        checkReturnType(method, methodName);

        if (resultCache.containsKey(method))
            return resultCache.get(method);
        if ("hashcode".equalsIgnoreCase(methodName))
            return this.hashCode();
        if ("toString".equalsIgnoreCase(methodName))
            return "Proxy for \"" + interfaceClass.getName() + "\".";
        if ("equals".equalsIgnoreCase(methodName))
            return proxyEquals(this, objects[0]);

        CssSelector cssSelector = getAnnotation(method, methodName);
        final String selector = cssSelector.selector();

        final Element element = getElement(methodName, selector, document);

        final Object elementValue = getElementValue(method, methodName, element, cssSelector);

        final Class<? extends Transformer> transformerClass = cssSelector.transformer();
        final Transformer transformer = getTransformer(methodName, transformerClass);

        return getResult(method, methodName, transformerClass, transformer, elementValue, resultCache);
    }

    private static Object getResult(Method method, String methodName, Class<? extends Transformer> transformerClass, Transformer transformer, Object elementValue, HashMap<Method, Object> resultCache) {
        final Object result;
        try {
            result = transformer.transform(elementValue);
            resultCache.put(method, result);
        } catch (Exception e) {
            throw new TransformerException(methodName, transformerClass, e);
        }
        return result;
    }

    private Object getElementValue(Method method, String methodName, Element element, CssSelector cssSelector) {
        final Class<? extends ElementCollector> collectorClass = cssSelector.parser();
        final ElementCollector<?> collector = getElementCollector(methodName, collectorClass);

        Object elementValue;
        try {
            elementValue = collector.collect(jsoupParser, element, method);
        } catch (Exception e) {
            throw new CollectorException(methodName, collectorClass, e);
        }
        return elementValue;
    }

    private Transformer getTransformer(String methodName, Class<? extends Transformer> transformerClass) {
        final Transformer transformer;
        try {
            transformer = transformerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InvalidTransformerException(methodName, transformerClass, e);
        }
        return transformer;
    }

    private static ElementCollector<?> getElementCollector(String methodName, Class<? extends ElementCollector> collectorClass) {
        final ElementCollector<?> collector;
        try {
            collector = collectorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InvalidCollectorException(methodName, collectorClass, e);
        }
        return collector;
    }

    @NotNull
    private static Element getElement(String methodName, String selector, Element document) {
        final Element element = document.selectFirst(selector);
        if (element == null)
            throw new InvalidSelectorException(methodName, selector);
        return element;
    }

    @NotNull
    private static CssSelector getAnnotation(Method method, String methodName) {
        CssSelector cssSelector = method.getAnnotation(CssSelector.class);
        if (cssSelector == null)
            throw new MissingSelectorException(methodName);
        return cssSelector;
    }

    private static void checkReturnType(Method method, String methodName) {
        if (method.getReturnType() == Void.TYPE)
            throw new InvalidResultException(methodName);
    }

    private Object proxyEquals(ProxyHandler proxyHandler, Object other) {
        if (other == null)
            return false;

        try {
            InvocationHandler handler = Proxy.getInvocationHandler(other);
            return proxyHandler.equals(handler);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
