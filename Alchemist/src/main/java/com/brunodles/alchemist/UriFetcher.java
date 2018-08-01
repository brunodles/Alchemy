package com.brunodles.alchemist;

import java.io.IOException;

public interface UriFetcher {

    String htmlGet(String uri) throws IOException;
}
