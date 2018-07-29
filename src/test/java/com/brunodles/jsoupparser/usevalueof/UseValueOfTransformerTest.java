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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.spy;
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
        Integer result = transformUsing(Integer.class, "123");

        assertEquals(new Integer(123), result);
    }

    @Test
    public void whenParseToLong_shouldParse() {
        Long result = transformUsing(Long.class, "2132312132331232121");

        assertEquals(new Long(2132312132331232121L), result);
    }

    @Test
    public void whenParseToFloat_shouldParse() {
        Float result = transformUsing(Float.class, "212121321321.3221332113321123213213");

        assertEquals(212121321321.3221332113321123213213F, result, 0.0000000000000000000001F);
    }

    @Test
    public void whenClassInputIsNotString_shouldReturnNull() {
        Character result = transformUsing(Character.class, "1231323");

        assertNull(result);
    }

    @Test
    public void whenClassDoesNotHaveValueOfMethod_shouldReturnNull() {
        Character result = transformUsing(Character.TYPE, "1231323");
        assertNull(result);
    }

    private <T> T transformUsing(Class<T> returnClass, String input) {
        when(method.getReturnType()).thenReturn((Class) returnClass);
        AnnotationInvocation<UseValueOf, String> invocation = buildAnnotationInvocation(input);
        return (T) transformer.transform(invocation);
    }

    private AnnotationInvocation<UseValueOf, String> buildAnnotationInvocation(String input) {
        Object[] parameters = new Object[]{};
        MethodInvocation methodInvocation = new MethodInvocation(proxyHandler, method, parameters);
        AnnotationInvocation<UseValueOf, String> invocation = new AnnotationInvocation<>(methodInvocation, annotation, input);
        invocation = spy(invocation);
        return invocation;
    }
}