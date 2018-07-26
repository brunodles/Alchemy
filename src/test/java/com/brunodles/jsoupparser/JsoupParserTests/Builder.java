package com.brunodles.jsoupparser.JsoupParserTests;

import com.brunodles.jsoupparser.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsoupParser.class, JsoupParser.Builder.class})
public class Builder {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    Transformers transformers;
    @Mock
    UriResolver uriResolver;
    @Mock
    ClassLoader classLoader;
    @Mock
    JsoupParser mockJsoupParser;
    @Mock
    AnnotationInvocationHandler invocationHandler;

    @Before
    public void setup() throws Exception {
        whenNew(JsoupParser.class)
                .withAnyArguments()
                .thenReturn(mockJsoupParser);
        whenNew(AnnotationInvocationHandler.class)
                .withAnyArguments()
                .thenReturn(invocationHandler);
    }

    @Test
    public void whenBuild_withAllParameters_shouldPassTheyToConstructor() throws Exception {
        new JsoupParser.Builder()
                .transformers(transformers)
                .classLoader(classLoader)
                .uriResolver(uriResolver)
                .build();

        verifyNew(JsoupParser.class).withArguments(invocationHandler, uriResolver, classLoader, null);
    }

    @Test
    public void whenBuild_withoutClassLoader_shouldPassNull() throws Exception {
        new JsoupParser.Builder()
                .transformers(transformers)
                .uriResolver(uriResolver)
                .build();

        verifyNew(JsoupParser.class).withArguments(invocationHandler, uriResolver, null, null);
    }

    @Test
    public void whenBuild_withoutParameters_shouldPassDefaultOnes_andNullForClassLoader() throws Exception {
        HttpResolver httpResolver = mock(HttpResolver.class);
        whenNew(HttpResolver.class)
                .withNoArguments()
                .thenReturn(httpResolver);

        new JsoupParser.Builder()
                .build();

        verifyNew(JsoupParser.class).withArguments(invocationHandler, httpResolver, null, null);
    }
}
