package com.zeoflow.crash.reporter.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.zeoflow.compat.EntityCore;
import com.zeoflow.crash.reporter.CrashReporter;
import com.zeoflow.crash.reporter.service.NotificationService;
import com.zeoflow.crash.reporter.ui.CrashReporterActivity;
import com.zeoflow.crash.reporter.ui.LogMessageActivity;
import com.zeoflow.flow.kit.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.zeoflow.crash.reporter.utils.Constants.ACTION_CR_ZF_DELETE;
import static com.zeoflow.crash.reporter.utils.Constants.ACTION_CR_ZF_SHARE;
import static com.zeoflow.crash.reporter.utils.Constants.CHANNEL_NOTIFICATION_ID;
import static com.zeoflow.initializer.ZeoFlowApp.getContext;

public class CrashUtil extends EntityCore
{

    private static final String TAG = CrashUtil.class.getSimpleName();

    private CrashUtil()
    {
        //this class is not publicly instantiable
    }

    private static String getCrashLogTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private static String getFileName()
    {
        return Constants.CRASH_PREFIX + getCrashLogTime() + Constants.FILE_EXTENSION;
    }

    public static void saveCrashReport(final Throwable throwable)
    {

        String crashReportPath = CrashReporter.getCrashReportPath();
        String filePath = writeToFile(crashReportPath, getFileName(), getStackTrace(throwable));

        showNotification(throwable.getLocalizedMessage(), true, filePath);
    }

    public static void logException(final Exception exception)
    {

        new Thread(() ->
        {
            String crashReportPath = CrashReporter.getCrashReportPath();
            String filePath = writeToFile(crashReportPath, getFileName(), getStackTrace(exception));

            showNotification(exception.getLocalizedMessage(), false, filePath);
        }).start();
    }

    private static String writeToFile(String crashReportPath, String filename, String crashLog)
    {

        if (TextUtils.isEmpty(crashReportPath))
        {
            crashReportPath = getDefaultPath();
        }

        File crashDir = new File(crashReportPath);
        if (!crashDir.exists() || !crashDir.isDirectory())
        {
            crashReportPath = getDefaultPath();
            Log.e(TAG, "Path provided doesn't exists : " + crashDir + "\nSaving crash report at : " + getDefaultPath());
        }

        BufferedWriter bufferedWriter;
        try
        {
            bufferedWriter = new BufferedWriter(new FileWriter(
                crashReportPath + File.separator + filename));

            bufferedWriter.write(crashLog);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return crashReportPath + File.separator + filename;
    }

    private static void showNotification(String localisedMsg, boolean isCrash)
    {
        showNotification(localisedMsg, isCrash, "");
    }

    private static void showNotification(String localisedMsg, boolean isCrash, String filePath)
    {

        if (CrashReporter.isNotificationEnabled())
        {
            Context context = getContext();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            createNotificationChannel(notificationManager);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_NOTIFICATION_ID);
            builder.setSmallIcon(R.drawable.zf_cr_ic_crash_notification);
            builder.setAutoCancel(true);
            builder.setColor(ContextCompat.getColor(context, R.color.zf_cr_colorAccent_CrashReporter));

            Intent intent = CrashReporter.getLaunchIntent();
            intent.putExtra(Constants.LANDING, isCrash);
            intent.setAction(Long.toString(System.currentTimeMillis()));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentIntent(pendingIntent);
            builder.setContentTitle(context.getString(R.string.zf_cr_view_crash_report));

            if (TextUtils.isEmpty(localisedMsg))
            {
                builder.setContentText(context.getString(R.string.zf_cr_check_your_message_here));

                Intent viewLogIntent = new Intent(context, CrashReporterActivity.class);
                PendingIntent viewLogPendingIntent = PendingIntent.getActivity(context, 0, viewLogIntent, 0);
                builder.addAction(R.drawable.zf_cr_ic_warning_black_24dp, "View Crashes", viewLogPendingIntent);
            } else
            {
                if (localisedMsg.length() > 100)
                {
                    localisedMsg = localisedMsg.substring(0, 97) + "...";
                }
                builder
                    .setContentText(localisedMsg)
                    .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(localisedMsg));

                Intent viewLogIntent = new Intent(context, LogMessageActivity.class);
                viewLogIntent.putExtra("LogMessage", filePath);
                PendingIntent viewLogPendingIntent = PendingIntent.getActivity(context, 0, viewLogIntent, 0);

                Intent deleteAction = new Intent(context, NotificationService.class);
                deleteAction.putExtra("LogMessage", filePath);
                deleteAction.setAction(ACTION_CR_ZF_DELETE);
                PendingIntent deleteActionPending = PendingIntent.getService(context, 0, deleteAction, 0);

                Intent shareAction = new Intent(context, LogMessageActivity.class);
                shareAction.putExtra("LogMessage", filePath);
                PendingIntent shareActionPending = PendingIntent.getActivity(context, 0, shareAction, 0);

                builder.addAction(R.drawable.zf_cr_ic_warning_black_24dp, "View", viewLogPendingIntent);
                builder.addAction(R.drawable.zf_cr_ic_warning_black_24dp, "Delete", deleteActionPending);
                builder.addAction(R.drawable.zf_cr_ic_warning_black_24dp, "Share", shareActionPending);
            }

            notificationManager.notify(Constants.CRASH_REPORTER_NOTIFICATION_ID, builder.build());
        }
    }

    private static void createNotificationChannel(NotificationManager notificationManager)
    {
        if (Build.VERSION.SDK_INT >= 26)
        {
            String title = "Crash Reporter";
            String description = "When a crash occurs or an exception, a notification will be pushed.";
            NotificationChannel channel = new NotificationChannel(CHANNEL_NOTIFICATION_ID, title, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static String getStackTrace(Throwable e)
    {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        String crashLog = result.toString();
        printWriter.close();
        return crashLog;
    }

    public static String getDefaultPath()
    {
        String defaultPath = Objects.requireNonNull(getContext().getExternalFilesDir(null)).getAbsolutePath()
            + File.separator + Constants.CRASH_REPORT_DIR;

        File file = new File(defaultPath);
        boolean isDirectoryCreated = file.mkdirs();
        return defaultPath;
    }
}
