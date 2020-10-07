package com.zeoflow.flow.kit.crash.reporter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.viewpager.widget.ViewPager;

import com.zeoflow.flow.kit.R;
import com.zeoflow.flow.kit.compat.ActivityCore;
import com.zeoflow.flow.kit.crash.reporter.CrashReporter;
import com.zeoflow.flow.kit.crash.reporter.adapter.MainPagerAdapter;
import com.zeoflow.flow.kit.crash.reporter.utils.Constants;
import com.zeoflow.flow.kit.crash.reporter.utils.CrashUtil;
import com.zeoflow.flow.kit.crash.reporter.utils.FileUtils;
import com.zeoflow.flow.kit.crash.reporter.utils.SimplePageChangeListener;
import com.zeoflow.material.elements.tabs.TabLayout;

import java.io.File;

public class CrashReporterActivity extends ActivityCore
{

    private MainPagerAdapter mainPagerAdapter;
    private int selectedTabPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_reporter_activity);

        ViewPager viewPager = findViewById(R.id.viewpager);
        if (viewPager != null)
        {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.sivDelete).setOnClickListener(view -> clearCrashLog());
    }

    private void clearCrashLog()
    {
        new Thread(() ->
        {
            String crashReportPath = TextUtils.isEmpty(CrashReporter.getCrashReportPath()) ?
                CrashUtil.getDefaultPath() : CrashReporter.getCrashReportPath();

            File[] logs = new File(crashReportPath).listFiles();
            assert logs != null;
            for (File file : logs)
            {
                FileUtils.delete(file);
            }
            runOnUiThread(() -> mainPagerAdapter.clearLogs());
        }).start();
    }

    private void setupViewPager(ViewPager viewPager)
    {
        String[] titles = {getString(R.string.crashes), getString(R.string.exceptions)};
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), titles);
        viewPager.setAdapter(mainPagerAdapter);

        viewPager.addOnPageChangeListener(new SimplePageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                selectedTabPosition = position;
            }
        });

        Intent intent = getIntent();
        if (intent != null && !intent.getBooleanExtra(Constants.LANDING, false))
        {
            selectedTabPosition = 0;
        }
        viewPager.setCurrentItem(selectedTabPosition);
    }

}
