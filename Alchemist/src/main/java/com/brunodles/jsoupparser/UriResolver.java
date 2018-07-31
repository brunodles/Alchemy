package com.brunodles.jsoupparser;

import java.io.IOException;

public interface UriResolver {

    String htmlGet(String uri) throws IOException;
}
