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
 * Denotes that the annotated method or constructor should only be called on the UI thread.
 * If the annotated element is a class, then all methods in the class should be called
 * on the UI thread.
 * <p>
 * Example:
 * <pre><code>
 *  &#64;UiThread
 *
 *  public abstract void setText(@NonNull String text) { ... }
 * </code></pre>
 *
 * <p class="note"><b>Note:</b> Ordinarily, an app's UI thread is also the main
 * thread. However, under special circumstances, an app's UI thread
 * might not be its main thread; for more information, see
 * <a href="/studio/write/annotations.html#thread-annotations">Thread
 * annotations</a>.
 *
 * @see com.zeoflow.flow.kit.annotation.MainThread
 */
@Documented
@Retention(CLASS)
@Target({METHOD, CONSTRUCTOR, TYPE, PARAMETER})
public @interface UiThread
{
}
