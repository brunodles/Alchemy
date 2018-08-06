package com.brunodles.alchemist.AlchemistTest;

import com.brunodles.alchemist.Alchemist;
import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmutation;
import com.brunodles.alchemist.transformers.TransmutationsBook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class WhenLastResultIsEmpty {

    Alchemist alchemist = new Alchemist.Builder()
            .transformers(new TransmutationsBook.Builder()
                    .add(EmptyTransmutation.class)
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

    public static class EmptyTransmutation
            implements Transmutation<AnnotationInvocation<EmptyList, List<Object>>, List<Object>> {

        @Override
        public List<Object> transform(AnnotationInvocation<EmptyList, List<Object>> value) {
            return new ArrayList<>();
        }
    }
}
