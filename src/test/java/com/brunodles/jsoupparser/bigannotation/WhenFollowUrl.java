package com.brunodles.jsoupparser.bigannotation;

import com.brunodles.jsoupparser.JsoupParser;
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
        parser = new JsoupParser(new ResourceUriResolver());
    }

    @Before
    public void setupFollowModel() {
        follow = parser.parseUrl("follow.html", FollowModel.class);
    }

    @Test
    public void givenWhenThen() {
        SimpleModel simple = follow.simple();
        assertEquals("Jsoup Parser", simple.title());
    }
}
