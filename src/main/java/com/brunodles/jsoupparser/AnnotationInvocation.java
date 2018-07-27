package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.MethodInvocation;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;

public class AnnotationInvocation<ANNOTATION_TYPE extends Annotation, INPUT_TYPE> extends MethodInvocation {
    public final ANNOTATION_TYPE annotation;
    public final INPUT_TYPE result;

    public AnnotationInvocation(MethodInvocation invocation, ANNOTATION_TYPE annotation, INPUT_TYPE result) {
        super(invocation.proxyHandler, invocation.method, invocation.parameters);
        this.annotation = annotation;
        this.result = result;
    }

    public Elements documentSelect(String selector) {
        return proxyHandler.document.select(selector);
    }
}
