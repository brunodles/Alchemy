package com.brunodles.jsoupparser.bigannotation;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.AttrElementCollector;
import com.brunodles.jsoupparser.collectors.AttrFollowUrlElementCollector;

public interface FollowModel {

    @CssSelector(selector = ".something a#simple", collector = AttrFollowUrlElementCollector.class)
    @AttrElementCollector.Settings(attrName = "href")
    SimpleModel simple();

    @CssSelector(selector = ".something a#collections", collector = AttrFollowUrlElementCollector.class)
    @AttrElementCollector.Settings(attrName = "href")
    CollectionsModel collections();
}
