package com.brunodles.alchemist.usevalueof;

import java.lang.annotation.*;

/**
 * Transforms the result into a Object Type.<br> Examples: {@link Integer}, {@link Long}, {@link Float}, {@link
 * Double}.<br>
 *
 * <p>Notice the annotation should be used only with classes that have a <code>valueOf</code> method that receives a
 * single {@link String} as parameter.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseValueOf {
}
