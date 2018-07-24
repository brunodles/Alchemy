package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.transformers.TransformToFloat;
import com.brunodles.jsoupparser.collectors.TextCollector;
import com.brunodles.jsoupparser.nested.Nested;
import com.brunodles.jsoupparser.selector.Selector;
import com.brunodles.jsoupparser.withtype.WithType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import static com.brunodles.test.ResourceLoader.readResourceText;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class WhenParseCollections {

    private static final String[] JVM_LANGUAGES = {"Java", "Clojure", "Groovy", "Kotlin", "Scala"};
    private static final HashMap<String, Float> GAMES;

    static {
        HashMap<String, Float> games = new HashMap<>();
        games.put("God Of War", 199.90F);
        games.put("Rayman Legends", 87.99F);
        games.put("The Last Of Us Remastered", 60F);
        games.put("Just Cause 3", 60F);
        GAMES = games;
    }

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

    @Test
    public void whenReturnObjects_shouldBuildNestedObjects() {
        LinkedList<CollectionsModel.Games> games = listModel.gamesArrayList();
        assertEquals(4, games.size());
        for (CollectionsModel.Games game : games) {
            Float expectedPrice = GAMES.get(game.name());
            assertEquals(expectedPrice, game.price());
        }
    }


    public interface CollectionsModel {

        @Selector("ol#jvm_languages li")
        @TextCollector
        ArrayList<String> jvmLanguagesArrayList();

        @Selector("ol#jvm_languages li")
        @TextCollector
        HashSet<String> jvmLanguagesHashSet();

        @Selector("#games tr:has(td)")
        @Nested
        LinkedList<Games> gamesArrayList();

        interface Games {

            @Selector("td:first-child")
            @TextCollector
            String name();

            @Selector("td:nth-child(2)")
            @TextCollector
            @WithType(TransformToFloat.class)
            Float price();
        }
    }

}
