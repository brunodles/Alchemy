package com.brunodles.alchemist.AlchemistTest;

import com.brunodles.alchemist.Alchemist;
import com.brunodles.alchemist.collectors.TextCollector;
import com.brunodles.alchemist.doubles.*;
import com.brunodles.alchemist.exceptions.ResultException;
import com.brunodles.alchemist.selector.InvalidSelectorException;
import com.brunodles.alchemist.selector.MissingSelectorException;
import com.brunodles.alchemist.selector.Selector;
import com.brunodles.alchemist.exceptions.TransformerException;
import com.brunodles.alchemist.transformers.Transformers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static com.brunodles.testhelpers.ResourceLoader.readResourceText;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

@RunWith(JUnit4.class)
public class WhenHaveError {

    private final Alchemist alchemist = new Alchemist.Builder()
            .transformers(new Transformers.Builder()
                    .add(TransmuterWithPrivateConstructor.class)
                    .add(TransmuterWithConstructorParameters.class)
                    .add(TransmuterWithError.class)
                    .build())
            .build();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ErrorModel errorModel;

    @Before
    public void parseHtmlToSimpleModel() throws IOException {
        errorModel = alchemist.parseHtml(readResourceText("simple.html"), ErrorModel.class);
    }

    @Test
    public void withoutAnnotation_shouldThrowMissingSelectorException() {
        expectedException.expect(MissingSelectorException.class);
        expectedException
                .expectMessage("Failed to get \"missingSelector\". Looks like it doesn't have Selector annotation.");

        String result = errorModel.missingSelector();
    }

    @Test
    public void withInvalidSelector_shouldThrowInvalidSelectorException() {
        expectedException.expect(TransformerException.class);
        expectedException.expectCause(AllOf.allOf(
                hasMessage(CoreMatchers.containsString(
                        "Failed to get \"invalidSelector\". Can't find the selector \"h1.classNotFound\".")),
                instanceOf(InvalidSelectorException.class)
        ));

        String result = errorModel.invalidSelector();
    }

    @Test
    public void withVoidReturn_shouldThrowInvalidResultException() {
        expectedException.expect(ResultException.class);
        expectedException.expectMessage("Called method should not have void as return.");

        errorModel.invalidResult();
    }

    @Test
    public void whenReturnConstructorIsPrivate_shouldThrowInvalidResultException() {
        expectedException.expect(ResultException.class);
        expectedException.expectMessage("Can't create an instance of \"CollectionWithPrivateConstructor\".");
        expectedException.expectCause(any(IllegalAccessException.class));

        errorModel.collectionWithPrivateConstructor();
    }

    @Test
    public void whenReturnConstructorHaveParameters_shouldThrowResultException() {
        expectedException.expect(ResultException.class);
        expectedException.expectMessage("Can't create an instance of \"CollectionWithConstructorParameters\".");
        expectedException.expectCause(any(InstantiationException.class));

        errorModel.collectionWithConstructorParameters();
    }

    @Test
    public void withTransformerThatHavePrivateConstructor_shouldThrowInvalidTransformerException() {
        expectedException.expect(TransformerException.class);
        expectedException.expectMessage(
                "Can't create \"TransmuterWithPrivateConstructor\". Check if it have private constructor or if it's " +
                        "constructor have parameters.");
        expectedException.expectCause(any(IllegalAccessException.class));

        String result = errorModel.transformerWithPrivateConstructor();
    }

    @Test
    public void withTransformerThatConstructorHaveParameters_shouldThrowInvalidTransformerException() {
        expectedException.expect(TransformerException.class);
        expectedException.expectMessage(
                "Can't create \"TransmuterWithConstructorParameters\". Check if it have private constructor or if " +
                        "it's constructor have parameters.");
        expectedException.expectCause(any(InstantiationException.class));

        String result = errorModel.transformerWithConstructorParameters();
    }

    @Test
    public void withErrorOnTransformer_shouldThrowInvalidTransformerException() {
        expectedException.expect(TransformerException.class);
        expectedException.expectMessage("The transformer \"TransmuterWithError\" can't transform \"[wow]\".");

        String result = errorModel.transformerWithError();
    }


    public interface ErrorModel {

        String missingSelector();

        @Selector("h1.classNotFound")
        @TextCollector
        String invalidSelector();

        @Selector("#123")
        @TextCollector
        void invalidResult();

        @Selector("#123")
        @TextCollector
        @TransmuterWithPrivateConstructor.Annotation
        String transformerWithPrivateConstructor();

        @Selector("#123")
        @TextCollector
        @TransmuterWithConstructorParameters.Annotation
        String transformerWithConstructorParameters();

        @Selector("#123")
        @TextCollector
        @TransmuterWithError.Annotation
        String transformerWithError();

        @Selector("span")
        @TextCollector
        CollectionWithPrivateConstructor collectionWithPrivateConstructor();

        @Selector("span")
        @TextCollector
        CollectionWithConstructorParameters collectionWithConstructorParameters();
    }
}
