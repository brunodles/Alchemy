package com.brunodles.alchemist.selector;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmuter;
import com.brunodles.alchemist.transformers.TransformerFor;
import org.jsoup.select.Elements;

@TransformerFor(Selector.class)
public class SelectorTransmuter implements Transmuter<AnnotationInvocation<Selector, Elements>, Elements> {

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
