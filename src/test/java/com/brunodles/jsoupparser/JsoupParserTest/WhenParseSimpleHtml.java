package com.brunodles.jsoupparser.JsoupParserTest;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.collectors.AttrCollector;
import com.brunodles.jsoupparser.collectors.TextCollector;
import com.brunodles.jsoupparser.selector.Selector;
import com.brunodles.jsoupparser.transformers.TransformToFloat;
import com.brunodles.jsoupparser.usevalueof.UseValueOf;
import com.brunodles.jsoupparser.withtype.WithType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.brunodles.test.ResourceLoader.readResourceText;
import static org.junit.Assert.*;

public class WhenParseSimpleHtml {

    private final JsoupParser jsoupParser = new JsoupParser();
    private SimpleModel simpleModel;

    @Before
    public void parseHtmlToSimpleModel() throws IOException {
        simpleModel = jsoupParser.parseHtml(readResourceText("simple.html"), SimpleModel.class);
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
        SimpleModel otherObjectFromSameClass = jsoupParser.parseHtml(readResourceText("simple.html"), SimpleModel.class);
        assertNotEquals(simpleModel.hashCode(), otherObjectFromSameClass.hashCode());
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void shouldBeEqualsToSameInstance() {
        assertTrue(simpleModel.equals(simpleModel));
    }

    @Test
    public void shouldNotBeEqualsToOtherInstance() throws IOException {
        SimpleModel otherObjectFromSameClass = jsoupParser.parseHtml(readResourceText("simple.html"), SimpleModel.class);
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
        assertEquals("Proxy for \"com.brunodles.jsoupparser.JsoupParserTest.WhenParseSimpleHtml$SimpleModel\".", simpleModel.toString());
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
        @WithType(TransformToFloat.class)
        Float floatValue();

        @Selector("#float")
        @AttrCollector("data-value")
        @UseValueOf
        Double doubleValue();
    }
}
