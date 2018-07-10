package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.doubles.NestedRootModel;
import com.brunodles.jsoupparser.doubles.SimpleModel;
import com.brunodles.jsoupparser.exceptions.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.io.IOException;

import static com.brunodles.test.ResourceLoader.readResourceText;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class JsoupParserTest {

    private static final JsoupParser jsoupParser = new JsoupParser();

    public static class WhenParseSimpleHtml {

        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        private SimpleModel simpleModel;

        @Before
        public void parseHtmlToSimpleModel() throws IOException {
            simpleModel = jsoupParser.parseHtml(readResourceText("simple.html"), SimpleModel.class);
        }

        @Test
        public void shouldBeAbleToReadContentFromBody() {
            assertEquals("wow", simpleModel.span123());
        }

        @Test
        public void shouldBeAbleToReadContentFromTitle() {
            assertEquals("Jsoup Parser", simpleModel.title());
        }

        @Test
        public void shouldReturnTheSameValueForMultipleCalls() {
            assertEquals(simpleModel.title(), simpleModel.title());
        }

        @Test
        public void shouldReturnDifferentHashCodeForEachInstance() throws IOException {
            SimpleModel otherObjectFromSameClass = jsoupParser.parseHtml(readResourceText("simple.html"), SimpleModel.class);
            assertNotEquals(simpleModel.hashCode(), otherObjectFromSameClass.hashCode());
        }

        @SuppressWarnings("EqualsWithItself")
        @Test
        public void shouldBeEqualsToSameInstance() {
            assertTrue(simpleModel.equals(simpleModel));
        }

        @Test
        public void shouldNotBeEqualsToOtherInstance() throws IOException {
            SimpleModel otherObjectFromSameClass = jsoupParser.parseHtml(readResourceText("simple.html"), SimpleModel.class);
            assertFalse(simpleModel.equals(otherObjectFromSameClass));
        }

        @SuppressWarnings("ObjectEqualsNull")
        @Test
        public void shouldNotBeEqualsToNull() {
            assertFalse(simpleModel.equals(null));
        }

        @SuppressWarnings("EqualsBetweenInconvertibleTypes")
        @Test
        public void shouldNotBeEqualsToOtherObject() {
            assertFalse(simpleModel.equals("wow"));
        }

        @Test
        public void shouldReturnMessageForToString() {
            assertEquals("Proxy for \"com.brunodles.jsoupparser.doubles.SimpleModel\".", simpleModel.toString());
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

    public static class WhenParseNestedContent {

        private NestedRootModel rootModel;

        @Before
        public void parseHtmlToNestedRootModel() throws IOException {
            rootModel = jsoupParser.parseHtml(readResourceText("nested_root.html"), NestedRootModel.class);
        }

        @Test
        public void shouldBeAbleToGetChild() {
            NestedRootModel.NestedChildModel child = rootModel.child();

            assertNotNull(child);
        }

        @Test
        public void shouldBeAbleToGetChildsContent() {
            NestedRootModel.NestedChildModel child = rootModel.child();

            String result = child.span123();
            assertEquals("look at this", result);
        }
    }

}
