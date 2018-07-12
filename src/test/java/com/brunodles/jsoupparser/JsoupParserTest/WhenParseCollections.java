package com.brunodles.jsoupparser.JsoupParserTest;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.doubles.CollectionsModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static com.brunodles.test.ResourceLoader.readResourceText;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class WhenParseCollections {

    private static final String[] JVM_LANGUAGES = {"Java", "Clojure", "Groovy", "Kotlin", "Scala"};

    private final JsoupParser jsoupParser = new JsoupParser();
    private CollectionsModel listModel;

    @Before
    public void parseHtmlToSimpleModel() throws IOException {
        listModel = jsoupParser.parseHtml(readResourceText("collections.html"), CollectionsModel.class);
    }

    @Test
    public void shouldReturnTheExpectedArrayList() {
        ArrayList<String> list = listModel.jvmLanguagesArrayList();
        assertThat(list, hasItems(JVM_LANGUAGES));
        assertEquals(6, list.size());
    }

    @Test
    public void shouldReturnTheExpectedHashSet() {
        HashSet<String> set = listModel.jvmLanguagesHashSet();
        assertThat(set, hasItems(JVM_LANGUAGES));
        assertEquals(5, set.size());
    }

}
