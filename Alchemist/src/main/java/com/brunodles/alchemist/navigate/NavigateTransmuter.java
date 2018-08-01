package com.brunodles.alchemist.navigate;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmuter;
import com.brunodles.alchemist.transformers.TransformerFor;

import java.util.ArrayList;
import java.util.List;

@TransformerFor(Navigate.class)
public class NavigateTransmuter<OUTPUT> implements Transmuter<AnnotationInvocation<Navigate, List<String>>,
        List<OUTPUT>> {

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<Navigate, List<String>> value) {
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (String url : value.result) {
            OUTPUT output = (OUTPUT) value.proxyHandler.alchemist.parseUrl(url, value.getMethodRealReturnType());
            result.add(output);
        }
        return result;
    }
}
