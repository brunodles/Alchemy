package com.brunodles.jsoupparser.JsoupParserTest;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.doubles.ListsModel;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static com.brunodles.test.ResourceLoader.readResourceText;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class WhenParseList {

    private static final String[] JVM_LANGUAGES = {"Java", "Clojure", "Groovy", "Kotlin", "Scala"};

    private final JsoupParser jsoupParser = new JsoupParser();
    private ListsModel listModel;

    @Before
    public void parseHtmlToSimpleModel() throws IOException {
        listModel = jsoupParser.parseHtml(readResourceText("lists.html"), ListsModel.class);
    }

    @Test
    public void shouldReturnTheExpectedList() {
        assertThat(listModel.jvmLanguagesList(), hasItems(JVM_LANGUAGES));
    }

}
