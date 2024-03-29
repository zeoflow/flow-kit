package com.zeoflow.crash.reporter.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeoflow.flow.kit.R;
import com.zeoflow.app.Fragment;
import com.zeoflow.crash.reporter.CrashReporter;
import com.zeoflow.crash.reporter.adapter.CrashLogAdapter;
import com.zeoflow.crash.reporter.utils.CrashUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

import static com.zeoflow.crash.reporter.utils.Constants.EXCEPTION_SUFFIX;

public class CrashLogFragment extends Fragment
{

    private CrashLogAdapter logAdapter;

    private RecyclerView crashRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.zf_cr_crash_log, container, false);
        crashRecyclerView = view.findViewById(R.id.crashRecyclerView);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadAdapter(getActivity(), crashRecyclerView);
    }

    private void loadAdapter(Context context, RecyclerView crashRecyclerView)
    {

        logAdapter = new CrashLogAdapter(context, getAllCrashes());
        crashRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        crashRecyclerView.setAdapter(logAdapter);
    }

    public void clearLog()
    {
        if (logAdapter != null)
        {
            logAdapter.updateList(getAllCrashes());
        }
    }

    private ArrayList<File> getAllCrashes()
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
        ArrayList<File> listOfFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.listFiles())));
        for (Iterator<File> iterator = listOfFiles.iterator(); iterator.hasNext(); )
        {
            if (iterator.next().getName().contains("_exception"))
            {
                iterator.remove();
            }
        }
        Collections.sort(listOfFiles, Collections.reverseOrder());
        return listOfFiles;
    }

}
