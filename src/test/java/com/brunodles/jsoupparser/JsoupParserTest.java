package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.colectors.NestedParser;
import com.brunodles.jsoupparser.colectors.TextElementParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.IOException;

import static com.brunodles.test.ResourceLoader.readResourceText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Enclosed.class)
public class JsoupParserTest {

    private static final JsoupParser jsoupParser = new JsoupParser();

    public static class WhenParseSimpleHtml {

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
    }

    public static class WhenParseNestedContent {

        private NestedRootModel rootModel;

        @Before
        public void parseHtmlToNestedRootModel() throws IOException {
            rootModel = jsoupParser.parseHtml(readResourceText("nested_root.html"), NestedRootModel.class);
        }

        @Test
        public void shouldBeAbleToGetChild() {
            NestedChildModel child = rootModel.child();

            assertNotNull(child);
        }

        @Test
        public void shouldBeAbleToGetChildsContent() {
            NestedChildModel child = rootModel.child();

            String result = child.span123();
            assertEquals("look at this", result);
        }
    }

    public interface SimpleModel {
        @CssSelector(selector = "#123",
                parser = TextElementParser.class)
        String span123();

        @CssSelector(selector = "head title",
                parser = TextElementParser.class)
        String title();
    }

    public interface NestedRootModel {

        @CssSelector(selector = ".root",
        parser = NestedParser.class)
        NestedChildModel child();
    }

    public interface NestedChildModel {
        @CssSelector(selector = "span.123",
                parser = TextElementParser.class)
        String span123();

        @CssSelector(selector = "div.456",
                parser = TextElementParser.class)
        String div456();
    }
}
