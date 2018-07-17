package com.brunodles.jsoupparser;

import com.brunodles.jsoupparser.smallanotation.annotations.*;
import com.brunodles.jsoupparser.smallanotation.SmallAnnotationInvocationHandler;
import com.brunodles.jsoupparser.bigannotation.transformers.TransformToFloat;
import com.brunodles.test.ResourceUriResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SmallAnnotation {

    private final JsoupParser jsoupParser = new JsoupParser(new ResourceUriResolver(), new SmallAnnotationInvocationHandler());

    @Test
    public void shouldParseSimple() {
        Simple2 simpleModel = jsoupParser.parseUrl("simple.html", Simple2.class);
        assertEquals("wow", simpleModel.span123());
        assertEquals("shouldBeAbleToReadContentFromTitle", "Jsoup Parser", simpleModel.title());
        assertEquals("shouldBeAbleToReadAttr", "magic", simpleModel.magic());
        assertEquals("shouldBeAbleToReadDataField", "chain", simpleModel.dataKey());
        assertEquals("shouldReturnTheSameValueForMultipleCalls", simpleModel.title(), simpleModel.title());
        assertEquals("shouldTransformStringToFloat", 0.13F, simpleModel.floatValue(), 0.001F);
    }

    @Test
    public void shouldParseNestedItem() {
        Nested rootModel = jsoupParser.parseUrl("nested_root.html", Nested.class);
        Nested.NestedChildModel child = rootModel.child();

        assertEquals("look at this", child.span123());
    }

    public interface Simple2 {

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

    public interface Nested {

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
}
