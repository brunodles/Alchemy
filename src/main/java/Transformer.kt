@FunctionalInterface
interface Transformer<INPUT, OUTPUT> {
    fun parse(value: INPUT): OUTPUT
}

class NonTransformer<T> : Transformer<T, T> {
    override fun parse(value: T): T = value
}

class TransformToLong : Transformer<String, Long> {
    override fun parse(value: String): Long = value.toLong()
}

class TransformToFloat : Transformer<String, Float> {
    override fun parse(value: String): Float = value.toFloat()
}