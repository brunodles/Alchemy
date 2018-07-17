package com.brunodles.jsoupparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public class MethodInvocation {
    public final ProxyHandler proxyHandler;
    public final Method method;
    public final Object[] parameters;
    public final String methodName;

    MethodInvocation(ProxyHandler proxyHandler, Method method, Object[] parameters) {
        this.proxyHandler = proxyHandler;
        this.method = method;
        this.parameters = parameters;
        methodName = method.getName();
    }

    /**
     * Get method annotations
     * @return an array
     */
    public Annotation[] getMethodAnnotations() {
        return method.getAnnotations();
    }

    /**
     * Just wraps call to @{code method.getReturnType}.
     * The @{link #getMethodRealReturnType} also consider the generic type on a collection.
     *
     * @return the raw return type of the method
     */
    public Class<?> getMethodRawReturnType() {
        return method.getReturnType();
    }

    /**
     * Get the type to use, this will not return the raw type, it can extract the type from a collection.
     */
    public Class<?> getMethodRealReturnType() {
        final Class<?> returnType = getMethodRawReturnType();
        if (isMethodReturnTypeCollection()) {
            ParameterizedType genericReturnType = (ParameterizedType) method.getGenericReturnType();
            return (Class<?>) genericReturnType.getActualTypeArguments()[0];
        }
        return returnType;
    }

    /**
     * Check if method raw return type is a collection.
     *
     * @return true if is a collection
     */
    public boolean isMethodReturnTypeCollection() {
        return Collection.class.isAssignableFrom(getMethodRawReturnType());
    }
}
