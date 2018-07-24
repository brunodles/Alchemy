package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.smallanotation.SmallAnnotationInvocationHandler;
import com.brunodles.jsoupparser.smallanotation.selector.Selector;
import com.brunodles.test.ResourceUriResolver;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class WhenParseToElement {

    private final JsoupParser jsoupParser = new JsoupParser(new ResourceUriResolver(), new SmallAnnotationInvocationHandler());

    @Test
    public void shouldReturnElementDirectly() {
        SimpleModel simpleModel = jsoupParser.parseUrl("simple.html", SimpleModel.class);
        Element element = simpleModel.span123();
        assertEquals("magic", element.attr("class"));
        assertEquals("chain", element.attr("data-key"));
        assertEquals("wow", element.text());
    }

    public interface SimpleModel {

        @Selector("#123")
        Element span123();
    }
}
