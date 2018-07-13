package com.brunodles.jsoupparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class HttpResolver implements UriResolver {

    @Override
    public String htmlGet(String uri) throws IOException {
        HttpURLConnection connect = (HttpURLConnection) new URL(uri).openConnection();
        InputStream inputStream = connect.getInputStream();
        return inputStreamToString(inputStream);
    }

    private static String inputStreamToString(InputStream is) throws IOException {
        final int PKG_SIZE = 1024;
        byte[] data = new byte[PKG_SIZE];
        StringBuilder buffer = new StringBuilder(PKG_SIZE * 10);
        int size;

        size = is.read(data, 0, data.length);
        while (size > 0) {
            String str = new String(data, 0, size);
            buffer.append(str);
            size = is.read(data, 0, data.length);
        }
        return buffer.toString();
    }
}
