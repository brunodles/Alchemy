package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.JsoupParserTests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        WhenFollowUrl.class,
        WhenHaveError.class,
        WhenParseCollections.class,
        WhenParseNestedContent.class,
        WhenParseSimpleHtml.class,
        WhenParseToElement.class
})
public class JsoupParserTest {
}