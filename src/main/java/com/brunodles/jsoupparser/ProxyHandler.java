package com.brunodles.jsoupparser;

import org.jsoup.nodes.Element;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

class ProxyHandler implements InvocationHandler {

    private static final String METHOD_HASHCODE = "hashcode";
    private static final String METHOD_TO_STRING = "toString";
    private static final String METHOD_EQUALS = "equals";

    final JsoupParser jsoupParser;
    final Element document;
    final Class interfaceClass;
    private final HashMap<Method, Object> resultCache = new HashMap<>();

    ProxyHandler(JsoupParser jsoupParser, Element document, Class interfaceClass) {
        this.jsoupParser = jsoupParser;
        this.document = document;
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] parameters) {
        if (resultCache.containsKey(method))
            return resultCache.get(method);

        String methodName = method.getName();
        if (METHOD_HASHCODE.equalsIgnoreCase(methodName))
            return this.hashCode();
        if (METHOD_TO_STRING.equalsIgnoreCase(methodName))
            return "Proxy for \"" + interfaceClass.getName() + "\".";
        if (METHOD_EQUALS.equalsIgnoreCase(methodName))
            return proxyEquals(parameters[0]);

        final Object result;
        if (method.getAnnotation(CssSelector.class) != null)
            result = new BigAnnotationInvokeHandler(this, method, parameters)
                    .invoke();
        else
            result = new SmallAnnotationInvokeHandler(this, method, parameters)
                    .invoke();
        resultCache.put(method, result);
        return result;
    }

    private Object proxyEquals(Object other) {
        if (other == null)
            return false;

        try {
            InvocationHandler handler = Proxy.getInvocationHandler(other);
            return equals(handler);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
