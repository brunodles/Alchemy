package com.brunodles.jsoupparser;

import org.jsoup.nodes.Element;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

class ProxyHandler implements InvocationHandler {

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
    public Object invoke(Object o, Method method, Object[] objects) {
        if (resultCache.containsKey(method))
            return resultCache.get(method);
        Object result = new MethodInvokeHandler(this, method, objects)
                .invoke();
        resultCache.put(method, result);
        return result;
    }
}
