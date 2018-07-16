package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.NestedCollector;
import com.brunodles.jsoupparser.collectors.TextElementCollector;
import com.brunodles.jsoupparser.transformers.TransformToFloat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public interface CollectionsModel {

    @CssSelector(selector = "ol#jvm_languages li", collector = TextElementCollector.class)
    ArrayList<String> jvmLanguagesArrayList();

    @CssSelector(selector = "ol#jvm_languages li", collector = TextElementCollector.class)
    HashSet<String> jvmLanguagesHashSet();

    @CssSelector(selector = "#games tr:has(td)", collector = NestedCollector.class)
    LinkedList<Games> gamesArrayList();

    interface Games {

        @CssSelector(selector = "td:first-child", collector = TextElementCollector.class)
        String name();

        @CssSelector(selector = "td:nth-child(2)", collector = TextElementCollector.class, transformer = TransformToFloat.class)
        Float price();
    }
}
