// Copyright 2021 ZeoFlow SRL
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.zeoflow.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Provides convenient methods to some common operations
 */
final class Utils
{

    private Utils()
    {
        // Hidden constructor.
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    static boolean isEmpty(CharSequence str)
    {
        return str == null || str.length() == 0;
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     *
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     * <p>
     * NOTE: Logic slightly change due to strict policy on CI -
     * "Inner assignments should be avoided"
     */
    static boolean equals(CharSequence a, CharSequence b)
    {
        if (a == b) return true;
        if (a != null && b != null)
        {
            int length = a.length();
            if (length == b.length())
            {
                if (a instanceof String && b instanceof String)
                {
                    return a.equals(b);
                } else
                {
                    for (int i = 0; i < length; i++)
                    {
                        if (a.charAt(i) != b.charAt(i)) return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Copied from "android.util.Log.getStackTraceString()" in order to avoid usage of Android stack
     * in unit tests.
     *
     * @return Stack trace in form of String
     */
    static String getStackTraceString(Throwable tr)
    {
        if (tr == null)
        {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null)
        {
            if (t instanceof UnknownHostException)
            {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    static String logLevel(int value)
    {
        switch (value)
        {
            case Log.VERBOSE:
                return "VERBOSE";
            case Log.DEBUG:
                return "DEBUG";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.ERROR:
                return "ERROR";
            case Log.ASSERT:
                return "ASSERT";
            default:
                return "UNKNOWN";
        }
    }

    public static String toString(Object object)
    {
        if (object == null)
        {
            return "null";
        }
        if (!object.getClass().isArray())
        {
            return object.toString();
        }
        if (object instanceof boolean[])
        {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[])
        {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[])
        {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[])
        {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[])
        {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[])
        {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[])
        {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[])
        {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[])
        {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }
}
