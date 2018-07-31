package com.brunodles.jsoupparser.JsoupParserTest;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.transformers.TransformerFor;
import com.brunodles.jsoupparser.transformers.Transformers;
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
