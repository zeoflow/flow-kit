package com.zeoflow.crash.reporter.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.zeoflow.app.Activity;
import com.zeoflow.flow.kit.R;
import com.zeoflow.annotation.NonNull;
import com.zeoflow.crash.reporter.utils.AppUtils;
import com.zeoflow.crash.reporter.utils.FileUtils;
import com.zeoflow.stylar.AbstractStylarPlugin;
import com.zeoflow.stylar.Stylar;
import com.zeoflow.stylar.core.StylarTheme;
import com.zeoflow.stylar.view.StylarView;

import java.io.File;

import static com.zeoflow.crash.reporter.utils.Constants.*;

public class LogMessageActivity extends Activity
{

    private StylarView zsvAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zf_cr_activity_log_message);
        zsvAppInfo = findViewById(R.id.zsvAppInfo);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(8102020);

        if (getStringExtra("LogMessage") != null)
        {
            String dirPath = getStringExtra("LogMessage");
            if (dirPath != null && !dirPath.isEmpty())
            {
                File file = new File(dirPath);
                String crashLog = "```java\n\n" + FileUtils.readFromFile(file) + "\n\n```";
                TextView textView = findViewById(R.id.logMessage);
                final Stylar stylar = Stylar.builder(this)
                    .withCodeStyle(true)
                    .build();
                stylar.setMarkdown(textView, crashLog);
            }
        }

        getAppInfo();

        findViewById(R.id.sivDelete).setOnClickListener(view ->
        {
            Intent intent12 = getIntent();
            String filePath = null;
            if (intent12 != null)
            {
                filePath = intent12.getStringExtra("LogMessage");
            }
            if (FileUtils.delete(filePath))
            {
                finish();
            }

        });

        /*findViewById(R.id.sivShare).setOnClickListener(view ->
        {
            Intent intent1 = getIntent();
            String filePath = null;
            if (intent1 != null)
            {
                filePath = intent1.getStringExtra("LogMessage");
            }
            shareCrashReport(filePath);
        });*/
    }

    private void getAppInfo()
    {
        final Stylar stylar = Stylar.builder(this)
            .withLayoutElement(zsvAppInfo)
            .withAnchoredHeadings(true)
            .withCodeStyle(true)
            .withImagePlugins(false)
            .usePlugin(new AbstractStylarPlugin()
            {
                @Override
                public void configureTheme(@NonNull StylarTheme.Builder builder)
                {
                    builder
                        .codeTextColor(Color.parseColor("#CE570CC1"))
                        .codeBackgroundColor(Color.parseColor("#EDEDED"));
                }
            })
            .build();
        stylar.setMarkdown(AppUtils.mdDeviceDetails());
    }

    private void shareCrashReport(String filePath)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_TEXT, AppUtils.getDeviceDetails());
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Share via"));
    }
}
