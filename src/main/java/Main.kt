//import com.brunodles.jsoupparser.CssSelector
//import com.brunodles.jsoupparser.JsoupParser
//import com.brunodles.jsoupparser.collectors.AttrElementCollector
//import com.brunodles.jsoupparser.collectors.AttrFollowUrlElementCollector
//import com.brunodles.jsoupparser.collectors.NestedCollector
//import com.brunodles.jsoupparser.collectors.TextElementCollector
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
//            parser = TextElementCollector::class)
//    fun name(): String
//
//    @CssSelector(selector = "[itemscope] [itemprop=image]",
//            parser = AttrElementCollector::class)
//    @AttrElementCollector.Settings(attrName = "content")
//    fun imageUrl(): String
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=ratingValue]",
//            parser = AttrElementCollector::class,
//            transformer = TransformToFloat::class)
//    @AttrElementCollector.Settings(attrName = "content")
//    fun rating(): Float
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=reviewCount]",
//            parser = AttrElementCollector::class,
//            transformer = TransformToLong::class)
//    @AttrElementCollector.Settings(attrName = "content")
//    fun reviewCount(): Long
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating]",
//            parser = NestedCollector::class)
//    fun aggregateRating(): AggregateRating
//
//    @CssSelector(selector = "[itemscope] [itemprop=url]",
//            parser = AttrFollowUrlElementCollector::class)
//    @AttrElementCollector.Settings(attrName = "content")
//    fun navigation(): NavigateModel
//}
//
//interface NavigateModel {
//
//    @CssSelector(selector = "[itemprop=name] span",
//            parser = TextElementCollector::class)
//    fun name(): String
//
//    @CssSelector(selector = "[itemscope] [itemprop=image]",
//            parser = AttrElementCollector::class)
//    @AttrElementCollector.Settings(attrName = "content")
//    fun imageUrl(): String
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=ratingValue]",
//            parser = AttrElementCollector::class,
//            transformer = TransformToFloat::class)
//    @AttrElementCollector.Settings(attrName = "content")
//    fun rating(): Float
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=reviewCount]",
//            parser = AttrElementCollector::class,
//            transformer = TransformToLong::class)
//    @AttrElementCollector.Settings(attrName = "content")
//    fun reviewCount(): Long
//
//    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating]",
//            parser = NestedCollector::class)
//    fun aggregateRating(): AggregateRating
//}
//
//interface AggregateRating {
//
//    @CssSelector(selector = "[itemprop=ratingValue]",
//            parser = AttrElementCollector::class,
//            transformer = TransformToFloat::class)
//    @AttrElementCollector.Settings(attrName = "content")
//    fun rating(): Float
//
//    @CssSelector(selector = "[itemprop=reviewCount]",
//            parser = AttrElementCollector::class,
//            transformer = TransformToLong::class)
//    @AttrElementCollector.Settings(attrName = "content")
//    fun reviewCount(): Long
//
//}
