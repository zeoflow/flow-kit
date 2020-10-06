package com.zeoflow.compat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.zson.Zson;

import java.io.Serializable;

public class Activity extends UtilCore
{
    private Class<?> activity;
    private Intent intent;

    public Activity(Class<?> activity)
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
    public Activity withParam(String name, boolean value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, byte value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, char value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, short value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, int value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, long value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, float value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, double value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable String value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable CharSequence value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable Parcelable value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable Parcelable[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable Serializable value)
    {
        this.intent.putExtra(name, new Zson().toJson(value));
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable boolean[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable byte[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable short[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable char[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable int[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable long[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable float[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable double[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable String[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable CharSequence[] value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

    @NonNull
    public Activity withParam(String name, @Nullable Bundle value)
    {
        this.intent.putExtra(name, value);
        return this;
    }

}
