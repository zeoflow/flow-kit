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

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.initializer.ZeoFlowApp;
import com.zeoflow.log.AndroidLogAdapter;
import com.zeoflow.log.FormatStrategy;
import com.zeoflow.log.Log;
import com.zeoflow.log.PrettyFormatStrategy;
import com.zeoflow.zson.Zson;

public class Entity
{

    public Context zContext = ZeoFlowApp.getContext();
    private String log_tag = getClass().getSimpleName();

    public Entity()
    {
        Log.configure(log_tag);
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
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .tag(log_tag == null || log_tag.isEmpty() ? this.getClass().getSimpleName() : log_tag)
            .build();
        Log.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        for (Object object : objects)
        {
            Log.json(new Zson().toJson(object));
        }
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
