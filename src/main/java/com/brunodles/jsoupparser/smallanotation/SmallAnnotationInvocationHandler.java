package com.brunodles.jsoupparser.smallanotation;

import com.brunodles.jsoupparser.MethodInvocation;
import com.brunodles.jsoupparser.MethodInvocationHandler;
import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.exceptions.ResultException;
import com.brunodles.jsoupparser.smallanotation.annotations.*;
import com.brunodles.jsoupparser.smallanotation.collectors.AttrCollector;
import com.brunodles.jsoupparser.smallanotation.collectors.AttrCollectorTransformer;
import com.brunodles.jsoupparser.smallanotation.collectors.TextCollector;
import com.brunodles.jsoupparser.smallanotation.collectors.TextCollectorTransformer;
import com.brunodles.jsoupparser.smallanotation.navigate.Navigate;
import com.brunodles.jsoupparser.smallanotation.navigate.NavigateTransformer;
import com.brunodles.jsoupparser.smallanotation.nested.Nested;
import com.brunodles.jsoupparser.smallanotation.nested.NestedTransformer;
import com.brunodles.jsoupparser.smallanotation.selector.Selector;
import com.brunodles.jsoupparser.smallanotation.selector.SelectorTransformer;
import com.brunodles.jsoupparser.smallanotation.withtype.WithType;
import com.brunodles.jsoupparser.smallanotation.withtype.WihTypeTransformer;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class SmallAnnotationInvocationHandler implements MethodInvocationHandler {

    public final Map<Class<? extends Annotation>, Class<? extends Transformer>> transformerMap;

    public SmallAnnotationInvocationHandler() {
        Map<Class<? extends Annotation>, Class<? extends Transformer>> transfomers = new HashMap<>();
        transfomers.put(Selector.class, SelectorTransformer.class);
        transfomers.put(TextCollector.class, TextCollectorTransformer.class);
        transfomers.put(AttrCollector.class, AttrCollectorTransformer.class);
        transfomers.put(WithType.class, WihTypeTransformer.class);
        transfomers.put(Nested.class, NestedTransformer.class);
        transfomers.put(Navigate.class, NavigateTransformer.class);
        transformerMap = Collections.unmodifiableMap(transfomers);
    }

    public SmallAnnotationInvocationHandler(Map<Class<? extends Annotation>, Class<? extends Transformer>> transformerMap) {
        this.transformerMap = Collections.unmodifiableMap(transformerMap);
    }

    @Override
    public Object invoke(MethodInvocation invocation) {
        Annotation[] annotations = getAnnotations(invocation);
        List result = null;
        for (Annotation annotation : annotations) {
            Class<? extends Transformer> transformerClass = null;
            for (Map.Entry<Class<? extends Annotation>, Class<? extends Transformer>> entry : transformerMap.entrySet()) {
                if (entry.getKey().isInstance(annotation)) {
                    transformerClass = entry.getValue();
                    break;
                }
            }
            if (transformerClass != null) {
                try {
                    result = (List) transformerClass
                            .newInstance()
                            .transform(new AnnotationInvocation(invocation, annotation, result));
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
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

    private Annotation[] getAnnotations(MethodInvocation invocation) {
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
        return annotations;
    }
}
