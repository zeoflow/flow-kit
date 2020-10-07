package com.zeoflow.flow.kit.crash.reporter.utils;

public class CrashReporterExceptionHandler implements Thread.UncaughtExceptionHandler
{

    private Thread.UncaughtExceptionHandler exceptionHandler;

    public CrashReporterExceptionHandler()
    {
        this.exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable)
    {

        CrashUtil.saveCrashReport(throwable);

        exceptionHandler.uncaughtException(thread, throwable);
    }
}
