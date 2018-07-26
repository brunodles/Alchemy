package com.brunodles.jsoupparser.JsoupParserTests;

import com.brunodles.jsoupparser.JsoupParser;
import com.brunodles.jsoupparser.collectors.AttrCollector;
import com.brunodles.jsoupparser.navigate.Navigate;
import com.brunodles.jsoupparser.selector.Selector;
import com.brunodles.test.ResourceUriResolver;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class WhenFollowUrl {

    private static JsoupParser parser;
    private FollowModel follow;

    @BeforeClass
    public static void setupParser() {
        parser = new JsoupParser.Builder().uriResolver(new ResourceUriResolver()).build();
    }

    @Before
    public void setupFollowModel() {
        follow = parser.parseUrl("follow.html", FollowModel.class);
    }

    @Test
    public void givenWhenThen() {
        WhenParseSimpleHtml.SimpleModel simple = follow.simple();
        assertEquals("Jsoup Parser", simple.title());
    }

    public interface FollowModel {

        @Selector(".something a#simple")
        @AttrCollector("href")
        @Navigate
        WhenParseSimpleHtml.SimpleModel simple();

        @Selector(".something a#collections")
        @AttrCollector("href")
        @Navigate
        WhenParseCollections.CollectionsModel collections();
    }
}
