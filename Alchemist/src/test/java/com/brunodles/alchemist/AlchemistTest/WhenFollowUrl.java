package com.brunodles.alchemist.AlchemistTest;

import com.brunodles.alchemist.Alchemist;
import com.brunodles.alchemist.collectors.AttrCollector;
import com.brunodles.alchemist.navigate.Navigate;
import com.brunodles.alchemist.selector.Selector;
import com.brunodles.testhelpers.ResourceUriFetcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class WhenFollowUrl {

    private static Alchemist parser;
    private FollowModel follow;

    @BeforeClass
    public static void setupParser() {
        parser = new Alchemist.Builder().uriResolver(new ResourceUriFetcher()).build();
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
