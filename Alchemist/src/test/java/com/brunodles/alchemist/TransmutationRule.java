package com.brunodles.alchemist;

import com.brunodles.testhelpers.AnnotationMockBuilder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransmutationRule<AnnotationT extends Annotation> implements TestRule {

    public final AnnotationTransmutation<AnnotationT, Object, Object> transmutation;
    @Mock
    public ProxyHandler proxyHandler;
    @Mock
    public Method method;
    @Mock
    public AnnotationT annotation;

    public TransmutationRule(AnnotationTransmutation<AnnotationT, Object, Object> transmutation) {
        this.transmutation = transmutation;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                MockitoAnnotations.initMocks(TransmutationRule.this);
                base.evaluate();
            }
        };
    }

    public void whenMethodNameReturn(String methodName) {
        when(method.getName()).thenReturn(methodName);
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public final <INPUT, OUTPUT> AnnotationInvocation<AnnotationT, List<INPUT>> buildInvocationWithList(
            final Class<OUTPUT> returnClass, final INPUT... inputs) {
        when(method.getReturnType()).thenReturn((Class) List.class);
        ParameterizedType type = mock(ParameterizedType.class);
        when(type.getActualTypeArguments()).thenReturn(new Type[]{returnClass});
        when(method.getGenericReturnType()).thenReturn(type);
        MethodInvocation methodInvocation = new MethodInvocation(proxyHandler, method, new Object[]{});
        return new AnnotationInvocation(methodInvocation, annotation, Arrays.asList(inputs));
    }

    @SuppressWarnings("unchecked")
    public <INPUT, OUTPUT> List<OUTPUT> transform(AnnotationInvocation<AnnotationT, INPUT> invocation) {
        return (List<OUTPUT>) transmutation.transform((AnnotationInvocation<AnnotationT, Object>) invocation);
    }

    public void withAnnotation(Class<AnnotationT> annotationClass, final String methodName, final Object result) {
        annotation = new AnnotationMockBuilder<>(annotationClass).withResultFor(methodName, result).build();
    }
}
