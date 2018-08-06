package com.brunodles.alchemist.stringformat;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.TransmutationRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class StringFormatTransmutationTest {


    @Rule
    public TransmutationRule<StringFormat> transmutation = new TransmutationRule(new StringFormatTransmutation());

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void whenTransform_shouldFormatResult() {
        transmutation.withAnnotation(StringFormat.class, "value", "This is the \"%s\".");
        AnnotationInvocation<StringFormat, List<String>> invocation = transmutation
                .buildInvocationWithList(String.class, "Input");

        List<String> result = transmutation.transform(invocation);

        assertEquals("This is the \"Input\".", result.get(0));
    }
}