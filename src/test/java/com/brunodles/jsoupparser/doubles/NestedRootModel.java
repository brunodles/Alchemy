package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.NestedCollector;
import com.brunodles.jsoupparser.collectors.TextElementCollector;

public interface NestedRootModel {

    @CssSelector(selector = ".root",
            collector = NestedCollector.class)
    NestedChildModel child();

    interface NestedChildModel {
        @CssSelector(selector = "span.123",
                collector = TextElementCollector.class)
        String span123();

        @CssSelector(selector = "div.456",
                collector = TextElementCollector.class)
        String div456();
    }
}
