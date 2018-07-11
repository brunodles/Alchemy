package com.brunodles.jsoupparser.JsoupParserTest;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.doubles.ErrorModel;
import com.brunodles.jsoupparser.doubles.SimpleModel;
import com.brunodles.jsoupparser.exceptions.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static com.brunodles.test.ResourceLoader.readResourceText;
import static org.hamcrest.CoreMatchers.any;

public class WhenHaveError {

    private final JsoupParser jsoupParser = new JsoupParser();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ErrorModel simpleModel;

    @Before
    public void parseHtmlToSimpleModel() throws IOException {
        simpleModel = jsoupParser.parseHtml(readResourceText("simple.html"), ErrorModel.class);
    }

    @Test
    public void withoutAnnotation_shouldThrowMissingSelectorException() {
        expectedException.expect(MissingSelectorException.class);
        expectedException.expectMessage("Failed to get \"missingSelector\". Looks like it doesn't have CssSelector annotation.");

        String result = simpleModel.missingSelector();
    }

    @Test
    public void withInvalidSelector_shouldThrowInvalidSelectorException() {
        expectedException.expect(InvalidSelectorException.class);
        expectedException.expectMessage("Failed to get \"invalidSelector\". Can't find the selector \"h1.classNotFound\".");

        String result = simpleModel.invalidSelector();
    }

    @Test
    public void withCollectorThatHavePrivateConstructor_shouldThrowInvalidCollectorException() {
        expectedException.expect(InvalidCollectorException.class);
        expectedException.expectMessage("Failed to get \"collectorWithPrivateConstructor\". Can't create a instance of \"CollectorWithPrivateConstructor\".");
        expectedException.expectCause(any(IllegalAccessException.class));

        String result = simpleModel.collectorWithPrivateConstructor();
    }

    @Test
    public void withCollectorThatConstructorHaveParameters_shouldThrowInvalidCollectorException() {
        expectedException.expect(InvalidCollectorException.class);
        expectedException.expectMessage("Failed to get \"collectorWithConstructorParameters\". Can't create a instance of \"CollectorWithConstructorParameters\".");
        expectedException.expectCause(any(InstantiationException.class));

        String result = simpleModel.collectorWithConstructorParameters();
    }

    @Test
    public void withErrorsOnCollector_shouldThrowCollectorException() {
        expectedException.expect(CollectorException.class);
        expectedException.expectMessage("Failed to get \"collectorWithError\" using collector \"CollectorWithError\".");

        String result = simpleModel.collectorWithError();
    }

    @Test
    public void withVoidResult_shouldThrowInvalidResultException() {
        expectedException.expect(InvalidResultException.class);
        expectedException.expectMessage("Failed to get \"invalidResult\". The method have void result.");

        simpleModel.invalidResult();
    }

    @Test
    public void withTransformerThatHavePrivateConstructor_shouldThrowInvalidTransformerException() {
        expectedException.expect(InvalidTransformerException.class);
        expectedException.expectMessage("Failed to get \"transformerWithPrivateConstructor\". Can't create a instance of \"TransformerWithPrivateConstructor\".");
        expectedException.expectCause(any(IllegalAccessException.class));

        String result = simpleModel.transformerWithPrivateConstructor();
    }

    @Test
    public void withTransformerThatConstructorHaveParameters_shouldThrowInvalidTransformerException() {
        expectedException.expect(InvalidTransformerException.class);
        expectedException.expectMessage("Failed to get \"transformerWithConstructorParameters\". Can't create a instance of \"TransformerWithConstructorParameters\".");
        expectedException.expectCause(any(InstantiationException.class));

        String result = simpleModel.transformerWithConstructorParameters();
    }

    @Test
    public void withErrorOnTransformer_shouldThrowInvalidTransformerException() {
        expectedException.expect(TransformerException.class);
        expectedException.expectMessage("Failed to get \"transformerWithError\" using \"TransformerWithError\".");

        String result = simpleModel.transformerWithError();
    }

}
