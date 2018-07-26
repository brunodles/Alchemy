package com.brunodles.jsoupparser.JsoupParserTest;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.selector.Selector;
import com.brunodles.test.ResourceUriResolver;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class WhenParseToElement {

    private final JsoupParser jsoupParser = new JsoupParser.Builder().uriResolver(new ResourceUriResolver()).build();

    @Test
    public void shouldBeAbleToReturnElement() {
        SimpleModel simpleModel = jsoupParser.parseUrl("simple.html", SimpleModel.class);
        Element element = simpleModel.span123();
        assertEquals("magic", element.attr("class"));
        assertEquals("chain", element.attr("data-key"));
        assertEquals("wow", element.text());
    }

    @Test
    public void shouldReturnElementDirectly() {
        Element element = jsoupParser.parseUrl("simple.html", Element.class);
        assertEquals("wow", element.select("#123").text());
    }

    public interface SimpleModel {

        @Selector("#123")
        Element span123();
    }
}
