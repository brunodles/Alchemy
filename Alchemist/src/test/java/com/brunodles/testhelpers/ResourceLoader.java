package com.brunodles.testhelpers;

import java.io.IOException;
import java.io.InputStream;

public class ResourceLoader {

    public static String readResourceText(String resourcePath) throws IOException {
        InputStream inputStream = ResourceLoader.class.getClassLoader()
                .getResourceAsStream(resourcePath);

        return inputStreamToString(inputStream);
    }

    public static String inputStreamToString(InputStream is) throws IOException {
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
