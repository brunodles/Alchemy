package com.brunodles.alchemist.AlchemistTest;

import com.brunodles.alchemist.Alchemist;
import com.brunodles.alchemist.Transmuter;
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

    Alchemist alchemist = new Alchemist.Builder()
            .transformers(new Transformers.Builder()
                    .add(EmptyTransmuter.class)
                    .build())
            .build();

    @Test
    public void shouldReturnNull() {
        SampleModel sampleModel = alchemist.parseHtml("", SampleModel.class);
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
    public static class EmptyTransmuter implements Transmuter {

        @Override
        public Object transform(Object value) {
            return new ArrayList<>();
        }
    }
}
