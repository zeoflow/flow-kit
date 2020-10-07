package com.zeoflow.flow.kit.core.manage;


import android.content.Context;
import android.content.res.Resources;

import com.zeoflow.flow.kit.annotation.KeepForApi;
import com.zeoflow.flow.kit.annotation.Nullable;
import com.zeoflow.flow.kit.core.utils.Preconditions;

@KeepForApi
public class StringResourceValueReader
{
    private final Resources resources;
    private final String string;

    public StringResourceValueReader(Context mContext)
    {
        Preconditions.checkNotNull(mContext);
        this.resources = mContext.getResources();
        this.string = "common_zeoflow_services_unknown_issue";
    }

    @Nullable
    @KeepForApi
    public String getString(String var1)
    {
        int var2;
        return (var2 = this.resources.getIdentifier(var1, "string", this.string)) == 0 ? null : this.resources.getString(var2);
    }
}
