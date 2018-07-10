package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.ElementCollector;
import com.brunodles.jsoupparser.JsoupParser;
import org.jsoup.nodes.Element;

import java.lang.reflect.Method;

public class CollectorWithConstructorParameters implements ElementCollector {

    public CollectorWithConstructorParameters(String parameter) {
    }

    @Override
    public Object collect(JsoupParser jsoupParser, Element value, Method method) {
        return null;
    }
}
