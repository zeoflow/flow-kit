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

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.zson.Zson;

import java.io.Serializable;

public class ActivityBuilder extends Entity
{
    private Class<?> activity;
    private Intent intent;

    public ActivityBuilder(Class<?> activity)
    {
        this.activity = activity;
        this.intent = new Intent(zContext, activity);
        this.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public void start()
    {
        zContext.startActivity(this.intent);
    }

    public void start(android.app.Activity finish)
    {
        zContext.startActivity(this.intent);
        if (finish != null)
        {
            finish.finish();
        }
    }

    @NonNull
    public ActivityBuilder withParam(String name, boolean value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, byte value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, char value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, short value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, int value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, long value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, float value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, double value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable String value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable CharSequence value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable Parcelable value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable Parcelable[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable Serializable value)
    {
        this.intent.putExtra(name, new Zson().toJson(value));
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable boolean[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable byte[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable short[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable char[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable int[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable long[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable float[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable double[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable String[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable CharSequence[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public ActivityBuilder withParam(String name, @Nullable Bundle value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

}
