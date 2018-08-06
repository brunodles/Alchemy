package com.brunodles.alchemist.usevalueof;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.TransmutationRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

@RunWith(JUnit4.class)
public class UseValueOfTransmutationTest {

    @Rule
    public TransmutationRule<UseValueOf> transmutationRule = new TransmutationRule(new UseValueOfTransmutation());

    @Before
    public void setup() {
        transmutationRule.whenMethodNameReturn("methodName");
    }

    @Test
    public void whenParseToInteger_shouldParse() {
        AnnotationInvocation<UseValueOf, List<String>> invocation = transmutationRule
                .buildInvocation(Integer.class, "123");

        List<Integer> result = transmutationRule.transform(invocation);

        assertEquals(new Integer(123), result.get(0));
    }

    @Test
    public void whenParseToLong_shouldParse() {
        AnnotationInvocation<UseValueOf, List<String>> invocation = transmutationRule.buildInvocation(Long.class,
                "2132312132331232121");

        List<Long> result = transmutationRule.transform(invocation);

        assertEquals(new Long(2132312132331232121L), result.get(0));
    }

    @Test
    public void whenParseToFloat_shouldParse() {
        AnnotationInvocation<UseValueOf, List<String>> invocation = transmutationRule.buildInvocation(Float.class,
                "212121321321.3221332113321123213213");

        List<Float> result = transmutationRule.transform(invocation);

        assertEquals(212121321321.3221332113321123213213F, result.get(0), 0.0000000000000000000001F);
    }

    @Test(expected = RuntimeException.class)
    public void whenClassDoesNotHaveValueOfMethod_shouldThrowException() {
        AnnotationInvocation<UseValueOf, List<String>> invocation = transmutationRule
                .buildInvocation(Character.TYPE, "1231323");

        List<Character> result = transmutationRule.transform(invocation);

        fail();
    }

}
