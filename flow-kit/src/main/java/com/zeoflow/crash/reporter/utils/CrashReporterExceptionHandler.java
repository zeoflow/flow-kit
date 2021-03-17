package com.zeoflow.crash.reporter.utils;

import androidx.annotation.NonNull;

public class CrashReporterExceptionHandler implements Thread.UncaughtExceptionHandler
{

    private final Thread.UncaughtExceptionHandler exceptionHandler;

    public CrashReporterExceptionHandler()
    {
        this.exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable)
    {
        CrashUtil.saveCrashReport(throwable);
        exceptionHandler.uncaughtException(thread, throwable);
    }
}
