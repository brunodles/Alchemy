package com.brunodles.alchemist.AlchemistTest;

import com.brunodles.alchemist.Alchemist;
import com.brunodles.alchemist.MethodToAnnotationInvocationHandler;
import com.brunodles.alchemist.HttpFetcher;
import com.brunodles.alchemist.UriFetcher;
import com.brunodles.alchemist.TransmutationsBook;
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
@PrepareForTest({Alchemist.Builder.class})
public class Builder {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    TransmutationsBook transmutationsBook;
    @Mock
    UriFetcher uriFetcher;
    @Mock
    MethodToAnnotationInvocationHandler invocationHandler;
    ClassLoader classLoader = this.getClass().getClassLoader();
    Alchemist mockAlchemist;

    @Before
    public void setup() throws Exception {
        // Just for coverage purposes, since we can't call real constructor
        mockAlchemist = spy(new Alchemist.Builder()
                .transformers(transmutationsBook)
                .classLoader(classLoader)
                .uriResolver(uriFetcher)
                .build());

        whenNew(Alchemist.class)
                .withAnyArguments()
//                .thenCallRealMethod() // this does not works
                .thenReturn(mockAlchemist);
        whenNew(MethodToAnnotationInvocationHandler.class)
                .withAnyArguments()
                .thenReturn(invocationHandler);
    }

    @Test
    public void whenBuild_withAllParameters_shouldPassTheyToConstructor() throws Exception {
        new Alchemist.Builder()
                .transformers(transmutationsBook)
                .classLoader(classLoader)
                .uriResolver(uriFetcher)
                .build();

        verifyNew(Alchemist.class).withArguments(invocationHandler, uriFetcher, classLoader, null);
    }

    @Test
    public void whenBuild_withoutClassLoader_shouldPassNull() throws Exception {
        new Alchemist.Builder()
                .transformers(transmutationsBook)
                .uriResolver(uriFetcher)
                .build();

        verifyNew(Alchemist.class).withArguments(invocationHandler, uriFetcher, null, null);
    }

    @Test
    public void whenBuild_withoutParameters_shouldPassDefaultOnes_andNullForClassLoader() throws Exception {
        HttpFetcher httpResolver = mock(HttpFetcher.class);
        whenNew(HttpFetcher.class)
                .withNoArguments()
                .thenReturn(httpResolver);

        new Alchemist.Builder()
                .build();

        verifyNew(Alchemist.class).withArguments(invocationHandler, httpResolver, null, null);
    }
}
