package com.brunodles.alchemist;

import com.brunodles.alchemist.exceptions.ResultException;
import com.brunodles.alchemist.exceptions.TransformerException;
import com.brunodles.alchemist.selector.MissingSelectorException;
import com.brunodles.glimmer.ClassGlimmer;
import com.brunodles.glimmer.Glimmer;
import com.brunodles.glimmer.TypeGlimmer;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

public class MethodToAnnotationInvocationHandler implements MethodInvocationHandler {

    public final TransmutationsBook transmutationsBook;

    MethodToAnnotationInvocationHandler(@NotNull TransmutationsBook transmutationsBook) {
        this.transmutationsBook = transmutationsBook;
    }

    @Override
    public Object invoke(MethodInvocation invocation) {
        Annotation[] annotations = invocation.getMethodAnnotations();
        if (annotations.length == 0)
            throw new MissingSelectorException(invocation.methodName);
        if (invocation.getMethodRealReturnType() == Void.TYPE)
            throw ResultException.voidReturn();
        List result = null;
        for (Annotation annotation : annotations) {
            Transmutation transmutation = transmutationsBook.transmutationFor(annotation);
            Class<? extends Transmutation> transmutationClass = transmutation.getClass();
            if (shouldUseWrapper(transmutationClass))
                transmutation = new WrapperTransmutation(transmutation);
            try {
                result = (List) transmutation.transform(new AnnotationInvocation(invocation, annotation, result));
            } catch (Exception e) {
                throw TransformerException.cantTransform(result, transmutationClass, e);
            }
        }
        return getResult(invocation, result);
    }

    private boolean shouldUseWrapper(Class<? extends Transmutation> transformerClass) {
        return !Glimmer.forClass(transformerClass)
                .getTypeForGenericInterface(AnnotationTransmutation.class)
                .asParameterizedType()
                .arguments()[1]
                .isCollection();
    }

    private Object getResult(MethodInvocation invocation, List result) {
        if (invocation.isMethodReturnTypeCollection()) {
            try {
                Collection collectionResult = (Collection) invocation.getMethodRawReturnType().newInstance();
                for (Object object : result)
                    collectionResult.add(object);
                return collectionResult;
            } catch (InstantiationException | IllegalAccessException e) {
                String returnClassName = invocation.getMethodRawReturnType().getSimpleName();
                throw ResultException.cantCreate(returnClassName, e);
            }
        }
        if (result.size() > 0)
            return result.get(0);
        return null;
    }
}
