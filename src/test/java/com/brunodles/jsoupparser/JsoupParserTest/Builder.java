package com.brunodles.jsoupparser.JsoupParserTest;

import com.brunodles.jsoupparser.AnnotationInvocationHandler;
import com.brunodles.jsoupparser.HttpResolver;
import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.UriResolver;
import com.brunodles.jsoupparser.transformers.Transformers;
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
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsoupParser.Builder.class})
public class Builder {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    Transformers transformers;
    @Mock
    UriResolver uriResolver;
    @Mock
    AnnotationInvocationHandler invocationHandler;
    ClassLoader classLoader = this.getClass().getClassLoader();
    JsoupParser mockJsoupParser;

    @Before
    public void setup() throws Exception {
        // Just for coverage purposes, since we can't call real constructor
        mockJsoupParser = spy(new JsoupParser.Builder()
                .transformers(transformers)
                .classLoader(classLoader)
                .uriResolver(uriResolver)
                .build());

        whenNew(JsoupParser.class)
                .withAnyArguments()
//                .thenCallRealMethod() // this does not works
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
