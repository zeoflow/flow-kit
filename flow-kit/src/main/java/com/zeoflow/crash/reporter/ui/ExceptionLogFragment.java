package com.zeoflow.crash.reporter.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeoflow.flow.kit.R;
import com.zeoflow.compat.FragmentCore;
import com.zeoflow.crash.reporter.CrashReporter;
import com.zeoflow.crash.reporter.adapter.CrashLogAdapter;
import com.zeoflow.crash.reporter.utils.Constants;
import com.zeoflow.crash.reporter.utils.CrashUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class ExceptionLogFragment extends FragmentCore
{

    private CrashLogAdapter logAdapter;

    private RecyclerView exceptionRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        exceptionRecyclerView = findViewById(R.id.exceptionRecyclerView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.zf_cr_exception_log, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadAdapter(getActivity(), exceptionRecyclerView);
    }

    private void loadAdapter(Context context, RecyclerView exceptionRecyclerView)
    {

        logAdapter = new CrashLogAdapter(context, getAllExceptions());
        exceptionRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        exceptionRecyclerView.setAdapter(logAdapter);
    }

    public void clearLog()
    {
        if (logAdapter != null)
        {
            logAdapter.updateList(getAllExceptions());
        }
    }

    public ArrayList<File> getAllExceptions()
    {
        String directoryPath;
        String crashReportPath = CrashReporter.getCrashReportPath();

        if (TextUtils.isEmpty(crashReportPath))
        {
            directoryPath = CrashUtil.getDefaultPath();
        } else
        {
            directoryPath = crashReportPath;
        }

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory())
        {
            throw new RuntimeException("The path provided doesn't exists : " + directoryPath);
        }

        ArrayList<File> listOfFiles = new ArrayList<>(Arrays.asList(directory.listFiles()));
        for (Iterator<File> iterator = listOfFiles.iterator(); iterator.hasNext(); )
        {
            if (iterator.next().getName().contains(Constants.CRASH_SUFFIX))
            {
                iterator.remove();
            }
        }
        Collections.sort(listOfFiles, Collections.reverseOrder());
        return listOfFiles;
    }

}
