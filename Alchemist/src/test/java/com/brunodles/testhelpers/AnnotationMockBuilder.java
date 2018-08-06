package com.brunodles.testhelpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public final class AnnotationMockBuilder<AnnotationT extends Annotation> {
    private final Class<AnnotationT> annotationClass;
    private final Map<String, Object> resultMap = new HashMap<>();

    public AnnotationMockBuilder(Class<AnnotationT> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public AnnotationMockBuilder<AnnotationT> withResultFor(String methodName, Object result) {
        resultMap.put(methodName, result);
        return this;
    }

    public AnnotationT build() {
        return (AnnotationT) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{annotationClass}, new InvocationHandler(resultMap));
    }

    private static class InvocationHandler implements java.lang.reflect.InvocationHandler {

        private final Map<String, Object> resultMap;

        InvocationHandler(Map<String, Object> resultMap) {
            this.resultMap = resultMap;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if (resultMap.containsKey(methodName))
                return resultMap.get(methodName);
            return null;
        }
    }
}
