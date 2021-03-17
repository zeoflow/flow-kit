package com.zeoflow.crash.reporter.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.viewpager.widget.ViewPager;

import com.zeoflow.flow.kit.R;
import com.zeoflow.app.Activity;
import com.zeoflow.crash.reporter.CrashReporter;
import com.zeoflow.crash.reporter.adapter.MainPagerAdapter;
import com.zeoflow.crash.reporter.utils.CrashUtil;
import com.zeoflow.crash.reporter.utils.FileUtils;
import com.zeoflow.crash.reporter.utils.SimplePageChangeListener;
import com.zeoflow.material.elements.tabs.TabLayout;

import java.io.File;

import static com.zeoflow.crash.reporter.utils.Constants.CRASH_REPORTER_NOTIFICATION_ID;
import static com.zeoflow.crash.reporter.utils.Constants.LANDING;

public class CrashReporterActivity extends Activity
{

    private MainPagerAdapter mainPagerAdapter;
    private int selectedTabPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zf_cr_crash_reporter_activity);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(CRASH_REPORTER_NOTIFICATION_ID);

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
        String[] titles = {getString(R.string.zf_cr_crashes), getString(R.string.zf_cr_exceptions)};
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
        if (intent != null && !intent.getBooleanExtra(LANDING, false))
        {
            selectedTabPosition = 0;
        }
        viewPager.setCurrentItem(selectedTabPosition);
    }

}
