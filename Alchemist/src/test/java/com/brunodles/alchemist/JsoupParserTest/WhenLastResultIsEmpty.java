package com.brunodles.alchemist.JsoupParserTest;

import com.brunodles.alchemist.JsoupParser;
import com.brunodles.alchemist.Transformer;
import com.brunodles.alchemist.transformers.TransformerFor;
import com.brunodles.alchemist.transformers.Transformers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

@RunWith(JUnit4.class)
public class WhenLastResultIsEmpty {

    JsoupParser jsoupParser = new JsoupParser.Builder()
            .transformers(new Transformers.Builder()
                    .add(EmptyTransformer.class)
                    .build())
            .build();

    @Test
    public void shouldReturnNull() {
        SampleModel sampleModel = jsoupParser.parseHtml("", SampleModel.class);
        Assert.assertNull(sampleModel.empty());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface EmptyList {
    }

    public interface SampleModel {
        @EmptyList
        String empty();
    }

    @TransformerFor(EmptyList.class)
    public static class EmptyTransformer implements Transformer {

        @Override
        public Object transform(Object value) {
            return new ArrayList<>();
        }
    }
}
