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
 * Denotes that the annotated element should have a given size or length.
 * Note that "-1" means "unset". Typically used with a parameter or
 * return value of type array or collection.
 * <p>
 * Example:
 * <pre>{@code
 *  public void getLocationInWindow(@Size(2) int[] location) {
 *      ...
 *  }
 * }</pre>
 */
@Documented
@Retention(CLASS)
@Target({PARAMETER, LOCAL_VARIABLE, METHOD, FIELD, ANNOTATION_TYPE})
public @interface Size
{
    /**
     * An exact size (or -1 if not specified)
     */
    long value() default -1;

    /**
     * A minimum size, inclusive
     */
    long min() default Long.MIN_VALUE;

    /**
     * A maximum size, inclusive
     */
    long max() default Long.MAX_VALUE;

    /**
     * The size must be a multiple of this factor
     */
    long multiple() default 1;
}
