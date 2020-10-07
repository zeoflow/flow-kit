package com.zeoflow.flow.kit.crash.reporter;

import android.content.Intent;

import com.zeoflow.flow.kit.crash.reporter.ui.CrashReporterActivity;
import com.zeoflow.flow.kit.crash.reporter.utils.CrashReporterExceptionHandler;
import com.zeoflow.flow.kit.crash.reporter.utils.CrashUtil;

import static com.zeoflow.flow.kit.initializer.ZeoFlowApp.getContext;

public class CrashReporter
{

    private static String crashReportPath;
    private static boolean isNotificationEnabled = true;

    private CrashReporter()
    {
        // This class in not publicly instantiable
    }

    public static void initialize()
    {
        setUpExceptionHandler();
    }

    public static void initialize(String crashReportSavePath)
    {
        crashReportPath = crashReportSavePath;
        setUpExceptionHandler();
    }

    private static void setUpExceptionHandler()
    {
        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CrashReporterExceptionHandler))
        {
            Thread.setDefaultUncaughtExceptionHandler(new CrashReporterExceptionHandler());
        }
    }

    public static String getCrashReportPath()
    {
        return crashReportPath;
    }

    public static boolean isNotificationEnabled()
    {
        return isNotificationEnabled;
    }

    //LOG Exception APIs
    public static void logException(Exception exception)
    {
        CrashUtil.logException(exception);
    }

    public static Intent getLaunchIntent()
    {
        return new Intent(getContext(), CrashReporterActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static void disableNotification()
    {
        isNotificationEnabled = false;
    }

}
