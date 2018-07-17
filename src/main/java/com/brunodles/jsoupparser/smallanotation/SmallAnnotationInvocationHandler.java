package com.brunodles.jsoupparser.smallanotation;

import com.brunodles.jsoupparser.MethodInvocation;
import com.brunodles.jsoupparser.MethodInvocationHandler;
import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.smallanotation.annotations.*;
import com.brunodles.jsoupparser.exceptions.InvalidSelectorException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class SmallAnnotationInvocationHandler implements MethodInvocationHandler {

    @Override
    public Object invoke(MethodInvocation invocation) {
        Annotation[] annotations = invocation.getMethodAnnotations();
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
        }
        if (invocation.isMethodReturnTypeCollection())
            return result;
        return result.get(0);
    }
}
