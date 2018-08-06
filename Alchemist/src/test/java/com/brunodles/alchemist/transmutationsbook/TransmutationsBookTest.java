package com.brunodles.alchemist.transmutationsbook;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.AnnotationTransmutation;
import com.brunodles.alchemist.Transmutation;
import com.brunodles.alchemist.TransmutationsBook;
import com.brunodles.alchemist.collectors.AttrCollector;
import com.brunodles.alchemist.collectors.TextCollector;
import com.brunodles.alchemist.navigate.Navigate;
import com.brunodles.alchemist.nested.Nested;
import com.brunodles.alchemist.selector.Selector;
import com.brunodles.alchemist.withtransformer.WithTransformer;
import org.jsoup.nodes.Document;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class TransmutationsBookTest {

    @SuppressWarnings("unchecked")
    private static final Class<? extends Annotation>[] DEFAULT_ANNOTATIONS = new Class[]{
            TextCollector.class,
            AttrCollector.class,
            Navigate.class,
            Nested.class,
            Selector.class,
            WithTransformer.class
    };
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void whenBuild_shouldHaveDefaultAnnotations() {
        TransmutationsBook transmutationsBook = new TransmutationsBook.Builder().build();

        for (Class<? extends Annotation> annotation : DEFAULT_ANNOTATIONS)
            assertNotNull(transmutationsBook.transmutationFor(mock(annotation)));
    }

    @Test
    public void whenCustomTransformer_whenBuild_shouldOverrideDefaultOne() {
        TransmutationsBook transmutationsBook = new TransmutationsBook.Builder().add(CustomTransmutation.class).build();
        Class<? extends Transmutation> transformerClass = transmutationsBook.transmutationFor(mock(Selector.class));
        assertEquals(CustomTransmutation.class, transformerClass);
    }

    @Test
    public void givenTransformersBuilder_whenAddNull_shouldThrow() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Null is not a valid transformer.");

        new TransmutationsBook.Builder().add(null);
    }

    @Test
    public void whenAddInvalidTransmutation_shouldThrowException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Transmutation should follow these parameters: " +
                "\"Transmutation<AnnotationInvocation<Annotation, Input>, Output>\"");

        new TransmutationsBook.Builder().add(InvalidTransmutation.class);
    }

    @interface UnknownAnnotation {
    }

    private static class CustomTransmutation
            implements AnnotationTransmutation<Selector, Document, Elements> {

        @Override
        public Elements transform(AnnotationInvocation<Selector, Document> value) {
            return null;
        }
    }

    private static class InvalidTransmutation implements Transmutation {

        @Override
        public Object transform(Object value) {
            return null;
        }
    }
}
