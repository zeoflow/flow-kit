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
import android.view.View;
import android.view.ViewGroup;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.initializer.ZeoFlowApp;
import com.zeoflow.logger.AndroidLogAdapter;
import com.zeoflow.logger.FormatStrategy;
import com.zeoflow.logger.Logger;
import com.zeoflow.logger.PrettyFormatStrategy;
import com.zeoflow.zson.Zson;

import java.util.List;

public abstract class RecyclerView
{

    public abstract static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder
    {

        public Context zContext = ZeoFlowApp.getContext();
        private String logger_tag = null;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            zContext = itemView.getContext();
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

    }

    public abstract static class Adapter<VH extends ViewHolder> extends
            androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>
    {

        public Context zContext = ZeoFlowApp.getContext();
        private String logger_tag = null;

        public Adapter()
        {
            super();
        }
        @androidx.annotation.NonNull
        @Override
        public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType)
        {
            return null;
        }
        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position, @androidx.annotation.NonNull List<Object> payloads)
        {
            super.onBindViewHolder(holder, position, payloads);
        }
        @Override
        public int getItemViewType(int position)
        {
            return super.getItemViewType(position);
        }
        @Override
        public void setHasStableIds(boolean hasStableIds)
        {
            super.setHasStableIds(hasStableIds);
        }
        @Override
        public long getItemId(int position)
        {
            return super.getItemId(position);
        }
        @Override
        public void onViewRecycled(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder holder)
        {
            super.onViewRecycled(holder);
        }
        @Override
        public boolean onFailedToRecycleView(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder holder)
        {
            return super.onFailedToRecycleView(holder);
        }
        @Override
        public void onViewAttachedToWindow(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder holder)
        {
            super.onViewAttachedToWindow(holder);
        }
        @Override
        public void onViewDetachedFromWindow(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder holder)
        {
            super.onViewDetachedFromWindow(holder);
        }
        @Override
        public void registerAdapterDataObserver(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.AdapterDataObserver observer)
        {
            super.registerAdapterDataObserver(observer);
        }
        @Override
        public void unregisterAdapterDataObserver(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.AdapterDataObserver observer)
        {
            super.unregisterAdapterDataObserver(observer);
        }
        @Override
        public void onAttachedToRecyclerView(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView recyclerView)
        {
            super.onAttachedToRecyclerView(recyclerView);
        }
        @Override
        public void onDetachedFromRecyclerView(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView recyclerView)
        {
            super.onDetachedFromRecyclerView(recyclerView);
        }
        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position)
        {

        }
        @Override
        public int getItemCount()
        {
            return 0;
        }

        // custom data
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

    }

}
