package com.brunodles.jsoupparser.bigannotation.collectors;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.doubles.UnknownException;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class TextElementCollectorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    JsoupParser jsoupParser;
    @Mock
    Element element;
    @Mock
    Method method;

    private TextElementCollector collector = new TextElementCollector();

    @After
    public void shouldCallOnlyElementText() {
        verifyZeroInteractions(jsoupParser, method);
        verify(element, only()).text();
    }

    @Test
    public void whenElementReturnsString_shouldReturnSame() {
        when(element.text()).thenReturn("Wow");

        String result = collector.collect(jsoupParser, element, method);

        assertEquals("Wow", result);
    }

    @Test
    public void whenElementReturnsNull_shouldReturnNull() {
        when(element.text()).thenReturn(null);

        String result = collector.collect(jsoupParser, element, method);

        assertEquals(null, result);
    }

    @Test(expected = UnknownException.class)
    public void whenElementThrowsException_shouldReThrow() {
        when(element.text()).thenThrow(UnknownException.class);

        collector.collect(jsoupParser, element, method);
    }
}