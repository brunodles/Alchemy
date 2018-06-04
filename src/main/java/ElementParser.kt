import org.jsoup.nodes.Element
import java.lang.reflect.Method

interface ElementParser<T> {
    fun parse(jsoupParser: JsoupParser, value: Element, method: Method): T?
}

class TextElementParser : ElementParser<String> {
    override fun parse(jsoupParser: JsoupParser, value: Element, method: Method): String? =
            value.text()
}

class AttrElementParser : ElementParser<String> {
    override fun parse(jsoupParser: JsoupParser, value: Element, method: Method): String? {
        val attrExtractor = method.getAnnotation(AttrExtractor::class.java)
        return value.attr(attrExtractor.attrName)
    }
}

class NestedParser : ElementParser<Any> {
    override fun parse(jsoupParser: JsoupParser, value: Element, method: Method): Any? =
            jsoupParser.parseElement(value, method.returnType)
}

class AttrFollowUrlElementParser : ElementParser<Any> {
    override fun parse(jsoupParser: JsoupParser, value: Element, method: Method): Any? {
        val attrExtractor = method.getAnnotation(AttrExtractor::class.java)
        return jsoupParser.parseUrl(value.attr(attrExtractor.attrName), method.returnType)
    }
}
