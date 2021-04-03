package com.zeoflow.log;

import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;

import static com.zeoflow.core.utils.Preconditions.checkNotNull;

/**
 * <pre>
 *  ┌────────────────────────────────────────────
 *  │ LOGGER
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Standard logging mechanism
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ But more pretty, simple and powerful
 *  └────────────────────────────────────────────
 * </pre>
 *
 * <h3>How to use it</h3>
 * Initialize it first
 * <pre><code>
 *   Log.addLogAdapter(new AndroidLogAdapter());
 * </code></pre>
 * <p>
 * And use the appropriate static Log methods.
 *
 * <pre><code>
 *   Log.d("debug");
 *   Log.e("error");
 *   Log.w("warning");
 *   Log.v("verbose");
 *   Log.i("information");
 *   Log.wtf("What a Terrible Failure");
 * </code></pre>
 *
 * <h3>String format arguments are supported</h3>
 * <pre><code>
 *   Log.d("hello %s", "world");
 * </code></pre>
 *
 * <h3>Collections are support ed(only available for debug logs)</h3>
 * <pre><code>
 *   Log.d(MAP);
 *   Log.d(SET);
 *   Log.d(LIST);
 *   Log.d(ARRAY);
 * </code></pre>
 *
 * <h3>Json and Xml support (output will be in debug level)</h3>
 * <pre><code>
 *   Log.json(JSON_CONTENT);
 *   Log.xml(XML_CONTENT);
 * </code></pre>
 *
 * <h3>Customize Log</h3>
 * Based on your needs, you can change the following settings:
 * <ul>
 *   <li>Different {@link LogAdapter}</li>
 *   <li>Different {@link FormatStrategy}</li>
 *   <li>Different {@link LogStrategy}</li>
 * </ul>
 *
 * @see LogAdapter
 * @see FormatStrategy
 * @see LogStrategy
 */
public final class Log
{

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;
    @NonNull
    private static Printer printer = new LogPrinter();

    private Log()
    {
        //no instance
    }
    /**
     * @param tag represents the log tag
     */
    public static void configure(String tag)
    {
        if (tag == null)
        {
            tag = "Log";
        }
        clearLogAdapters();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .tag(tag)
                .build();
        addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
    public static void printer(@NonNull Printer printer)
    {
        Log.printer = checkNotNull(printer);
    }
    public static void addLogAdapter(@NonNull LogAdapter adapter)
    {
        printer.addAdapter(checkNotNull(adapter));
    }
    public static void clearLogAdapters()
    {
        printer.clearLogAdapters();
    }
    /**
     * Given tag will be used as tag only once for this method call regardless of the tag that's been
     * set during initialization. After this invocation, the general tag that's been set will
     * be used for the subsequent log calls
     */
    public static Printer t(@Nullable String tag)
    {
        return printer.t(tag);
    }
    /**
     * General log function that accepts all configurations as parameter
     */
    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable)
    {
        printer.log(priority, tag, message, throwable);
    }
    public static void d(@NonNull String message, @Nullable Object... args)
    {
        printer.d(message, args);
    }
    public static void d(@Nullable Object object)
    {
        printer.d(object);
    }
    public static void e(@NonNull String message, @Nullable Object... args)
    {
        printer.e(null, message, args);
    }
    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args)
    {
        printer.e(throwable, message, args);
    }
    public static void i(@NonNull String message, @Nullable Object... args)
    {
        printer.i(message, args);
    }
    public static void v(@NonNull String message, @Nullable Object... args)
    {
        printer.v(message, args);
    }
    public static void w(@NonNull String message, @Nullable Object... args)
    {
        printer.w(message, args);
    }
    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull String message, @Nullable Object... args)
    {
        printer.wtf(message, args);
    }
    /**
     * Formats the given json content and print it
     */
    public static void json(@Nullable String json)
    {
        printer.json(json);
    }
    /**
     * Formats the given xml content and print it
     */
    public static void xml(@Nullable String xml)
    {
        printer.xml(xml);
    }

}
