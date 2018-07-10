package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.NestedCollector;
import com.brunodles.jsoupparser.collectors.TextElementCollector;

public interface NestedRootModel {

    @CssSelector(selector = ".root",
            parser = NestedCollector.class)
    NestedChildModel child();

    interface NestedChildModel {
        @CssSelector(selector = "span.123",
                parser = TextElementCollector.class)
        String span123();

        @CssSelector(selector = "div.456",
                parser = TextElementCollector.class)
        String div456();
    }
}
