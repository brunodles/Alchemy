import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AttrExtractor {

    @NotNull
    String attrName();
}