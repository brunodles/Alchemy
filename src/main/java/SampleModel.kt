interface SampleModel {

    @CssSelector(selector = "[itemprop=name] span",
            parser = TextElementParser::class)
    fun name(): String

    @CssSelector(selector = "[itemscope] [itemprop=image]",
            parser = AttrElementParser::class)
    @AttrExtractor(attrName = "content")
    fun imageUrl(): String

    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=ratingValue]",
            parser = AttrElementParser::class,
            transformer = TransformToFloat::class)
    @AttrExtractor(attrName = "content")
    fun rating(): Float

    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=reviewCount]",
            parser = AttrElementParser::class,
            transformer = TransformToLong::class)
    @AttrExtractor(attrName = "content")
    fun reviewCount(): Long

    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating]",
            parser = NestedParser::class)
    fun aggregateRating(): AggregateRating

    @CssSelector(selector = "[itemscope] [itemprop=url]",
            parser = AttrFollowUrlElementParser::class)
    @AttrExtractor(attrName = "content")
    fun navigation(): NavigateModel
}

interface NavigateModel {

    @CssSelector(selector = "[itemprop=name] span",
            parser = TextElementParser::class)
    fun name(): String

    @CssSelector(selector = "[itemscope] [itemprop=image]",
            parser = AttrElementParser::class)
    @AttrExtractor(attrName = "content")
    fun imageUrl(): String

    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=ratingValue]",
            parser = AttrElementParser::class,
            transformer = TransformToFloat::class)
    @AttrExtractor(attrName = "content")
    fun rating(): Float

    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating] [itemprop=reviewCount]",
            parser = AttrElementParser::class,
            transformer = TransformToLong::class)
    @AttrExtractor(attrName = "content")
    fun reviewCount(): Long

    @CssSelector(selector = "[itemscope] [itemprop=aggregateRating]",
            parser = NestedParser::class)
    fun aggregateRating(): AggregateRating
}

interface AggregateRating {

    @CssSelector(selector = "[itemprop=ratingValue]",
            parser = AttrElementParser::class,
            transformer = TransformToFloat::class)
    @AttrExtractor(attrName = "content")
    fun rating(): Float

    @CssSelector(selector = "[itemprop=reviewCount]",
            parser = AttrElementParser::class,
            transformer = TransformToLong::class)
    @AttrExtractor(attrName = "content")
    fun reviewCount(): Long

}