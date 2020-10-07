package com.zeoflow.flow.kit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated method returns a result that it typically is
 * an error to ignore. This is usually used for methods that have no side effect,
 * so calling it without actually looking at the result usually means the developer
 * has misunderstood what the method does.
 * <p>
 * Example:
 * <pre>{@code
 *  public @CheckResult String trim(String s) { return s.trim(); }
 *  ...
 *  s.trim(); // this is probably an error
 *  s = s.trim(); // ok
 * }</pre>
 */
@Documented
@Retention(CLASS)
@Target({METHOD})
public @interface CheckResult
{
    /**
     * Defines the name of the suggested method to use instead, if applicable (using
     * the same signature format as javadoc.) If there is more than one possibility,
     * list them all separated by commas.
     * <p>
     * For example, ProcessBuilder has a method named {@code redirectErrorStream()}
     * which sounds like it might redirect the error stream. It does not. It's just
     * a getter which returns whether the process builder will redirect the error stream,
     * and to actually set it, you must call {@code redirectErrorStream(boolean)}.
     * In that case, the method should be defined like this:
     * <pre>
     *  &#64;CheckResult(suggest="#redirectErrorStream(boolean)")
     *  public boolean redirectErrorStream() { ... }
     * </pre>
     */
    String suggest() default "";
}
