package com.zeoflow.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zeoflow.flow.kit.compat.ActivityCore;
import com.zeoflow.flow.kit.crash.reporter.ui.CrashReporterActivity;

public class MainActivity extends ActivityCore
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(CrashReporterActivity.class);

    }
}
