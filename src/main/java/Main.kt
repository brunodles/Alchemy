

fun main(args: Array<String>) {
    val jsoupParser = JsoupParser()
    val url = "https://play.google.com/store/apps/details?id=com.google.android.apps.maps"
    val appData : SampleModel = jsoupParser.parseUrl(url, SampleModel::class.java)
    println(appData.name())
    println(appData.imageUrl())
    println(appData.rating())
    println(appData.reviewCount())

    println(appData.aggregateRating().rating())
    println(appData.aggregateRating().reviewCount())

    println(appData.navigation().name())
}