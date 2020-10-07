package com.zeoflow.flow.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated element should be an int or long in the given range
 * <p>
 * Example:
 * <pre><code>
 *  &#64;IntRange(from=0,to=255)
 *  public int getAlpha() {
 *      ...
 *  }
 * </code></pre>
 */
@Documented
@Retention(CLASS)
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE, ANNOTATION_TYPE})
public @interface IntRange
{
    /**
     * Smallest value, inclusive
     */
    long from() default Long.MIN_VALUE;

    /**
     * Largest value, inclusive
     */
    long to() default Long.MAX_VALUE;
}
