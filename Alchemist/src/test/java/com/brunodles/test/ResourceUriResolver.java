package com.brunodles.test;

import com.brunodles.jsoupparser.UriResolver;

import java.io.IOException;

public class ResourceUriResolver implements UriResolver {

    @Override
    public String htmlGet(String uri) throws IOException {
        return ResourceLoader.readResourceText(uri);
    }
}
