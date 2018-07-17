package com.brunodles.jsoupparser.smallanotation;

import com.brunodles.jsoupparser.MethodInvocation;
import com.brunodles.jsoupparser.MethodInvocationHandler;
import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.exceptions.InvalidSelectorException;
import com.brunodles.jsoupparser.exceptions.ResultException;
import com.brunodles.jsoupparser.smallanotation.annotations.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SmallAnnotationInvocationHandler implements MethodInvocationHandler {

    @Override
    public Object invoke(MethodInvocation invocation) {
        Mapping mappingAnnotation = invocation.getMethodAnnotation(Mapping.class);
        Annotation[] annotations;
        if (mappingAnnotation == null) {
            annotations = invocation.getMethodAnnotations();
        } else {
            String[] strings = mappingAnnotation.value();
            ArrayList<Annotation> annotationList = new ArrayList<>();
            for (String string : strings) {
                final String annotationName;
                final String annotationValue;
                if (string.contains("(")) {
                    int openBrackets = string.indexOf("(");
                    int closeBrackets = string.lastIndexOf(")");
                    annotationName = string.substring(0, openBrackets);
                    annotationValue = string.substring(openBrackets + 1, closeBrackets);
                } else {
                    annotationName = string;
                    annotationValue = null;
                }
                Class<? extends Annotation> annotationClass;
                try {
                    annotationClass = (Class<? extends Annotation>) Class.forName("com.brunodles.jsoupparser.smallanotation.annotations." + annotationName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Annotation annotation = (Annotation) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{annotationClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        return annotationValue;
                    }
                });
                annotationList.add(annotation);
            }
            annotations = annotationList.toArray(new Annotation[annotationList.size()]);
        }
        Elements elements = null;
        List<Object> result = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof Selector) {
                String selector = ((Selector) annotation).value();
                elements = invocation.proxyHandler.document.select(selector);
                if (elements == null || elements.isEmpty())
                    throw new InvalidSelectorException(invocation.methodName, selector);
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
                result = new ArrayList<>(elements.size());
                for (Element element : elements)
                    result.add(invocation.proxyHandler.jsoupParser.parseElement(element, invocation.getMethodRealReturnType()));
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
            if (annotation instanceof FollowTransformer && result != null && !result.isEmpty()) {
                ArrayList<Object> newResult = new ArrayList<>(result.size());
                for (int i = 0; i < result.size(); i++) {
                    String url = (String) result.get(i);
                    newResult.add(invocation.proxyHandler.jsoupParser.parseUrl(url, invocation.getMethodRealReturnType()));
                }
                result = newResult;
                continue;
            }
        }
        if (invocation.isMethodReturnTypeCollection()) {
            try {
                Collection collectionResult = (Collection) invocation.getMethodRawReturnType().newInstance();
                for (Object object : result)
                    collectionResult.add(object);
                return collectionResult;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ResultException(invocation.methodName, invocation.getMethodRawReturnType().getSimpleName(), e);
            }
        }
        return result.get(0);
    }
}
