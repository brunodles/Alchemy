package com.brunodles.jsoupparser.navigate;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.AnnotationInvocation;
import com.brunodles.jsoupparser.TransformerFor;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(Navigate.class)
public class NavigateTransformer<OUTPUT> implements Transformer<AnnotationInvocation<Navigate, List<String>>, List<OUTPUT>> {

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<Navigate, List<String>> value) {
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (String url : value.result) {
            OUTPUT output = (OUTPUT) value.proxyHandler.jsoupParser.parseUrl(url, value.getMethodRealReturnType());
            result.add(output);
        }
        return result;
    }
}
