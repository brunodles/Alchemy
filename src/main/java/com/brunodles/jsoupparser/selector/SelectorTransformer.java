package com.brunodles.jsoupparser.selector;

import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.exceptions.InvalidSelectorException;
import com.brunodles.jsoupparser.AnnotationInvocation;
import com.brunodles.jsoupparser.TransformerFor;
import org.jsoup.select.Elements;

@TransformerFor(Selector.class)
public class SelectorTransformer implements Transformer<AnnotationInvocation<Selector, Elements>, Elements> {

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
