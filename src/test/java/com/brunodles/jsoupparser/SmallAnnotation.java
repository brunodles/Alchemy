package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.bigannotation.transformers.TransformToFloat;
import com.brunodles.jsoupparser.smallanotation.SmallAnnotationInvocationHandler;
import com.brunodles.jsoupparser.smallanotation.annotations.*;
import com.brunodles.test.ResourceUriResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class SmallAnnotation {

    private static final String[] JVM_LANGUAGES = {"Java", "Clojure", "Groovy", "Kotlin", "Scala"};
    private static final Map<String, Float> GAMES;

    static {
        HashMap<String, Float> games = new HashMap<>();
        games.put("God Of War", 199.90F);
        games.put("Rayman Legends", 87.99F);
        games.put("The Last Of Us Remastered", 60F);
        games.put("Just Cause 3", 60F);
        GAMES = Collections.unmodifiableMap(games);
    }

    private final JsoupParser jsoupParser = new JsoupParser(new ResourceUriResolver(), new SmallAnnotationInvocationHandler());

    @Test
    public void shouldParseSimple() {
        SimpleModel simpleModel = jsoupParser.parseUrl("simple.html", SimpleModel.class);
        assertEquals("wow", simpleModel.span123());
        assertEquals("shouldBeAbleToReadContentFromTitle", "Jsoup Parser", simpleModel.title());
        assertEquals("shouldBeAbleToReadAttr", "magic", simpleModel.magic());
        assertEquals("shouldBeAbleToReadDataField", "chain", simpleModel.dataKey());
        assertEquals("shouldReturnTheSameValueForMultipleCalls", simpleModel.title(), simpleModel.title());
        assertEquals("shouldTransformStringToFloat", 0.13F, simpleModel.floatValue(), 0.001F);
    }

    @Test
    public void shouldParseNestedItem() {
        NestedRootModel rootModel = jsoupParser.parseUrl("nested_root.html", NestedRootModel.class);
        NestedRootModel.NestedChildModel child = rootModel.child();

        assertEquals("look at this", child.span123());
    }

    @Test
    public void shouldParseCollections() {
        CollectionsModel listModel = jsoupParser.parseUrl("collections.html", CollectionsModel.class);

        ArrayList<String> list = listModel.jvmLanguagesArrayList();
        assertThat(list, hasItems(JVM_LANGUAGES));
        assertEquals(6, list.size());

        HashSet<String> set = listModel.jvmLanguagesHashSet();
        assertThat(set, hasItems(JVM_LANGUAGES));
        assertEquals(5, set.size());

        LinkedList<CollectionsModel.Games> games = listModel.gamesArrayList();
        assertEquals(4, games.size());
        for (CollectionsModel.Games game : games) {
            Float expectedPrice = GAMES.get(game.name());
            assertEquals(expectedPrice, game.price());
        }
    }

    @Test
    public void shouldFollowUrl() {
        FollowModel follow = jsoupParser.parseUrl("follow.html", FollowModel.class);

        SimpleModel simple = follow.simple();
        assertEquals("Jsoup Parser", simple.title());

        assertEquals(4, follow.collections().gamesArrayList().size());
    }

    @Test
    public void shouldAcceptStringOnComplex() {
        FollowModel follow = jsoupParser.parseUrl("follow.html", FollowModel.class);

        assertEquals("Jsoup Parser", follow.mappingSimple().title());
        assertEquals("wow", follow.mappingSimpleSpan123());
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
        @TypeTransformer(TransformToFloat.class)
        Float floatValue();
    }

    public interface NestedRootModel {

        @Selector(".root")
        @NestedCollector
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

    public interface CollectionsModel {

        @Selector("ol#jvm_languages li")
        @TextCollector
        ArrayList<String> jvmLanguagesArrayList();

        @Selector("ol#jvm_languages li")
        @TextCollector
        HashSet<String> jvmLanguagesHashSet();

        @Selector("#games tr:has(td)")
        @NestedCollector
        LinkedList<Games> gamesArrayList();

        interface Games {

            @Selector("td:first-child")
            @TextCollector
            String name();

            @Selector("td:nth-child(2)")
            @TextCollector
            @TypeTransformer(TransformToFloat.class)
            Float price();
        }
    }

    public interface FollowModel {

        @Selector(".something a#simple")
        @AttrCollector("href")
        @FollowTransformer
        SmallAnnotation.SimpleModel simple();

        @Selector(".something a#collections")
        @AttrCollector("href")
        @FollowTransformer
        CollectionsModel collections();

        @Mapping({"Selector(.something a#simple)",
                "AttrCollector(href)",
                "FollowTransformer"})
        SmallAnnotation.SimpleModel mappingSimple();

        // this one would be great
        @Mapping({"Selector(.something a#simple)",
                "AttrCollector(href)",
                "FollowTransformer",
                "Selector(#123)",
                "TextCollector"})
        SmallAnnotation.SimpleModel mappingSimpleSpan123();

    }

}
