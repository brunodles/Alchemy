package com.brunodles.alchemist.stringformat;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.AnnotationTransmutation;

import java.util.ArrayList;
import java.util.List;

public class StringFormatTransmutation implements AnnotationTransmutation<StringFormat, List<Object>, List<String>> {

    @Override
    public List<String> transform(AnnotationInvocation<StringFormat, List<Object>> value) {
        ArrayList<String> result = new ArrayList<>();
        String format = value.annotation.value();
        for (Object o : value.result)
            result.add(String.format(format, o));
        return result;
    }
}
