package com.brunodles.jsoupparser.usevalueof;

import com.brunodles.jsoupparser.AnnotationInvocation;
import com.brunodles.jsoupparser.MethodInvocation;
import com.brunodles.jsoupparser.ProxyHandler;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class UseValueOfTransformerTest {

    private final UseValueOfTransformer transformer = new UseValueOfTransformer();

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    ProxyHandler proxyHandler;
    @Mock
    Method method;
    @Mock
    UseValueOf annotation;

    @Before
    public void setup() {
        when(method.getName()).thenReturn("methodName");
    }

    @Test
    public void whenParseToInteger_shouldParse() {
        AnnotationInvocation<UseValueOf, List<String>> invocation = buildInvocation(Integer.class, "123");

        List<Integer> result = transform(invocation);

        assertEquals(new Integer(123), result.get(0));
    }

    @Test
    public void whenParseToLong_shouldParse() {
        AnnotationInvocation<UseValueOf, List<String>> invocation = buildInvocation(Long.class,
                "2132312132331232121");

        List<Long> result = transform(invocation);

        assertEquals(new Long(2132312132331232121L), result.get(0));
    }

    @Test
    public void whenParseToFloat_shouldParse() {
        AnnotationInvocation<UseValueOf, List<String>> invocation = buildInvocation(Float.class,
                "212121321321.3221332113321123213213");

        List<Float> result = transform(invocation);

        assertEquals(212121321321.3221332113321123213213F, result.get(0), 0.0000000000000000000001F);
    }

    @Test(expected = RuntimeException.class)
    public void whenClassDoesNotHaveValueOfMethod_shouldThrowException() {
        AnnotationInvocation<UseValueOf, List<String>> invocation = buildInvocation(Character.TYPE, "1231323");

        List<Character> result = transform(invocation);

        fail();
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    private final <INPUT, OUTPUT> AnnotationInvocation<UseValueOf, List<INPUT>> buildInvocation(
            final Class<OUTPUT> returnClass, final INPUT... inputs) {
        when(method.getReturnType()).thenReturn((Class) List.class);
        ParameterizedType type = mock(ParameterizedType.class);
        when(type.getActualTypeArguments()).thenReturn(new Type[]{returnClass});
        when(method.getGenericReturnType()).thenReturn(type);
        return buildAnnotationInvocation(inputs);
    }

    @SuppressWarnings("unchecked")
    private <INPUT, OUTPUT> List<OUTPUT> transform(AnnotationInvocation<UseValueOf, List<INPUT>> invocation) {
        return (List<OUTPUT>) transformer.transform(invocation);
    }

    @SafeVarargs
    private final <T> AnnotationInvocation<UseValueOf, List<T>> buildAnnotationInvocation(final T... input) {
        Object[] parameters = new Object[]{};
        MethodInvocation methodInvocation = new MethodInvocation(proxyHandler, method, parameters);
        return new AnnotationInvocation<>(methodInvocation, annotation, Arrays.asList(input));
    }
}