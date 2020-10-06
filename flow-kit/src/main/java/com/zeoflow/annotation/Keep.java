package com.zeoflow.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated element should not be removed when
 * the code is minified at build time. This is typically used
 * on methods and classes that are accessed only via reflection
 * so a compiler may think that the code is unused.
 * <p>
 * Example:
 * <pre><code>
 *  &#64;Keep
 *  public void foo() {
 *      ...
 *  }
 * </code></pre>
 */
@Retention(CLASS)
@Target({PACKAGE, TYPE, ANNOTATION_TYPE, CONSTRUCTOR, METHOD, FIELD})
public @interface Keep
{
}
