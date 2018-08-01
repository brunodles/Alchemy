package com.brunodles.alchemist.AlchemistTest;

import com.brunodles.alchemist.Alchemist;
import com.brunodles.alchemist.UriFetcher;
import com.brunodles.alchemist.doubles.UnknownException;
import com.brunodles.alchemist.exceptions.ResolverException;
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
    private UriFetcher uriFetcher;
    private Alchemist alchemist;

    @Before
    public void setUp() {
        alchemist = new Alchemist.Builder().uriResolver(uriFetcher).build();
    }

    @Test
    public void shouldCallUriResolver() throws IOException {
        when(uriFetcher.htmlGet(anyString())).thenReturn("");

        Element simpleModel = alchemist.parseUrl("res:whatIsThis", Element.class);

        verify(uriFetcher, only()).htmlGet(eq("res:whatIsThis"));
    }

    @Test
    public void whenResolverThrowsException_shouldEncapsulateInResolverException() throws IOException {
        when(uriFetcher.htmlGet(anyString())).thenThrow(UnknownException.class);
        expectedException.expect(ResolverException.class);
        expectedException.expectCause(any(UnknownException.class));

        alchemist.parseUrl("any", Element.class);
    }
}
