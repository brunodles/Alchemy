package com.brunodles.jsoupparser.JsoupParserTest;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.collectors.TextCollector;
import com.brunodles.jsoupparser.nested.Nested;
import com.brunodles.jsoupparser.selector.Selector;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.brunodles.test.ResourceLoader.readResourceText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WhenParseNestedContent {

    private final JsoupParser jsoupParser = new JsoupParser();
    private NestedRootModel rootModel;

    @Before
    public void parseHtmlToNestedRootModel() throws IOException {
        rootModel = jsoupParser.parseHtml(readResourceText("nested_root.html"), NestedRootModel.class);
    }

    @Test
    public void shouldBeAbleToGetChild() {
        NestedRootModel.NestedChildModel child = rootModel.child();

        assertNotNull(child);
    }

    @Test
    public void shouldBeAbleToGetChildsContent() {
        NestedRootModel.NestedChildModel child = rootModel.child();

        String result = child.span123();
        assertEquals("look at this", result);
    }


    public interface NestedRootModel {

        @Selector(".root")
        @Nested
        NestedChildModel child();

        interface NestedChildModel {
            @Selector("span.123")
            @TextCollector
            String span123();

            @Selector("div.456")
            @TextCollector
            String div456();
        }
    }
}
