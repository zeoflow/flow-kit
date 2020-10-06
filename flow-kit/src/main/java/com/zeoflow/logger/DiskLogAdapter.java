package com.zeoflow.logger;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;

import static com.zeoflow.core.utils.Preconditions.checkNotNull;

/**
 * This is used to saves log messages to the disk.
 * By default it uses {@link CsvFormatStrategy} to translates text message into CSV format.
 */
public class DiskLogAdapter implements LogAdapter
{

    @NonNull
    private final FormatStrategy formatStrategy;

    public DiskLogAdapter()
    {
        formatStrategy = CsvFormatStrategy.newBuilder().build();
    }

    public DiskLogAdapter(@NonNull FormatStrategy formatStrategy)
    {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag)
    {
        return true;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message)
    {
        formatStrategy.log(priority, tag, message);
    }
}
