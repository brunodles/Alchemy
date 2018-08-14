package com.brunodles.alchemist.regex;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.TransmutationRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.regex.PatternSyntaxException;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class RegexTransmutationTest {

    @Rule
    public TransmutationRule<Regex> transmutationRule = new TransmutationRule(new RegexTransmutation());

    @Before
    public void setup() {
        transmutationRule.whenMethodNameReturn("methodName");
    }

    @Test
    public void whenExtractContentFromString_shuoldReturnResultsFlated() {
        transmutationRule.withAnnotation(Regex.class, "value", "(?:\\W|^)?(m\\w+)(?:\\W|$)");
        AnnotationInvocation<Regex, List<String>> invocation = transmutationRule
                .buildInvocationWithList(String.class, "blablabla mainContent ignore",
                        "my <- these three will be included -> mock, magic");

        List<String> result = transmutationRule.transform(invocation);

        assertEquals("mainContent", result.get(0));
        assertEquals("my", result.get(1));
        assertEquals("mock", result.get(2));
        assertEquals("magic", result.get(3));
    }

    @Test(expected = PatternSyntaxException.class)
    public void whenRegexIsInvalid_shouldThrowException() {
        transmutationRule.withAnnotation(Regex.class, "value", "\\m(");
        AnnotationInvocation<Regex, List<String>> invocation = transmutationRule
                .buildInvocationWithList(String.class, "blablabla mainContent ignore");

        transmutationRule.transform(invocation);
    }
}