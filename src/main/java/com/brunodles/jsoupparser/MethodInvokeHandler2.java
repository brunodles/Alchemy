package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.annotations.*;
import com.brunodles.jsoupparser.exceptions.InvalidSelectorException;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class MethodInvokeHandler2 {
    private final ProxyHandler proxyHandler;
    private final Method method;
    private final Object[] parameters;
    private final String methodName;

    MethodInvokeHandler2(ProxyHandler proxyHandler, Method method, Object[] parameters) {
        this.proxyHandler = proxyHandler;
        this.method = method;
        this.parameters = parameters;
        methodName = method.getName();
    }

    public Object invoke() {
        Annotation[] annotations = method.getAnnotations();
        Elements elements = null;
        List<Object> result = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof Selector) {
                elements = getElements(((Selector) annotation).value());
                continue;
            }
            if (annotation instanceof TextCollector && elements != null) {
                result = new ArrayList<>(elements.size());
                for (Element element : elements)
                    result.add(element.text());
                continue;
            }
            if (annotation instanceof AttrCollector && elements != null) {
                result = new ArrayList<>(elements.size());
                for (Element element : elements)
                    result.add(element.attr(((AttrCollector) annotation).value()));
                continue;
            }
            if (annotation instanceof NestedCollector && elements != null) {
                Class<?> returnType = method.getReturnType();
                if (Collection.class.isAssignableFrom(returnType)) {
                    ParameterizedType genericReturnType = (ParameterizedType) method.getGenericReturnType();
                    returnType = (Class<?>) genericReturnType.getActualTypeArguments()[0];
                }
                result = new ArrayList<>(elements.size());
                for (Element element : elements)
                    result.add(proxyHandler.jsoupParser.parseElement(element, returnType));
                continue;
            }
            if (annotation instanceof TypeTransformer && result != null && !result.isEmpty()) {
                Transformer transformer = null;
                try {
                    transformer = ((TypeTransformer) annotation).value().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Object> newResult = new ArrayList<>(result.size());
                for (Object o : result)
                    newResult.add(transformer.transform(o));
                result = newResult;
                continue;
            }
        }
        final Class<?> returnType = method.getReturnType();
        if (Collection.class.isAssignableFrom(returnType))
            return result;
        return result.get(0);
    }

    @NotNull
    private Elements getElements(String selector) {
        final Elements elements = proxyHandler.document.select(selector);
        if (elements == null || elements.isEmpty())
            throw new InvalidSelectorException(methodName, selector);
        return elements;
    }
}
