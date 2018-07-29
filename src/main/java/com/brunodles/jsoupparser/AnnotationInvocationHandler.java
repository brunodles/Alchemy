package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.exceptions.InvalidResultException;
import com.brunodles.jsoupparser.exceptions.ResultException;
import com.brunodles.jsoupparser.selector.MissingSelectorException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class AnnotationInvocationHandler implements MethodInvocationHandler {

    public final Transformers transformers;

    public AnnotationInvocationHandler() {
        this(new Transformers.Builder().build());
    }

    public AnnotationInvocationHandler(@NotNull Transformers transformers) {
        this.transformers = transformers;
    }

    @Override
    public Object invoke(MethodInvocation invocation) {
        Annotation[] annotations = invocation.getMethodAnnotations();
        if (annotations.length == 0)
            throw new MissingSelectorException(invocation.methodName);
        if (invocation.getMethodRealReturnType() == Void.TYPE)
            throw new InvalidResultException(invocation.methodName);
        List result = null;
        for (Annotation annotation : annotations) {
            Class<? extends Transformer> transformerClass = transformers.transformerFor(annotation);
            try {
                Transformer transformer = transformerClass.newInstance();
                if (shouldUseWrapper(transformerClass))
                    transformer = new WrapperTransformer(transformer);
                result = (List) transformer.transform(new AnnotationInvocation(invocation, annotation, result));
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return getResult(invocation, result);
    }

    private boolean shouldUseWrapper(Class<? extends Transformer> transformerClass) {
        try {
            Type[] genericInterfaces = transformerClass.getGenericInterfaces(); // List of interfaces of our transformer
            ParameterizedType type = (ParameterizedType) genericInterfaces[0]; // expected: Transformer
            Type[] actualTypeArguments = type.getActualTypeArguments(); // Array of Transformer's Generics
            Type annotationInvocationType = actualTypeArguments[0];
            Class<?> inputClass = (Class<?>) actualTypeArguments[1]; // second argument is the result
            return !Collection.class.isAssignableFrom(inputClass); // is it a Collection?
        } catch (Exception e) {
            return false;
        }
    }

    private Object getResult(MethodInvocation invocation, List result) {
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
        if (result.size() > 0)
            return result.get(0);
        return null;
    }
}
