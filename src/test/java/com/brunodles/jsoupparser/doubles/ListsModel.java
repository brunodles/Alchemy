package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.TextElementCollector;

import java.util.ArrayList;
import java.util.List;

public interface ListsModel {

    @CssSelector(selector = "ol#jvm_languages li", parser = TextElementCollector.class)
    ArrayList<String> jvmLanguagesList();
}
