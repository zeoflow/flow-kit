package com.zeoflow.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated method should not be inlined when
 * the code is optimized at build time. This is typically used
 * to avoid inlining purposely out-of-line methods that are
 * intended to be in separate classes.
 * <p>
 * Example:
 * <pre><code>
 *  &#64;DoNotInline
 *  public void foo() {
 *      ...
 *  }
 * </code></pre>
 */
@Retention(CLASS)
@Target({METHOD})
public @interface DoNotInline
{
}
