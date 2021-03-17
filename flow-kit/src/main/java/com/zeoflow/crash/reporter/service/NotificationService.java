package com.zeoflow.crash.reporter.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.zeoflow.crash.reporter.utils.AppUtils;
import com.zeoflow.crash.reporter.utils.FileUtils;

import java.io.File;

import static com.zeoflow.crash.reporter.utils.Constants.ACTION_CR_ZF_DELETE;
import static com.zeoflow.crash.reporter.utils.Constants.ACTION_CR_ZF_SHARE;
import static com.zeoflow.crash.reporter.utils.Constants.CRASH_REPORTER_NOTIFICATION_ID;

public class NotificationService extends Service
{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent.getAction() != null)
        {
            if (intent.getAction().equals("DELETE_ACTION_CR_ZF"))
            {
                if (FileUtils.delete(intent.getStringExtra("LogMessage")))
                {
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(8102020);
                }
            } else if (intent.getAction().equals("SHARE_ACTION_CR_ZF"))
            {
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("*/*");
                intentShare.putExtra(Intent.EXTRA_TEXT, AppUtils.getDeviceDetails());
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(intent.getStringExtra("LogMessage")));
                intentShare.putExtra(Intent.EXTRA_STREAM, photoURI);
                Intent share = Intent.createChooser(intentShare, "Share Crash Report via");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(share);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
