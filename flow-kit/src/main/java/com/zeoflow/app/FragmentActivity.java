package com.zeoflow.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.initializer.ZeoFlowApp;
import com.zeoflow.logger.AndroidLogAdapter;
import com.zeoflow.logger.FormatStrategy;
import com.zeoflow.logger.Logger;
import com.zeoflow.logger.PrettyFormatStrategy;
import com.zeoflow.model.Extra;
import com.zeoflow.zson.JsonElement;
import com.zeoflow.zson.Zson;
import com.zeoflow.zson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FragmentActivity extends androidx.fragment.app.FragmentActivity
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
