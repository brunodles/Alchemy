import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class CssSelectorK<RESULT : Any>(
        val selector: String,
        val parser: KClass<out ElementParser<out RESULT>>,
        val transformer: KClass<out Transformer<out RESULT, out Any>> //= NonTransformer::class
)