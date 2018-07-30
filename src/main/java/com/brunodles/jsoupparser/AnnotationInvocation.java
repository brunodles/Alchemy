package com.brunodles.jsoupparser;

import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;

/**
 * This class holds the data of a annotation invocation.
 */
public class AnnotationInvocation<AnnotationT extends Annotation, InputT> extends MethodInvocation {
    public final AnnotationT annotation;
    public final InputT result;

    /**
     * Creates a new instance of AnnotationInvocation.
     *
     * @param invocation the original invocation. The invoked method.
     * @param annotation the annotation data
     * @param result     the previous result
     */
    public AnnotationInvocation(MethodInvocation invocation, AnnotationT annotation, InputT result) {
        super(invocation.proxyHandler, invocation.method, invocation.parameters);
        this.annotation = annotation;
        this.result = result;
    }

    public Elements documentSelect(String selector) {
        return proxyHandler.document.select(selector);
    }
}
