package com.zeoflow.crash.reporter.utils;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CrashReporterException extends RuntimeException
{
    static final long serialVersionUID = 1;

    /**
     * Constructs a new CrashReporterException.
     */
    public CrashReporterException()
    {
        super();
    }

    /**
     * Constructs a new CrashReporterException.
     *
     * @param message the detail message of this exception
     */
    public CrashReporterException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new CrashReporterException.
     *
     * @param format the format string (see {@link java.util.Formatter#format})
     * @param args   the list of arguments passed to the formatter.
     */
    public CrashReporterException(String format, Object... args)
    {
        this(String.format(format, args));
    }

    /**
     * Constructs a new CrashReporterException.
     *
     * @param message   the detail message of this exception
     * @param throwable the cause of this exception
     */
    public CrashReporterException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

    /**
     * Constructs a new CrashReporterException.
     *
     * @param throwable the cause of this exception
     */
    public CrashReporterException(Throwable throwable)
    {
        super(throwable);
    }

    @NonNull
    @Override
    public String toString()
    {
        // Throwable.toString() returns "CrashReporterException:{message}". Returning just "{message}"
        // should be fine here.
        return Objects.requireNonNull(getMessage());
    }
}
