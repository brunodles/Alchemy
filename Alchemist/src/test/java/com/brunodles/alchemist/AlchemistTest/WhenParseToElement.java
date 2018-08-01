package com.brunodles.alchemist.AlchemistTest;

import com.brunodles.alchemist.Alchemist;
import com.brunodles.alchemist.selector.Selector;
import com.brunodles.testhelpers.ResourceUriFetcher;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class WhenParseToElement {

    private final Alchemist alchemist = new Alchemist.Builder().uriResolver(new ResourceUriFetcher()).build();

    @Test
    public void shouldBeAbleToReturnElement() {
        SimpleModel simpleModel = alchemist.parseUrl("simple.html", SimpleModel.class);
        Element element = simpleModel.span123();
        assertEquals("magic", element.attr("class"));
        assertEquals("chain", element.attr("data-key"));
        assertEquals("wow", element.text());
    }

    @Test
    public void shouldReturnElementDirectly() {
        Element element = alchemist.parseUrl("simple.html", Element.class);
        assertEquals("wow", element.select("#123").text());
    }

    public interface SimpleModel {

        @Selector("#123")
        Element span123();
    }
}
