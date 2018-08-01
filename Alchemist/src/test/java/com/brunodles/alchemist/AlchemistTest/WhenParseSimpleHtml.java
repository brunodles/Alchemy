package com.brunodles.alchemist.AlchemistTest;

import com.brunodles.alchemist.Alchemist;
import com.brunodles.alchemist.collectors.AttrCollector;
import com.brunodles.alchemist.collectors.TextCollector;
import com.brunodles.alchemist.doubles.TransformToFloat;
import com.brunodles.alchemist.selector.Selector;
import com.brunodles.alchemist.usevalueof.UseValueOf;
import com.brunodles.alchemist.withtransformer.WithTransformer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.brunodles.testhelpers.ResourceLoader.readResourceText;
import static org.junit.Assert.*;

public class WhenParseSimpleHtml {

    private final Alchemist alchemist = new Alchemist();
    private SimpleModel simpleModel;

    @Before
    public void parseHtmlToSimpleModel() throws IOException {
        simpleModel = alchemist.parseHtml(readResourceText("simple.html"), SimpleModel.class);
    }

    @Test
    public void shouldBeAbleToReadContentFromBody() {
        assertEquals("wow", simpleModel.span123());
    }

    @Test
    public void shouldBeAbleToReadContentFromTitle() {
        assertEquals("Jsoup Parser", simpleModel.title());
    }

    @Test
    public void shouldBeAbleToReadAttr() {
        assertEquals("magic", simpleModel.magic());
    }

    @Test
    public void shouldBeAbleToReadDataField() {
        assertEquals("chain", simpleModel.dataKey());
    }

    @Test
    public void shouldReturnTheSameValueForMultipleCalls() {
        assertEquals(simpleModel.title(), simpleModel.title());
    }

    @Test
    public void shouldReturnDifferentHashCodeForEachInstance() throws IOException {
        SimpleModel otherObjectFromSameClass = alchemist
                .parseHtml(readResourceText("simple.html"), SimpleModel.class);
        assertNotEquals(simpleModel.hashCode(), otherObjectFromSameClass.hashCode());
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void shouldBeEqualsToSameInstance() {
        assertTrue(simpleModel.equals(simpleModel));
    }

    @Test
    public void shouldNotBeEqualsToOtherInstance() throws IOException {
        SimpleModel otherObjectFromSameClass = alchemist
                .parseHtml(readResourceText("simple.html"), SimpleModel.class);
        assertFalse(simpleModel.equals(otherObjectFromSameClass));
    }

    @SuppressWarnings("ObjectEqualsNull")
    @Test
    public void shouldNotBeEqualsToNull() {
        assertFalse(simpleModel.equals(null));
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void shouldNotBeEqualsToOtherObject() {
        assertFalse(simpleModel.equals("wow"));
    }

    @Test
    public void shouldReturnMessageForToString() {
        assertEquals("Proxy for \"com.brunodles.alchemist.AlchemistTest.WhenParseSimpleHtml$SimpleModel\".",
                simpleModel.toString());
    }

    @Test
    public void shouldParseNumbers() {
        assertEquals(0.13F, simpleModel.floatValue(), 0.01);
        assertEquals(0.13D, simpleModel.doubleValue(), 0.01D);
    }

    public interface SimpleModel {

        @Selector("#123")
        @TextCollector
        String span123();

        @Selector("head title")
        @TextCollector
        String title();

        @Selector("#123")
        @AttrCollector("class")
        String magic();

        @Selector("#123")
        @AttrCollector("data-key")
        String dataKey();

        @Selector("#float")
        @AttrCollector("data-value")
        @WithTransformer(TransformToFloat.class)
        Float floatValue();

        @Selector("#float")
        @AttrCollector("data-value")
        @UseValueOf
        Double doubleValue();
    }
}
