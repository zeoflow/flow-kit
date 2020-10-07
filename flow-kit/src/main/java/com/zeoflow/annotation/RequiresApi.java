package com.zeoflow.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that the annotated element should only be called on the given API level or higher.
 *
 * <p>This is similar in purpose to the older {@code @TargetApi} annotation, but more clearly
 * expresses that this is a requirement on the caller, rather than being used to "suppress" warnings
 * within the method that exceed the {@code minSdkVersion}.
 */
@Documented
@Retention(CLASS)
@Target({TYPE, METHOD, CONSTRUCTOR, FIELD, PACKAGE})
public @interface RequiresApi
{
    /**
     * The API level to require. Alias for {@link #api} which allows you to leave out the {@code
     * api=} part.
     */
    @IntRange(from = 1)
    int value() default 1;

    /**
     * The API level to require
     */
    @IntRange(from = 1)
    int api() default 1;
}
