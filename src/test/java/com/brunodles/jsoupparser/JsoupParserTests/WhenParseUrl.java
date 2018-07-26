package com.brunodles.jsoupparser.JsoupParserTests;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.UriResolver;
import com.brunodles.jsoupparser.doubles.UnknownException;
import com.brunodles.jsoupparser.exceptions.ResolverException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.xml.bind.Element;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class WhenParseUrl {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UriResolver uriResolver;
    private JsoupParser jsoupParser;

    @Before
    public void setUp() {
        jsoupParser = new JsoupParser.Builder().uriResolver(uriResolver).build();
    }

    @Test
    public void shouldCallUriResolver() throws IOException {
        when(uriResolver.htmlGet(anyString())).thenReturn("");

        Element simpleModel = jsoupParser.parseUrl("res:whatIsThis", Element.class);

        verify(uriResolver, only()).htmlGet(eq("res:whatIsThis"));
    }

    @Test
    public void whenResolverThrowsException_shouldEncapsulateInResolverException() throws IOException {
        when(uriResolver.htmlGet(anyString())).thenThrow(UnknownException.class);
        expectedException.expect(ResolverException.class);
        expectedException.expectCause(any(UnknownException.class));

        jsoupParser.parseUrl("any", Element.class);
    }
}
