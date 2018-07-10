package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.ElementCollector;
import com.brunodles.jsoupparser.JsoupParser;
import org.jsoup.nodes.Element;

import java.lang.reflect.Method;

public class CollectorWithPrivateConstructor implements ElementCollector {

    private CollectorWithPrivateConstructor() {
    }

    @Override
    public Object collect(JsoupParser jsoupParser, Element value, Method method) {
        return null;
    }
}
