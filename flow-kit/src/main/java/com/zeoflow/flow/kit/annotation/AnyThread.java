package com.zeoflow.flow.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated method can be called from any thread (e.g. it is "thread safe".)
 * If the annotated element is a class, then all methods in the class can be called
 * from any thread.
 * <p>
 * The main purpose of this method is to indicate that you believe a method can be called
 * from any thread; static tools can then check that nothing you call from within this method
 * or class have more strict threading requirements.
 * <p>
 * Example:
 * <pre><code>
 *  &#64;AnyThread
 *  public void deliverResult(D data) { ... }
 * </code></pre>
 */
@Documented
@Retention(CLASS)
@Target({METHOD, CONSTRUCTOR, TYPE, PARAMETER})
public @interface AnyThread
{
}
