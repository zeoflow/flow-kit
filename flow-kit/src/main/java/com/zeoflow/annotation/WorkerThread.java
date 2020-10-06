package com.zeoflow.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated method should only be called on a worker thread.
 * If the annotated element is a class, then all methods in the class should be called
 * on a worker thread.
 * <p>
 * Example:
 * <pre><code>
 *  &#64;WorkerThread
 *  protected abstract FilterResults performFiltering(CharSequence constraint);
 * </code></pre>
 */
@Documented
@Retention(CLASS)
@Target({METHOD, CONSTRUCTOR, TYPE, PARAMETER})
public @interface WorkerThread
{
}
