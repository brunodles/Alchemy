package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.NestedCollector;
import com.brunodles.jsoupparser.collectors.TextElementCollector;
import com.brunodles.jsoupparser.transformers.TransformToFloat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public interface CollectionsModel {

    @CssSelector(selector = "ol#jvm_languages li", parser = TextElementCollector.class)
    ArrayList<String> jvmLanguagesArrayList();

    @CssSelector(selector = "ol#jvm_languages li", parser = TextElementCollector.class)
    HashSet<String> jvmLanguagesHashSet();

    @CssSelector(selector = "#games tr:has(td)", parser = NestedCollector.class)
    LinkedList<Games> gamesArrayList();

    interface Games {

        @CssSelector(selector = "td:first-child", parser = TextElementCollector.class)
        String name();

        @CssSelector(selector = "td:nth-child(2)", parser = TextElementCollector.class, transformer = TransformToFloat.class)
        Float price();
    }
}
