package com.zeoflow.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @hide
 */
@IntDef({Dimension.PX,
    Dimension.DP,
    Dimension.SP}
// Important: If updating these constants, also update
// ../../../../external-annotations/android/support/annotation/annotations.xml
)
@RestrictTo(RestrictTo.Scope.LIBRARY)
@Retention(SOURCE)
@interface DimensionUnit
{
}
