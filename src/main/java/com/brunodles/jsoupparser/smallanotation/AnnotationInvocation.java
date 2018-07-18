package com.brunodles.jsoupparser.smallanotation;

import com.brunodles.jsoupparser.MethodInvocation;
import com.brunodles.jsoupparser.ProxyHandler;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationInvocation<ANNOTATION_TYPE extends Annotation, INPUT_TYPE> {
    public final ProxyHandler proxyHandler;
    public final Method method;
    public final Object[] parameters;
    public final String methodName;
    public final ANNOTATION_TYPE annotation;
    public final INPUT_TYPE result;

    public AnnotationInvocation(MethodInvocation invocation, ANNOTATION_TYPE annotation, INPUT_TYPE result) {
        proxyHandler = invocation.proxyHandler;
        method = invocation.method;
        parameters = invocation.parameters;
        methodName = invocation.methodName;
        this.annotation = annotation;
        this.result = result;
    }

    public Elements documentSelect(String selector) {
        return proxyHandler.document.select(selector);
    }
}
