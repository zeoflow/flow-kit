package com.zeoflow.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Typedef for the {@link acom.zeoflow.zsdkndroidx.annotation.VisibleForTesting#otherwise} attribute.
 *
 * @hide
 */
@IntDef({VisibleForTesting.PRIVATE,
    VisibleForTesting.PACKAGE_PRIVATE,
    VisibleForTesting.PROTECTED,
    VisibleForTesting.NONE}
)
@RestrictTo(RestrictTo.Scope.LIBRARY)
@Retention(SOURCE)
@interface ProductionVisibility
{
}
