package com.zeoflow.crash.reporter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeoflow.annotation.NotNull;
import com.zeoflow.crash.reporter.ui.LogMessageActivity;
import com.zeoflow.crash.reporter.utils.FileUtils;
import com.zeoflow.flow.kit.R;

import java.io.File;
import java.util.ArrayList;

public class CrashLogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private final Context context;
    private ArrayList<File> crashFileList;

    public CrashLogAdapter(Context context, ArrayList<File> allCrashLogs)
    {
        this.context = context;
        crashFileList = allCrashLogs;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.zf_cr_custom_item, null);
        return new CrashLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ((CrashLogViewHolder) holder).setUpViewHolder(context, crashFileList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return crashFileList.size();
    }


    public void updateList(ArrayList<File> allCrashLogs)
    {
        crashFileList = allCrashLogs;
        notifyDataSetChanged();
    }

    private static class CrashLogViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView textViewMsg;
        private final TextView messageLogTime;

        CrashLogViewHolder(View itemView)
        {
            super(itemView);
            messageLogTime = itemView.findViewById(R.id.messageLogTime);
            textViewMsg = itemView.findViewById(R.id.textViewMsg);
        }

        void setUpViewHolder(final Context context, final File file)
        {
            final String filePath = file.getAbsolutePath();

            messageLogTime.setText(file.getName().replaceAll("[a-zA-Z_.]", ""));
            String crashLog = FileUtils.readFirstLineFromFile(new File(filePath));
            textViewMsg.setText(crashLog);

            textViewMsg.setOnClickListener(v ->
            {
                Intent intent = new Intent(context, LogMessageActivity.class);
                intent.putExtra("LogMessage", filePath);
                context.startActivity(intent);
            });
        }
    }
}
