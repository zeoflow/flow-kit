package com.zeoflow.flow.kit.compat;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.zeoflow.flow.kit.annotation.NonNull;
import com.zeoflow.flow.kit.annotation.Nullable;
import com.zeoflow.flow.kit.initializer.ZeoFlowApp;
import com.zeoflow.flow.kit.logger.AndroidLogAdapter;
import com.zeoflow.flow.kit.logger.FormatStrategy;
import com.zeoflow.flow.kit.logger.Logger;
import com.zeoflow.flow.kit.logger.PrettyFormatStrategy;
import com.zeoflow.zson.Zson;

public class RVHolderCore extends RecyclerView.ViewHolder
{

    public Context zContext = ZeoFlowApp.getContext();
    private String logger_tag = null;

    public RVHolderCore(@NonNull View itemView)
    {
        super(itemView);
    }

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
