package com.zeoflow.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.initializer.ZeoFlowApp;
import com.zeoflow.log.AndroidLogAdapter;
import com.zeoflow.log.FormatStrategy;
import com.zeoflow.log.Log;
import com.zeoflow.log.PrettyFormatStrategy;
import com.zeoflow.model.Extra;
import com.zeoflow.zson.JsonElement;
import com.zeoflow.zson.Zson;
import com.zeoflow.zson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Activity extends AppCompatActivity
{

    public Context zContext = ZeoFlowApp.getContext();
    private String log_tag = getClass().getSimpleName();

    public Activity()
    {
        super();
        Log.configure(log_tag);
    }
    public Activity(int contentLayoutId)
    {
        super(contentLayoutId);
        Log.configure(log_tag);
    }
    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        zContext = this;
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
    public Gist getGist()
    {
        return new Gist(this);
    }
    public List<Extra> getExtras()
    {
        return getGist().getExtras();
    }
    public JsonElement getExtra(String key)
    {
        return getGist().getExtra(key).getValue();
    }
    public int getIntExtra(String key)
    {
        return getGist().getExtra(key).getValue().getAsInt();
    }
    public <T> List<T> getArrayExtra(String key)
    {
        Zson zson = new Zson();
        Type type = new TypeToken<List<T>>()
        {
        }.getType();
        return zson.fromJson(getGist().getExtra(key).getValue().getAsString(), type);
    }
    public String getStringExtra(String key)
    {
        return getGist().getExtra(key).getValue().getAsString();
    }
    public Object getObjectExtra(String key)
    {
        return getGist().getExtra(key).getValue().getAsJsonObject();
    }
    public boolean getBooleanExtra(String key)
    {
        return getGist().getExtra(key).getValue().getAsBoolean();
    }
    public float getFloatExtra(String key)
    {
        return getGist().getExtra(key).getValue().getAsFloat();
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
