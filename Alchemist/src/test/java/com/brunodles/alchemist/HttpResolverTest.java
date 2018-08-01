package com.brunodles.alchemist;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class HttpResolverTest {

    @Rule
    public WireMockRule wireMock = new WireMockRule(8089);

    private HttpFetcher resolver = new HttpFetcher();
    private String url;

    @Before
    public void setUp() {
        url = wireMock.url("");
    }

    @Test
    public void whenRequest() throws IOException {
        wireMock.stubFor(get(anyUrl())
                .willReturn(aResponse().withBody("Batman"))
        );
        String result = resolver.htmlGet(url);

        assertEquals("Batman", result);
    }

}
