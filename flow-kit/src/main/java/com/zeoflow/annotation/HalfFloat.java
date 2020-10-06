package com.zeoflow.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * <p>Denotes that the annotated element represents a half-precision floating point
 * value. Such values are stored in short data types and can be manipulated with
 * the <code>android.util.Half</code> class. If applied to an array of short, every
 * element in the array represents a half-precision float.</p>
 *
 * <p>Example:</p>
 *
 * <pre>{@code
 * public abstract void setPosition(@HalfFloat short x, @HalfFloat short y, @HalfFloat short z);
 * }</pre>
 */
@Documented
@Retention(SOURCE)
@Target({PARAMETER, METHOD, LOCAL_VARIABLE, FIELD})
public @interface HalfFloat
{
}
