package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.colectors.TextElementParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.IOException;

import static com.brunodles.test.ResourceLoader.readResourceText;
import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class JsoupParserTest {

    private static final JsoupParser jsoupParser = new JsoupParser();

    public static class WhenParseSimpleHtml {

        private SimpleModel simpleModel;

        @Before
        public void temp() throws IOException {
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

    public interface SimpleModel {
        @CssSelector(selector = "#123",
                parser = TextElementParser.class)
        String span123();

        @CssSelector(selector = "head title",
                parser = TextElementParser.class)
        String title();
    }
}
