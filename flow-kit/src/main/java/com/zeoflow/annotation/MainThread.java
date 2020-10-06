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
 * Denotes that the annotated method should only be called on the main thread.
 * If the annotated element is a class, then all methods in the class should be called
 * on the main thread.
 * <p>
 * Example:
 * <pre><code>
 *  &#64;MainThread
 *  public void deliverResult(D data) { ... }
 * </code></pre>
 *
 * <p class="note"><b>Note:</b> Ordinarily, an app's main thread is also the UI
 * thread. However, under special circumstances, an app's main thread
 * might not be its UI thread; for more information, see
 * <a href="/studio/write/annotations.html#thread-annotations">Thread
 * annotations</a>.
 */
@Documented
@Retention(CLASS)
@Target({METHOD, CONSTRUCTOR, TYPE, PARAMETER})
public @interface MainThread
{
}
