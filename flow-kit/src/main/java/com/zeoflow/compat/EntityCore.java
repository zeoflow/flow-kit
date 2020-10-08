package com.zeoflow.compat;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.initializer.ZeoFlowApp;
import com.zeoflow.logger.AndroidLogAdapter;
import com.zeoflow.logger.FormatStrategy;
import com.zeoflow.logger.Logger;
import com.zeoflow.logger.PrettyFormatStrategy;
import com.zeoflow.zson.Zson;

public class EntityCore
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

    public void startActivity(Class<?> activity)
    {
        new ActivityBuilder(activity).start();
    }

    public ActivityBuilder configureNewActivity(Class<?> activity)
    {
        return new ActivityBuilder(activity);
    }

}
