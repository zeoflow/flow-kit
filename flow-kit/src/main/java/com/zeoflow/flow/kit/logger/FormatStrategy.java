package com.zeoflow.flow.kit.logger;

import com.zeoflow.flow.kit.annotation.NonNull;
import com.zeoflow.flow.kit.annotation.Nullable;

/**
 * Used to determine how messages should be printed or saved.
 *
 * @see PrettyFormatStrategy
 * @see CsvFormatStrategy
 */
public interface FormatStrategy
{

    void log(int priority, @Nullable String tag, @NonNull String message);
}
