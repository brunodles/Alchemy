package com.brunodles.jsoupparser.bigannotation.collectors;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.doubles.UnknownException;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class AttrElementCollectorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    JsoupParser jsoupParser;
    @Mock
    Element element;
    @Mock
    Method method;

    private AttrElementCollector collector = new AttrElementCollector();

    @After
    public void shouldCallOnlyElementText() {
        verifyZeroInteractions(jsoupParser);
        verify(element, atMost(1)).attr(anyString());
    }

    @Test
    public void whenMethodAnnotationIsNull_shouldThrowSettingsNotFoundException() {
        when(method.getAnnotation(eq(AttrElementCollector.Settings.class))).thenReturn(null);
        when(method.getName()).thenReturn("methodName");
        expectedException.expect(AttrElementCollector.SettingsNotFoundException.class);
        expectedException.expectMessage("Failed to collect attr for \"methodName\", missing Settings annotation.");

        collect();
    }

    @Test
    public void whenElementReturnsString_shouldReturnSame() {
        AttrElementCollector.Settings settings = mock(AttrElementCollector.Settings.class);
        when(method.getAnnotation(eq(AttrElementCollector.Settings.class))).thenReturn(settings);
        when(settings.attrName()).thenReturn("attrName");
        when(element.attr(anyString())).thenReturn("is this real");

        String result = collect();

        assertEquals("is this real", result);
    }

    @Nullable
    private String collect() {
        return collector.collect(jsoupParser, element, method);
    }

    @Test
    public void whenElementReturnsNull_shouldReturnNull() {
        AttrElementCollector.Settings settings = mock(AttrElementCollector.Settings.class);
        when(method.getAnnotation(eq(AttrElementCollector.Settings.class))).thenReturn(settings);
        when(settings.attrName()).thenReturn("attrName");
        when(element.attr(anyString())).thenReturn(null);

        String result = collect();

        assertEquals(null, result);
    }

    @Test
    public void whenElementThrowsException_shouldReThrow() {
        AttrElementCollector.Settings settings = mock(AttrElementCollector.Settings.class);
        when(method.getAnnotation(eq(AttrElementCollector.Settings.class))).thenReturn(settings);
        when(settings.attrName()).thenReturn("attrName");
        when(element.attr(anyString())).thenThrow(UnknownException.class);
        expectedException.expect(UnknownException.class);

        collect();
    }

}
