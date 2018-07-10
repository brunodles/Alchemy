package com.brunodles.jsoupparser.collectors;

import com.brunodles.jsoupparser.JsoupParser;
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
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TextElementCollectorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    public JsoupParser jsoupParser;
    @Mock
    Element element;
    @Mock
    Method method;

    private TextElementCollector collector = new TextElementCollector();

    @After
    public void tearDown() {
        verifyZeroInteractions(jsoupParser, method);
    }

    @Test
    public void givenWhenThen() {
        when(element.text()).thenReturn("Wow");

        String result = collector.collect(jsoupParser, element, method);

        assertEquals("Wow", result);
    }
}