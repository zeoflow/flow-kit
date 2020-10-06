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
 * <p>Denotes that the annotated element represents a packed color
 * long. If applied to a long array, every element in the array
 * represents a color long. For more information on how colors
 * are packed in a long, please refer to the documentation of
 * the {@link android.graphics.Color} class.</p>
 *
 * <p>Example:</p>
 *
 * <pre>{@code
 *  public void setFillColor(@ColorLong long color);
 * }</pre>
 *
 * @see android.graphics.Color
 */
@Documented
@Retention(SOURCE)
@Target({PARAMETER, METHOD, LOCAL_VARIABLE, FIELD})
public @interface ColorLong
{
}
