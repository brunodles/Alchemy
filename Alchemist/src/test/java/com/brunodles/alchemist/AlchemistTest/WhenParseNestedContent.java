package com.brunodles.alchemist.AlchemistTest;

import com.brunodles.alchemist.Alchemist;
import com.brunodles.alchemist.collectors.TextCollector;
import com.brunodles.alchemist.nested.Nested;
import com.brunodles.alchemist.selector.Selector;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.brunodles.testhelpers.ResourceLoader.readResourceText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WhenParseNestedContent {

    private final Alchemist alchemist = new Alchemist();
    private NestedRootModel rootModel;

    @Before
    public void parseHtmlToNestedRootModel() throws IOException {
        rootModel = alchemist.parseHtml(readResourceText("nested_root.html"), NestedRootModel.class);
    }

    @Test
    public void shouldBeAbleToGetChild() {
        NestedRootModel.NestedChildModel child = rootModel.child();

        assertNotNull(child);
    }

    @Test
    public void shouldBeAbleToGetChildsContent() {
        NestedRootModel.NestedChildModel child = rootModel.child();

        String result = child.span123();
        assertEquals("look at this", result);
    }


    public interface NestedRootModel {

        @Selector(".root")
        @Nested
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
}
