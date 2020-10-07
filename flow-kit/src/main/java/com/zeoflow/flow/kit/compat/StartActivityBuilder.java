package com.zeoflow.flow.kit.compat;

import android.content.Intent;

import static com.zeoflow.flow.kit.initializer.ZeoFlowApp.getContext;

public class StartActivityBuilder
{

    public <T> StartActivityBuilder(Class<T> classOfT)
    {
        getContext().startActivity(new Intent(getContext(), classOfT));
    }


}
