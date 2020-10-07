package com.zeoflow.flow.kit.core.manage;

import android.text.TextUtils;

import com.zeoflow.flow.kit.annotation.KeepForApi;
import com.zeoflow.flow.kit.annotation.Nullable;

import java.util.regex.Pattern;

@KeepForApi
public class StringsVerify
{
    private static final Pattern zzhh = Pattern.compile("\\$\\{(.*?)\\}");

    private StringsVerify()
    {
    }

    @Nullable
    @KeepForApi
    public static String emptyToNull(@Nullable String var0)
    {
        return TextUtils.isEmpty(var0) ? null : var0;
    }

    @KeepForApi
    public static boolean isEmptyOrWhitespace(@Nullable String var0)
    {
        return var0 == null || var0.trim().isEmpty();
    }
}
