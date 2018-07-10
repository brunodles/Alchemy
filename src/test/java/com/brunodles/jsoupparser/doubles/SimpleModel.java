package com.brunodles.jsoupparser.doubles;

import com.brunodles.jsoupparser.CssSelector;
import com.brunodles.jsoupparser.collectors.TextElementCollector;

public interface SimpleModel {
    @CssSelector(selector = "#123",
            parser = TextElementCollector.class)
    String span123();

    @CssSelector(selector = "head title",
            parser = TextElementCollector.class)
    String title();

    String missingSelector();

    @CssSelector(selector = "h1.classNotFound", parser = TextElementCollector.class)
    String invalidSelector();

    @CssSelector(selector = "#123", parser = CollectorWithPrivateConstructor.class)
    String collectorWithPrivateConstructor();

    @CssSelector(selector = "#123", parser = CollectorWithConstructorParameters.class)
    String collectorWithConstructorParameters();

    @CssSelector(selector = "#123", parser = CollectorWithError.class)
    String collectorWithError();

    @CssSelector(selector = "#123", parser = TextElementCollector.class)
    void invalidResult();

    @CssSelector(selector = "#123", parser = TextElementCollector.class, transformer = TransformerWithPrivateConstructor.class)
    String transformerWithPrivateConstructor();

    @CssSelector(selector = "#123", parser = TextElementCollector.class, transformer = TransformerWithConstructorParameters.class)
    String transformerWithConstructorParameters();

    @CssSelector(selector = "#123", parser = TextElementCollector.class, transformer = TransformerWithError.class)
    String transformerWithError();
}
