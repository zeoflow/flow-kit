// Copyright 2021 ZeoFlow SRL
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.zeoflow.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.initializer.ZeoFlowApp;
import com.zeoflow.log.AndroidLogAdapter;
import com.zeoflow.log.FormatStrategy;
import com.zeoflow.log.Log;
import com.zeoflow.log.PrettyFormatStrategy;
import com.zeoflow.zson.Zson;

public class FragmentActivity extends androidx.fragment.app.FragmentActivity
{

    public Context zContext;
    private String log_tag = getClass().getSimpleName();

    public FragmentActivity()
    {
        Log.configure(log_tag);
        zContext = getApplicationContext();
    }
    public FragmentActivity(int contentLayoutId)
    {
        super(contentLayoutId);
        Log.configure(log_tag);
        zContext = getApplicationContext();
    }
    public void withLoggerTag(@NonNull String tag)
    {
        log_tag = tag;
    }
    public void log(@NonNull String message, @Nullable Object... args)
    {

        boolean isDebuggable = (0 != (zContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        if (!isDebuggable)
        {
            return;
        }

        Log.d(message, args);

    }
    public void log(Object... objects)
    {

        boolean isDebuggable = (0 != (zContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        if (!isDebuggable)
        {
            return;
        }

        for (Object object : objects)
        {
            Log.json(new Zson().toJson(object));
        }
    }
    public final <T extends View> T findViewById(int id, Bundle bundle, String key) {
        View view = getSupportFragmentManager().getFragment(bundle, key).getView();
        if (view == null) {
            throw new IllegalStateException("Fragment " + this + " did not return a View from"
                    + " onViewCreated() or this was called before onViewCreated().");
        }
        return view.findViewById(id);
    }
    public final <T extends View> T findViewById(int id, int fragmentIndex) {
        View view = getSupportFragmentManager().getFragments().get(fragmentIndex).getView();
        if (view == null) {
            throw new IllegalStateException("Fragment " + this + " did not return a View from"
                + " onViewCreated() or this was called before onViewCreated().");
        }
        return view.findViewById(id);
    }
    public void startActivity(Class<?> activity)
    {
        new ActivityBuilder(activity).start();
    }
    public ActivityBuilder configureNewActivity(Class<?> activity)
    {
        return new ActivityBuilder(activity);
    }

}
