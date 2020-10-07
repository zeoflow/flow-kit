package com.zeoflow.logger;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;

import static com.zeoflow.core.utils.Preconditions.checkNotNull;

public class AndroidLogAdapter implements LogAdapter
{

    @NonNull
    private final FormatStrategy formatStrategy;

    public AndroidLogAdapter()
    {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
    }

    public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy)
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
