package com.zeoflow.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.initializer.ZeoFlowApp;
import com.zeoflow.log.AndroidLogAdapter;
import com.zeoflow.log.FormatStrategy;
import com.zeoflow.log.Log;
import com.zeoflow.log.PrettyFormatStrategy;
import com.zeoflow.zson.Zson;

public class RVHolderCore extends RecyclerView.ViewHolder
{

    public Context zContext = ZeoFlowApp.getContext();
    private String log_tag = getClass().getSimpleName();

    public RVHolderCore(@NonNull View itemView)
    {
        super(itemView);
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
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .tag(log_tag == null || log_tag.isEmpty() ? this.getClass().getSimpleName() : log_tag)
            .build();
        Log.addLogAdapter(new AndroidLogAdapter(formatStrategy));

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

    public int zPosition = getPos();

    private int getPos()
    {
        return getLayoutPosition();
    }

    public int zViewType = getViewType();

    private int getViewType()
    {
        return getItemViewType();
    }

}
