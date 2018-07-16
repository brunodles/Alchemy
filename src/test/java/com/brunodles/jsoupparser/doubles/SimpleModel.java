package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.AttrElementCollector;
import com.brunodles.jsoupparser.collectors.TextElementCollector;

public interface SimpleModel {
    @CssSelector(selector = "#123",
            collector = TextElementCollector.class)
    String span123();

    @CssSelector(selector = "head title",
            collector = TextElementCollector.class)
    String title();

    @CssSelector(selector = "#123",
            collector = AttrElementCollector.class)
    @AttrElementCollector.Settings(attrName = "class")
    String magic();

    @CssSelector(selector = "#123",
            collector = AttrElementCollector.class)
    @AttrElementCollector.Settings(attrName = "data-key")
    String dataKey();
}
