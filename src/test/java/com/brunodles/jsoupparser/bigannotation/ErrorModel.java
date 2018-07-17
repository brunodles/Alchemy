package com.brunodles.jsoupparser.bigannotation;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.TextElementCollector;
import com.brunodles.jsoupparser.doubles.*;

public interface ErrorModel {

    String missingSelector();

    @CssSelector(selector = "h1.classNotFound", collector = TextElementCollector.class)
    String invalidSelector();

    @CssSelector(selector = "#123", collector = CollectorWithPrivateConstructor.class)
    String collectorWithPrivateConstructor();

    @CssSelector(selector = "#123", collector = CollectorWithConstructorParameters.class)
    String collectorWithConstructorParameters();

    @CssSelector(selector = "#123", collector = CollectorWithError.class)
    String collectorWithError();

    @CssSelector(selector = "#123", collector = TextElementCollector.class)
    void invalidResult();

    @CssSelector(selector = "#123", collector = TextElementCollector.class, transformer = TransformerWithPrivateConstructor.class)
    String transformerWithPrivateConstructor();

    @CssSelector(selector = "#123", collector = TextElementCollector.class, transformer = TransformerWithConstructorParameters.class)
    String transformerWithConstructorParameters();

    @CssSelector(selector = "#123", collector = TextElementCollector.class, transformer = TransformerWithError.class)
    String transformerWithError();

    @CssSelector(selector = "span", collector = TextElementCollector.class)
    CollectionWithPrivateConstructor collectionWithPrivateConstructor();

    @CssSelector(selector = "span", collector = TextElementCollector.class)
    CollectionWithConstructorParameters collectionWithConstructorParameters();
}
