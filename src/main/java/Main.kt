//import com.brunodles.jsoupparser.CssSelector
//import com.brunodles.jsoupparser.JsoupParser
//import com.brunodles.jsoupparser.colectors.AttrElementParser
//import com.brunodles.jsoupparser.colectors.AttrFollowUrlElementParser
//import com.brunodles.jsoupparser.colectors.NestedParser
//import com.brunodles.jsoupparser.colectors.TextElementParser
//import com.brunodles.jsoupparser.transformers.TransformToFloat
//import com.brunodles.jsoupparser.transformers.TransformToLong
//
//fun main(args: Array<String>) {
//    val jsoupParser = JsoupParser()
//    val url = "https://play.google.com/store/apps/details?id=com.itau.empresas"
//    val appData: SampleModel = jsoupParser.parseUrl(url, SampleModel::class.java)
//    with(appData) {
//        println("Name: ${name()}")
//        println("ImageUrl: ${imageUrl()}")
//        println("Rating: ${rating()}")
//        println("Reviews: ${reviewCount()}")
//        println(aggregateRating().rating())
//        println(aggregateRating().reviewCount())
//    }
//
//    println(appData.navigation().name())
//}
//
//interface SampleModel {
//
//    @CssSelector(selector = "[itemprop=name] span",
//            parser = TextElementParser::class)
//    fun name(): String
//
//    @CssSelector(selector = "[itemscope] [itemprop=image]",
//            parser = AttrElementParser::class)
//    @AttrElementParser.Settings(attrName = "content")
//    fun imageUrl(): String
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=ratingValue]",
//            parser = AttrElementParser::class,
//            transformer = TransformToFloat::class)
//    @AttrElementParser.Settings(attrName = "content")
//    fun rating(): Float
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=reviewCount]",
//            parser = AttrElementParser::class,
//            transformer = TransformToLong::class)
//    @AttrElementParser.Settings(attrName = "content")
//    fun reviewCount(): Long
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating]",
//            parser = NestedParser::class)
//    fun aggregateRating(): AggregateRating
//
//    @CssSelector(selector = "[itemscope] [itemprop=url]",
//            parser = AttrFollowUrlElementParser::class)
//    @AttrElementParser.Settings(attrName = "content")
//    fun navigation(): NavigateModel
//}
//
//interface NavigateModel {
//
//    @CssSelector(selector = "[itemprop=name] span",
//            parser = TextElementParser::class)
//    fun name(): String
//
//    @CssSelector(selector = "[itemscope] [itemprop=image]",
//            parser = AttrElementParser::class)
//    @AttrElementParser.Settings(attrName = "content")
//    fun imageUrl(): String
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=ratingValue]",
//            parser = AttrElementParser::class,
//            transformer = TransformToFloat::class)
//    @AttrElementParser.Settings(attrName = "content")
//    fun rating(): Float
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=reviewCount]",
//            parser = AttrElementParser::class,
//            transformer = TransformToLong::class)
//    @AttrElementParser.Settings(attrName = "content")
//    fun reviewCount(): Long
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating]",
//            parser = NestedParser::class)
//    fun aggregateRating(): AggregateRating
//}
//
//interface AggregateRating {
//
//    @CssSelector(selector = "[itemprop=ratingValue]",
//            parser = AttrElementParser::class,
//            transformer = TransformToFloat::class)
//    @AttrElementParser.Settings(attrName = "content")
//    fun rating(): Float
//
//    @CssSelector(selector = "[itemprop=reviewCount]",
//            parser = AttrElementParser::class,
//            transformer = TransformToLong::class)
//    @AttrElementParser.Settings(attrName = "content")
//    fun reviewCount(): Long
//
//}
