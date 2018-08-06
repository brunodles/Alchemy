package com.brunodles.alchemist.selector;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmutation;
import org.jsoup.select.Elements;

public class SelectorTransmutation implements Transmutation<AnnotationInvocation<Selector, Elements>, Elements> {

    @Override
    public Elements transform(AnnotationInvocation<Selector, Elements> value) {
        final String selector = value.annotation.value();
        final Elements result;
        if (value.result == null)
            result = value.documentSelect(selector);
        else
            result = value.result.select(selector);
        if (result == null || result.isEmpty())
            throw new InvalidSelectorException(value.methodName, selector);
        return result;
    }
}
