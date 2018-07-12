package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.TextElementCollector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface CollectionsModel {

    @CssSelector(selector = "ol#jvm_languages li", parser = TextElementCollector.class)
    ArrayList<String> jvmLanguagesArrayList();

    @CssSelector(selector = "ol#jvm_languages li", parser = TextElementCollector.class)
    HashSet<String> jvmLanguagesHashSet();
}
