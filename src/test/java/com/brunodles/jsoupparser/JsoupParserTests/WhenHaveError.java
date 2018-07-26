package com.brunodles.jsoupparser.JsoupParserTests;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.exceptions.InvalidResultException;
import com.brunodles.jsoupparser.selector.InvalidSelectorException;
import com.brunodles.jsoupparser.selector.MissingSelectorException;
import com.brunodles.jsoupparser.collectors.TextCollector;
import com.brunodles.jsoupparser.selector.Selector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static com.brunodles.test.ResourceLoader.readResourceText;

@RunWith(JUnit4.class)
public class WhenHaveError {

    private final JsoupParser jsoupParser = new JsoupParser();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ErrorModel errorModel;

    @Before
    public void parseHtmlToSimpleModel() throws IOException {
        errorModel = jsoupParser.parseHtml(readResourceText("simple.html"), ErrorModel.class);
    }

    @Test
    public void withoutAnnotation_shouldThrowMissingSelectorException() {
        expectedException.expect(MissingSelectorException.class);
        expectedException.expectMessage("Failed to get \"missingSelector\". Looks like it doesn't have Selector annotation.");

        String result = errorModel.missingSelector();
    }

    @Test
    public void withInvalidSelector_shouldThrowInvalidSelectorException() {
        expectedException.expect(InvalidSelectorException.class);
        expectedException.expectMessage("Failed to get \"invalidSelector\". Can't find the selector \"h1.classNotFound\".");

        String result = errorModel.invalidSelector();
    }

//    @Test
//    public void withCollectorThatHavePrivateConstructor_shouldThrowInvalidCollectorException() {
//        expectedException.expect(InvalidCollectorException.class);
//        expectedException.expectMessage("Failed to get \"collectorWithPrivateConstructor\". Can't create an instance of \"CollectorWithPrivateConstructor\".");
//        expectedException.expectCause(any(IllegalAccessException.class));
//
//        String result = errorModel.collectorWithPrivateConstructor();
//    }

//    @Test
//    public void withCollectorThatConstructorHaveParameters_shouldThrowInvalidCollectorException() {
//        expectedException.expect(InvalidCollectorException.class);
//        expectedException.expectMessage("Failed to get \"collectorWithConstructorParameters\". Can't create an instance of \"CollectorWithConstructorParameters\".");
//        expectedException.expectCause(any(InstantiationException.class));
//
//        String result = errorModel.collectorWithConstructorParameters();
//    }

//    @Test
//    public void withErrorsOnCollector_shouldThrowCollectorException() {
//        expectedException.expect(CollectorException.class);
//        expectedException.expectMessage("Failed to get \"collectorWithError\" using collector \"CollectorWithError\".");
//
//        String result = errorModel.collectorWithError();
//    }

    @Test
    public void withVoidReturn_shouldThrowInvalidResultException() {
        expectedException.expect(InvalidResultException.class);
        expectedException.expectMessage("Failed to get \"invalidResult\". The method have void return.");

        errorModel.invalidResult();
    }

//    @Test
//    public void whenReturnConstructorIsPrivate_shouldThrowInvalidResultException() {
//        expectedException.expect(ResultException.class);
//        expectedException.expectMessage("Failed to get \"collectionWithPrivateConstructor\". Can't create an instance of \"CollectionWithPrivateConstructor\".");
//        expectedException.expectCause(any(IllegalAccessException.class));
//
//        errorModel.collectionWithPrivateConstructor();
//    }

//    @Test
//    public void whenReturnConstructorHaveParameters_shouldThrowResultException() {
//        expectedException.expect(ResultException.class);
//        expectedException.expectMessage("Failed to get \"collectionWithConstructorParameters\". Can't create an instance of \"CollectionWithConstructorParameters\".");
//        expectedException.expectCause(any(InstantiationException.class));
//
//        errorModel.collectionWithConstructorParameters();
//    }

//    @Test
//    public void withTransformerThatHavePrivateConstructor_shouldThrowInvalidTransformerException() {
//        expectedException.expect(InvalidTransformerException.class);
//        expectedException.expectMessage("Failed to get \"transformerWithPrivateConstructor\". Can't create an instance of \"TransformerWithPrivateConstructor\".");
//        expectedException.expectCause(any(IllegalAccessException.class));
//
//        String result = errorModel.transformerWithPrivateConstructor();
//    }

//    @Test
//    public void withTransformerThatConstructorHaveParameters_shouldThrowInvalidTransformerException() {
//        expectedException.expect(InvalidTransformerException.class);
//        expectedException.expectMessage("Failed to get \"transformerWithConstructorParameters\". Can't create an instance of \"TransformerWithConstructorParameters\".");
//        expectedException.expectCause(any(InstantiationException.class));
//
//        String result = errorModel.transformerWithConstructorParameters();
//    }

//    @Test
//    public void withErrorOnTransformer_shouldThrowInvalidTransformerException() {
//        expectedException.expect(TransformerException.class);
//        expectedException.expectMessage("Failed to get \"transformerWithError\" using \"TransformerWithError\".");
//
//        String result = errorModel.transformerWithError();
//    }


    public interface ErrorModel {

        String missingSelector();

        @Selector("h1.classNotFound")
        @TextCollector
        String invalidSelector();

//        @Selector("#123")
//        @CollectorWithPrivateConstructor
//        String collectorWithPrivateConstructor();

//        @Selector("#123")
//        @CollectorWithConstructorParameters
//        String collectorWithConstructorParameters();

//        @Selector("#123")
//        @CollectorWithError
//        String collectorWithError();

        @Selector("#123")
        @TextCollector
        void invalidResult();

//        @Selector("#123")
//        @TextCollector
//        @TransformerWithPrivateConstructor
//        String transformerWithPrivateConstructor();

//        @Selector("#123")
//        @TextCollector
//        @TransformerWithConstructorParameters
//        String transformerWithConstructorParameters();

//        @Selector("#123")
//        @TextCollector
//        @TransformerWithError
//        String transformerWithError();

//        @Selector("span")
//        @TextCollector
//        CollectionWithPrivateConstructor collectionWithPrivateConstructor();

//        @Selector("span")
//        @TextCollector
//        CollectionWithConstructorParameters collectionWithConstructorParameters();
    }
}
