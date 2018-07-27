package com.brunodles.jsoupparser.selector;

import com.brunodles.jsoupparser.AnnotationInvocation;
import com.brunodles.jsoupparser.MethodInvocation;
import com.brunodles.jsoupparser.ProxyHandler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class SelectorTransformerTest {

    private final SelectorTransformer transformer = new SelectorTransformer();

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    ProxyHandler proxyHandler;
    @Mock
    Method method;
    @Mock
    Selector annotation;

    @Before
    public void setup() {
        when(method.getName()).thenReturn("methodName");
        when(annotation.value()).thenReturn("selector");
    }

    @Test
    public void whenInputIsNull_shouldDocumentSelect() {
        AnnotationInvocation<Selector, Elements> invocation = buildAnnotationInvocation(null);
        Elements value = new Elements();
        value.add(mock(Element.class));
        doReturn(value).when(invocation).documentSelect(anyString());

        transformer.transform(invocation);

        verify(invocation).documentSelect(anyString());
    }

    @Test
    public void whenInputIsElements_shouldSelectOnElements() {
        Elements input = mock(Elements.class);
        AnnotationInvocation<Selector, Elements> invocation = buildAnnotationInvocation(input);
        when(annotation.value()).thenReturn("wow");
        Elements value = new Elements();
        value.add(mock(Element.class));
        doReturn(value).when(input).select(anyString());

        transformer.transform(invocation);
        
        verify(input).select(anyString());
    }

    @Test
    public void whenResultsIsNull_shouldThrowSelectorException() {
        expectedException.expect(InvalidSelectorException.class);
        expectedException.expectMessage("Failed to get \"methodName\". Can't find the selector \"selector\".");

        AnnotationInvocation<Selector, Elements> invocation = buildAnnotationInvocation(null);
        doReturn(null).when(invocation).documentSelect(anyString());

        transformer.transform(invocation);

        verify(invocation).documentSelect(anyString());
    }

    @Test
    public void whenResultsIsEmpty_shouldThrowSelectorException() {
        expectedException.expect(InvalidSelectorException.class);
        expectedException.expectMessage("Failed to get \"methodName\". Can't find the selector \"selector\".");

        AnnotationInvocation<Selector, Elements> invocation = buildAnnotationInvocation(null);
        doReturn(new Elements()).when(invocation).documentSelect(anyString());

        transformer.transform(invocation);

        verify(invocation).documentSelect(anyString());
    }

    private AnnotationInvocation<Selector, Elements> buildAnnotationInvocation(Elements input) {
        Object[] parameters = new Object[]{};
        MethodInvocation methodInvocation = new MethodInvocation(proxyHandler, method, parameters);
        AnnotationInvocation<Selector, Elements> invocation = new AnnotationInvocation<>(methodInvocation, annotation, input);
        invocation = spy(invocation);
        return invocation;
    }
}