package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.collectors.AttrCollector;
import com.brunodles.jsoupparser.collectors.TextCollector;
import com.brunodles.jsoupparser.navigate.Navigate;
import com.brunodles.jsoupparser.nested.Nested;
import com.brunodles.jsoupparser.selector.Selector;
import com.brunodles.jsoupparser.withtype.WithTransformer;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class TransformersTest {

    private static final Class<? extends Annotation>[] DEFAULT_ANNOTATIONS = new Class[]{
            TextCollector.class,
            AttrCollector.class,
            Navigate.class,
            Nested.class,
            Selector.class,
            WithTransformer.class
    };

    @Test
    public void whenBuild_shouldHaveDefaultAnnotations() {
        Transformers transformers = new Transformers.Builder().build();

        for (Class<? extends Annotation> annotation : DEFAULT_ANNOTATIONS)
            assertNotNull(transformers.transformerFor(mock(annotation)));
    }

    @Test
    public void whenCustomTransformer_whenBuild_shouldOverrideDefaultOne() {
        Transformers transformers = new Transformers.Builder().add(CustomTransformer.class).build();
        Class<? extends Transformer> transformerClass = transformers.transformerFor(mock(Selector.class));
        assertEquals(CustomTransformer.class, transformerClass);
    }

    @Test(expected = RuntimeException.class)
    public void givenTransformersBuilder_whenAddNull_shouldThrow() {
        new Transformers.Builder().add(null).build();
    }

    @Test(expected = RuntimeException.class)
    public void whenTransformerForInvalidAnnotation_shouldThrowException() {
        Transformers transformers = new Transformers.Builder().build();
        Class<? extends Transformer> transformer = transformers.transformerFor(mock(UnknownAnnotation.class));
    }

    @interface UnknownAnnotation {
    }

    @TransformerFor(Selector.class)
    private static class CustomTransformer implements Transformer<AnnotationInvocation<Selector, Document>, Elements> {

        @Override
        public Elements transform(AnnotationInvocation<Selector, Document> value) {
            return null;
        }
    }
}
