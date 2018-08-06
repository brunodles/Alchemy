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

import java.util.IllegalFormatConversionException;
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
    public void whenTransformString_shouldFormatResult() {
        transmutation.withAnnotation(StringFormat.class, "value", "This is the \"%s\".");
        AnnotationInvocation<StringFormat, List<String>> invocation = transmutation
                .buildInvocationWithList(String.class, "Input");

        List<String> result = transmutation.transform(invocation);

        assertEquals("This is the \"Input\".", result.get(0));
    }

    @Test
    public void whenTransformFloat_withDecimalParameters_shouldFormatResult() {
        transmutation.withAnnotation(StringFormat.class, "value", "Formatted value \"%#.2f\".");
        AnnotationInvocation<StringFormat, List<Float>> invocation = transmutation
                .buildInvocationWithList(String.class, 123.132132F);

        List<String> result = transmutation.transform(invocation);

        assertEquals("Formatted value \"123.13\".", result.get(0));
    }

    @Test
    public void whenTransformFloat_withDecimalParameters_andRoundingCondition_shouldFormatResultRoundedUp() {
        transmutation.withAnnotation(StringFormat.class, "value", "Formatted value \"%#.2f\".");
        AnnotationInvocation<StringFormat, List<Float>> invocation = transmutation
                .buildInvocationWithList(String.class, 123.137132F);

        List<String> result = transmutation.transform(invocation);

        assertEquals("Formatted value \"123.14\".", result.get(0));
    }

    @Test
    public void whenTransformDifferentObjectTypes_shouldUseToString() {
        transmutation.withAnnotation(StringFormat.class, "value", "Formatted value \"%s\".");
        AnnotationInvocation<StringFormat, List<Object>> invocation = transmutation.buildInvocationWithList(
                String.class, 12.321231132F, 13.09832190812390812309D, 987321931298731278L, "MyName");

        List<String> result = transmutation.transform(invocation);

        assertEquals("Formatted value \"12.321231\".", result.get(0));
        assertEquals("Formatted value \"13.098321908123909\".", result.get(1));
        assertEquals("Formatted value \"987321931298731278\".", result.get(2));
        assertEquals("Formatted value \"MyName\".", result.get(3));
    }

    @Test(expected = IllegalFormatConversionException.class)
    public void whenTransformWithInvalidFormatter_shouldThrowException() {
        transmutation.withAnnotation(StringFormat.class, "value", "Formatted value \"%#.2f\".");
        AnnotationInvocation<StringFormat, List<Object>> invocation = transmutation.buildInvocationWithList(
                String.class, "MyName");

        List<String> result = transmutation.transform(invocation);

        assertEquals("Formatted value \"not even hits here\".", result.get(0));
    }
}