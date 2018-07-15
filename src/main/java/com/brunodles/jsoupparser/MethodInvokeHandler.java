package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.exceptions.*;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class MethodInvokeHandler {

    private final ProxyHandler proxyHandler;
    private final Method method;
    private final String methodName;
    private final Object[] parameters;

    MethodInvokeHandler(ProxyHandler proxyHandler, Method method, Object[] parameters) {
        this.proxyHandler = proxyHandler;
        this.method = method;
        this.parameters = parameters;
        this.methodName = method.getName();
    }

    public Object invoke() {
        checkReturnType();

        final CssSelector annotation = getAnnotation();

        final List<Object> values = getElementsValues(annotation);

        final Class<?> returnType = method.getReturnType();
        if (Collection.class.isAssignableFrom(returnType))
            return getResults(annotation, values, (Class<? extends Collection>) returnType);

        return getResult(annotation, values.get(0));
    }

    private void checkReturnType() {
        if (method.getReturnType() == Void.TYPE)
            throw new InvalidResultException(methodName);
    }

    @NotNull
    private CssSelector getAnnotation() {
        CssSelector cssSelector = method.getAnnotation(CssSelector.class);
        if (cssSelector == null)
            throw new MissingSelectorException(methodName);
        return cssSelector;
    }

    @NotNull
    private Elements getElements(String selector) {
        final Elements elements = proxyHandler.document.select(selector);
        if (elements == null || elements.isEmpty())
            throw new InvalidSelectorException(methodName, selector);
        return elements;
    }

    private List<Object> getElementsValues(CssSelector cssSelector) {
        final Class<? extends ElementCollector> collectorClass = cssSelector.parser();
        final ElementCollector<?> collector = getElementCollector(collectorClass);

        List<Object> result = new LinkedList<>();
        try {
            for (Element element : getElements(cssSelector.selector()))
                result.add(collector.collect(proxyHandler.jsoupParser, element, method));
        } catch (Exception e) {
            throw new CollectorException(methodName, collectorClass, e);
        }
        return result;
    }

    private ElementCollector<?> getElementCollector(Class<? extends ElementCollector> collectorClass) {
        final ElementCollector<?> collector;
        try {
            collector = collectorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InvalidCollectorException(methodName, collectorClass, e);
        }
        return collector;
    }

    private Object getResults(CssSelector annotation, List<Object> values, Class<? extends Collection> returnType) {
        try {
            Collection result = returnType.newInstance();
            for (Object value : values)
                result.add(getResult(annotation, value));
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ResultException(methodName, returnType.getSimpleName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private Object getResult(CssSelector cssSelector, Object value) {
        final Class<? extends Transformer> transformerClass = cssSelector.transformer();
        final Transformer transformer = getTransformer(transformerClass);

        final Object result;
        try {
            result = transformer.transform(value);
        } catch (Exception e) {
            throw new TransformerException(methodName, transformerClass, e);
        }
        return result;
    }

    private Transformer getTransformer(Class<? extends Transformer> transformerClass) {
        final Transformer transformer;
        try {
            transformer = transformerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InvalidTransformerException(methodName, transformerClass, e);
        }
        return transformer;
    }
}
