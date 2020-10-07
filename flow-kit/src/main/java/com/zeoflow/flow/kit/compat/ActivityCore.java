package com.zeoflow.flow.kit.compat;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.zeoflow.flow.kit.annotation.NonNull;
import com.zeoflow.flow.kit.annotation.Nullable;
import com.zeoflow.flow.kit.model.Extra;
import com.zeoflow.flow.kit.initializer.ZeoFlowApp;
import com.zeoflow.flow.kit.logger.AndroidLogAdapter;
import com.zeoflow.flow.kit.logger.FormatStrategy;
import com.zeoflow.flow.kit.logger.Logger;
import com.zeoflow.flow.kit.logger.PrettyFormatStrategy;
import com.zeoflow.zson.JsonElement;
import com.zeoflow.zson.Zson;
import com.zeoflow.zson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ActivityCore extends AppCompatActivity
{

    public Context zContext = ZeoFlowApp.getContext();
    private String logger_tag = null;

    public void withLoggerTag(@NonNull String tag)
    {
        logger_tag = tag;
    }

    public void logger(@NonNull String message, @Nullable Object... args)
    {

        boolean isDebuggable = (0 != (zContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        if (!isDebuggable)
        {
            return;
        }
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .tag(logger_tag == null || logger_tag.isEmpty() ? this.getClass().getSimpleName() : logger_tag)
            .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Logger.d(message, args);

    }

    public void logger(Object... objects)
    {

        boolean isDebuggable = (0 != (zContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        if (!isDebuggable)
        {
            return;
        }
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .tag(logger_tag == null || logger_tag.isEmpty() ? this.getClass().getSimpleName() : logger_tag)
            .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        for (Object object : objects)
        {
            Logger.json(new Zson().toJson(object));
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

    public ActivityBuilder activity(Class<?> activity)
    {
        return new ActivityBuilder(activity);
    }

}
