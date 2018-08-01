package com.brunodles.testhelpers;

import com.brunodles.alchemist.UriFetcher;

import java.io.IOException;

public class ResourceUriFetcher implements UriFetcher {

    @Override
    public String htmlGet(String uri) throws IOException {
        return ResourceLoader.readResourceText(uri);
    }
}
