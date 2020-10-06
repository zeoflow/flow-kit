/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zeoflow.core.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.zeoflow.annotation.IntRange;
import com.zeoflow.annotation.KeepForApi;
import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.Nullable;
import com.zeoflow.annotation.RequiresApi;
import com.zeoflow.annotation.RestrictTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Simple static methods to be called at the start of your own methods to verify
 * correct arguments and state.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
public final class Preconditions
{

    private Preconditions()
    {
    }

    @KeepForApi
    public static String checkNotEmpty(String var0)
    {
        if (TextUtils.isEmpty(var0))
        {
            throw new IllegalArgumentException("Given String is empty or null");
        } else
        {
            return var0;
        }
    }

    @KeepForApi
    public static String checkNotEmpty(String var0, Object var1)
    {
        if (TextUtils.isEmpty(var0))
        {
            throw new IllegalArgumentException(String.valueOf(var1));
        } else
        {
            return var0;
        }
    }

    @KeepForApi
    @NonNull
    public static <T> T checkNotNull(T var0, Object var1)
    {
        if (var0 == null)
        {
            throw new NullPointerException(String.valueOf(var1));
        } else
        {
            return var0;
        }
    }

    @KeepForApi
    public static int checkNotZero(int var0, Object var1)
    {
        if (var0 == 0)
        {
            throw new IllegalArgumentException(String.valueOf(var1));
        } else
        {
            return var0;
        }
    }

    @KeepForApi
    public static int checkNotZero(int var0)
    {
        if (var0 == 0)
        {
            throw new IllegalArgumentException("Given Integer is zero");
        } else
        {
            return var0;
        }
    }

    @KeepForApi
    public static long checkNotZero(long var0, Object var2)
    {
        if (var0 == 0L)
        {
            throw new IllegalArgumentException(String.valueOf(var2));
        } else
        {
            return var0;
        }
    }

    @KeepForApi
    public static long checkNotZero(long var0)
    {
        if (var0 == 0L)
        {
            throw new IllegalArgumentException("Given Long is zero");
        } else
        {
            return var0;
        }
    }

    @KeepForApi
    public static void checkState(boolean var0, Object var1)
    {
        if (!var0)
        {
            throw new IllegalStateException(String.valueOf(var1));
        }
    }

    @KeepForApi
    public static void checkState(boolean var0, String var1, Object... var2)
    {
        if (!var0)
        {
            throw new IllegalStateException(String.format(var1, var2));
        }
    }

    @KeepForApi
    public static void checkMainThread(String var0)
    {
        if (!isMainThread())
        {
            throw new IllegalStateException(var0);
        }
    }

    @KeepForApi
    public static void checkNotMainThread()
    {
        checkNotMainThread("Must not be called on the main application thread");
    }

    @KeepForApi
    public static void checkNotMainThread(String var0)
    {
        if (isMainThread())
        {
            throw new IllegalStateException(var0);
        }
    }

    @KeepForApi
    public static void checkHandlerThread(Handler var0)
    {
        checkHandlerThread(var0, "Must be called on the handler thread");
    }

    @KeepForApi
    public static void checkHandlerThread(Handler var0, String var1)
    {
        if (Looper.myLooper() != var0.getLooper())
        {
            throw new IllegalStateException(var1);
        }
    }

    private static boolean isMainThread()
    {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static void checkArgument(boolean expression)
    {
        if (!expression)
        {
            throw new IllegalArgumentException();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier)
    {
        return Objects.requireNonNull(obj, messageSupplier);
    }

    public static <T> T requireNonNullElseGet(T obj, Supplier<? extends T> supplier)
    {
        return Objects.requireNonNullElseGet(obj, supplier);
    }

    public static <T> T requireNonNullElse(T obj, T defaultObj)
    {
        return Objects.requireNonNullElse(obj, defaultObj);
    }

    public static <T> T requireNonNull(T obj, String message)
    {
        return Objects.requireNonNull(obj);
    }

    public static <T> T requireNonNull(T obj)
    {
        return Objects.requireNonNull(obj);
    }

    /**
     * Ensures that an expression checking an argument is true.
     *
     * @param expression   the expression to check
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, @NonNull Object errorMessage)
    {
        if (!expression)
        {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures that an string reference passed as a parameter to the calling
     * method is not empty.
     *
     * @param string an string reference
     * @return the string reference that was validated
     * @throws IllegalArgumentException if {@code string} is empty
     */
    public static @NonNull
    <T extends CharSequence> T checkStringNotEmpty(
        @Nullable final T string)
    {
        if (TextUtils.isEmpty(string))
        {
            throw new IllegalArgumentException();
        }
        return string;
    }

    /**
     * Ensures that an string reference passed as a parameter to the calling
     * method is not empty.
     *
     * @param string       an string reference
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @return the string reference that was validated
     * @throws IllegalArgumentException if {@code string} is empty
     */
    public static @NonNull
    <T extends CharSequence> T checkStringNotEmpty(
        @Nullable final T string, @NonNull final Object errorMessage)
    {
        if (TextUtils.isEmpty(string))
        {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return string;
    }

    /**
     * Ensures that an string reference passed as a parameter to the calling method is not empty.
     *
     * @param string          an string reference
     * @param messageTemplate a printf-style message template to use if the check fails; will be
     *                        converted to a string using {@link String#format(String, Object...)}
     * @param messageArgs     arguments for {@code messageTemplate}
     * @return the string reference that was validated
     * @throws IllegalArgumentException if {@code string} is empty
     */
    public static @NonNull
    <T extends CharSequence> T checkStringNotEmpty(
        @Nullable final T string, @NonNull final String messageTemplate,
        @NonNull final Object... messageArgs)
    {
        if (TextUtils.isEmpty(string))
        {
            throw new IllegalArgumentException(String.format(messageTemplate, messageArgs));
        }
        return string;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static @NonNull
    <T> T checkNotNull(@Nullable T reference)
    {
        if (reference == null)
        {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param message    exception message
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(boolean expression, @Nullable String message)
    {
        if (!expression)
        {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(final boolean expression)
    {
        checkState(expression, null);
    }

    /**
     * Ensures that that the argument numeric value is non-negative.
     *
     * @param value        a numeric int value
     * @param errorMessage the exception message to use if the check fails
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was negative
     */
    public static @IntRange(from = 0)
    int checkArgumentNonnegative(final int value,
                                 @Nullable String errorMessage)
    {
        if (value < 0)
        {
            throw new IllegalArgumentException(errorMessage);
        }

        return value;
    }

    /**
     * Ensures that that the argument numeric value is non-negative.
     *
     * @param value a numeric int value
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was negative
     */
    public static @IntRange(from = 0)
    int checkArgumentNonnegative(final int value)
    {
        if (value < 0)
        {
            throw new IllegalArgumentException();
        }

        return value;
    }

    /**
     * Ensures that the argument int value is within the inclusive range.
     *
     * @param value     a int value
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated int value
     * @throws IllegalArgumentException if {@code value} was not within the range
     */
    public static int checkArgumentInRange(int value, int lower, int upper,
                                           @NonNull String valueName)
    {
        if (value < lower)
        {
            throw new IllegalArgumentException(
                String.format(Locale.US,
                    "%s is out of range of [%d, %d] (too low)", valueName, lower, upper));
        } else if (value > upper)
        {
            throw new IllegalArgumentException(
                String.format(Locale.US,
                    "%s is out of range of [%d, %d] (too high)", valueName, lower, upper));
        }

        return value;
    }

    /**
     * Ensures that an expression checking an argument is true.
     *
     * @param expression      the expression to check
     * @param messageTemplate a printf-style message template to use if the check fails; will
     *                        be converted to a string using {@link String#format(String, Object...)}
     * @param messageArgs     arguments for {@code messageTemplate}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression,
                                     final String messageTemplate,
                                     final Object... messageArgs)
    {
        if (!expression)
        {
            throw new IllegalArgumentException(String.format(messageTemplate, messageArgs));
        }
    }

    /**
     * Check the requested flags, throwing if any requested flags are outside
     * the allowed set.
     *
     * @return the validated requested flags.
     */
    public static int checkFlagsArgument(final int requestedFlags, final int allowedFlags)
    {
        if ((requestedFlags & allowedFlags) != requestedFlags)
        {
            throw new IllegalArgumentException("Requested flags 0x"
                + Integer.toHexString(requestedFlags) + ", but only 0x"
                + Integer.toHexString(allowedFlags) + " are allowed");
        }

        return requestedFlags;
    }

    /**
     * Ensures that that the argument numeric value is non-negative (greater than or equal to 0).
     *
     * @param value a numeric long value
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was negative
     */
    public static long checkArgumentNonnegative(final long value)
    {
        if (value < 0)
        {
            throw new IllegalArgumentException();
        }

        return value;
    }

    /**
     * Ensures that that the argument numeric value is non-negative (greater than or equal to 0).
     *
     * @param value        a numeric long value
     * @param errorMessage the exception message to use if the check fails
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was negative
     */
    public static long checkArgumentNonnegative(final long value, final String errorMessage)
    {
        if (value < 0)
        {
            throw new IllegalArgumentException(errorMessage);
        }

        return value;
    }

    /**
     * Ensures that that the argument numeric value is positive (greater than 0).
     *
     * @param value        a numeric int value
     * @param errorMessage the exception message to use if the check fails
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was not positive
     */
    public static int checkArgumentPositive(final int value, final String errorMessage)
    {
        if (value <= 0)
        {
            throw new IllegalArgumentException(errorMessage);
        }

        return value;
    }

    /**
     * Ensures that the argument floating point value is non-negative (greater than or equal to 0).
     *
     * @param value        a floating point value
     * @param errorMessage the exteption message to use if the check fails
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was negative
     */
    public static float checkArgumentNonNegative(final float value, final String errorMessage)
    {
        if (value < 0)
        {
            throw new IllegalArgumentException(errorMessage);
        }

        return value;
    }

    /**
     * Ensures that the argument floating point value is positive (greater than 0).
     *
     * @param value        a floating point value
     * @param errorMessage the exteption message to use if the check fails
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was not positive
     */
    public static float checkArgumentPositive(final float value, final String errorMessage)
    {
        if (value <= 0)
        {
            throw new IllegalArgumentException(errorMessage);
        }

        return value;
    }

    /**
     * Ensures that the argument floating point value is a finite number.
     *
     * <p>A finite number is defined to be both representable (that is, not NaN) and
     * not infinite (that is neither positive or negative infinity).</p>
     *
     * @param value     a floating point value
     * @param valueName the name of the argument to use if the check fails
     * @return the validated floating point value
     * @throws IllegalArgumentException if {@code value} was not finite
     */
    public static float checkArgumentFinite(final float value, final String valueName)
    {
        if (Float.isNaN(value))
        {
            throw new IllegalArgumentException(valueName + " must not be NaN");
        } else if (Float.isInfinite(value))
        {
            throw new IllegalArgumentException(valueName + " must not be infinite");
        }

        return value;
    }

    /**
     * Ensures that the argument floating point value is within the inclusive range.
     *
     * <p>While this can be used to range check against +/- infinity, note that all NaN numbers
     * will always be out of range.</p>
     *
     * @param value     a floating point value
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated floating point value
     * @throws IllegalArgumentException if {@code value} was not within the range
     */
    public static float checkArgumentInRange(float value, float lower, float upper,
                                             String valueName)
    {
        if (Float.isNaN(value))
        {
            throw new IllegalArgumentException(valueName + " must not be NaN");
        } else if (value < lower)
        {
            throw new IllegalArgumentException(
                String.format(
                    "%s is out of range of [%f, %f] (too low)", valueName, lower, upper));
        } else if (value > upper)
        {
            throw new IllegalArgumentException(
                String.format(
                    "%s is out of range of [%f, %f] (too high)", valueName, lower, upper));
        }

        return value;
    }

    /**
     * Ensures that the argument long value is within the inclusive range.
     *
     * @param value     a long value
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated long value
     * @throws IllegalArgumentException if {@code value} was not within the range
     */
    public static long checkArgumentInRange(long value, long lower, long upper,
                                            String valueName)
    {
        if (value < lower)
        {
            throw new IllegalArgumentException(
                String.format(
                    "%s is out of range of [%d, %d] (too low)", valueName, lower, upper));
        } else if (value > upper)
        {
            throw new IllegalArgumentException(
                String.format(
                    "%s is out of range of [%d, %d] (too high)", valueName, lower, upper));
        }

        return value;
    }

    /**
     * Ensures that the array is not {@code null}, and none of its elements are {@code null}.
     *
     * @param value     an array of boxed objects
     * @param valueName the name of the argument to use if the check fails
     * @return the validated array
     * @throws NullPointerException if the {@code value} or any of its elements were {@code null}
     */
    public static <T> T[] checkArrayElementsNotNull(final T[] value, final String valueName)
    {
        if (value == null)
        {
            throw new NullPointerException(valueName + " must not be null");
        }

        for (int i = 0; i < value.length; ++i)
        {
            if (value[i] == null)
            {
                throw new NullPointerException(
                    String.format("%s[%d] must not be null", valueName, i));
            }
        }

        return value;
    }

    /**
     * Ensures that the {@link Collection} is not {@code null}, and none of its elements are
     * {@code null}.
     *
     * @param value     a {@link Collection} of boxed objects
     * @param valueName the name of the argument to use if the check fails
     * @return the validated {@link Collection}
     * @throws NullPointerException if the {@code value} or any of its elements were {@code null}
     */
    public static @NonNull
    <C extends Collection<T>, T> C checkCollectionElementsNotNull(
        final C value, final String valueName)
    {
        if (value == null)
        {
            throw new NullPointerException(valueName + " must not be null");
        }

        long ctr = 0;
        for (T elem : value)
        {
            if (elem == null)
            {
                throw new NullPointerException(
                    String.format("%s[%d] must not be null", valueName, ctr));
            }
            ++ctr;
        }

        return value;
    }

    /**
     * Ensures that the {@link Collection} is not {@code null}, and contains at least one element.
     *
     * @param value     a {@link Collection} of boxed elements.
     * @param valueName the name of the argument to use if the check fails.
     * @return the validated {@link Collection}
     * @throws NullPointerException     if the {@code value} was {@code null}
     * @throws IllegalArgumentException if the {@code value} was empty
     */
    public static <T> Collection<T> checkCollectionNotEmpty(final Collection<T> value,
                                                            final String valueName)
    {
        if (value == null)
        {
            throw new NullPointerException(valueName + " must not be null");
        }
        if (value.isEmpty())
        {
            throw new IllegalArgumentException(valueName + " is empty");
        }
        return value;
    }

    /**
     * Ensures that the given byte array is not {@code null}, and contains at least one element.
     *
     * @param value     an array of elements.
     * @param valueName the name of the argument to use if the check fails.
     * @return the validated array
     * @throws NullPointerException     if the {@code value} was {@code null}
     * @throws IllegalArgumentException if the {@code value} was empty
     */
    @NonNull
    public static byte[] checkByteArrayNotEmpty(final byte[] value, final String valueName)
    {
        if (value == null)
        {
            throw new NullPointerException(valueName + " must not be null");
        }
        if (value.length == 0)
        {
            throw new IllegalArgumentException(valueName + " is empty");
        }
        return value;
    }

    /**
     * Ensures that argument {@code value} is one of {@code supportedValues}.
     *
     * @param supportedValues an array of string values
     * @param value           a string value
     * @return the validated value
     * @throws NullPointerException     if either {@code value} or {@code supportedValues} is null
     * @throws IllegalArgumentException if the {@code value} is not in {@code supportedValues}
     */
    @NonNull
    public static String checkArgumentIsSupported(final String[] supportedValues,
                                                  final String value)
    {

        if (!contains(supportedValues, value))
        {
            throw new IllegalArgumentException(value + "is not supported "
                + Arrays.toString(supportedValues));
        }
        return value;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean contains(String[] values, String value)
    {
        if (values == null)
        {
            return false;
        }
        for (String s : values)
        {
            if (Objects.equals(value, s))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Ensures that all elements in the argument floating point array are within the inclusive range
     *
     * <p>While this can be used to range check against +/- infinity, note that all NaN numbers
     * will always be out of range.</p>
     *
     * @param value     a floating point array of values
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated floating point value
     * @throws IllegalArgumentException if any of the elements in {@code value} were out of range
     * @throws NullPointerException     if the {@code value} was {@code null}
     */
    public static float[] checkArrayElementsInRange(float[] value, float lower, float upper,
                                                    String valueName)
    {
        Objects.requireNonNull(value, valueName + " must not be null");

        for (int i = 0; i < value.length; ++i)
        {
            float v = value[i];

            if (Float.isNaN(v))
            {
                throw new IllegalArgumentException(valueName + "[" + i + "] must not be NaN");
            } else if (v < lower)
            {
                throw new IllegalArgumentException(
                    String.format("%s[%d] is out of range of [%f, %f] (too low)",
                        valueName, i, lower, upper));
            } else if (v > upper)
            {
                throw new IllegalArgumentException(
                    String.format("%s[%d] is out of range of [%f, %f] (too high)",
                        valueName, i, lower, upper));
            }
        }

        return value;
    }

    /**
     * Ensures that all elements in the argument integer array are within the inclusive range
     *
     * @param value     an integer array of values
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated integer array
     * @throws IllegalArgumentException if any of the elements in {@code value} were out of range
     * @throws NullPointerException     if the {@code value} was {@code null}
     */
    public static int[] checkArrayElementsInRange(int[] value, int lower, int upper,
                                                  String valueName)
    {
        Objects.requireNonNull(value, valueName + " must not be null");

        for (int i = 0; i < value.length; ++i)
        {
            int v = value[i];

            if (v < lower)
            {
                throw new IllegalArgumentException(
                    String.format("%s[%d] is out of range of [%d, %d] (too low)",
                        valueName, i, lower, upper));
            } else if (v > upper)
            {
                throw new IllegalArgumentException(
                    String.format("%s[%d] is out of range of [%d, %d] (too high)",
                        valueName, i, lower, upper));
            }
        }

        return value;
    }

    /**
     * Maps out-of-bounds values to a runtime exception.
     *
     * @param checkKind the kind of bounds check, whose name may correspond
     *                  to the name of one of the range check methods, checkIndex,
     *                  checkFromToIndex, checkFromIndexSize
     * @param args      the out-of-bounds arguments that failed the range check.
     *                  If the checkKind corresponds a the name of a range check method
     *                  then the bounds arguments are those that can be passed in order
     *                  to the method.
     * @param oobef     the exception formatter that when applied with a checkKind
     *                  and a list out-of-bounds arguments returns a runtime exception.
     *                  If {@code null} then, it is as if an exception formatter was
     *                  supplied that returns {@link IndexOutOfBoundsException} for any
     *                  given arguments.
     * @return the runtime exception
     */
    private static RuntimeException outOfBounds(
        BiFunction<String, List<Integer>, ? extends RuntimeException> oobef,
        String checkKind,
        Integer... args)
    {
        List<Integer> largs = List.of(args);
        RuntimeException e = oobef == null
            ? null : oobef.apply(checkKind, largs);
        return e == null
            ? new IndexOutOfBoundsException(outOfBoundsMessage(checkKind, largs)) : e;
    }

    private static RuntimeException outOfBoundsCheckIndex(
        BiFunction<String, List<Integer>, ? extends RuntimeException> oobe,
        int index, int length)
    {
        return outOfBounds(oobe, "checkIndex", index, length);
    }

    private static RuntimeException outOfBoundsCheckFromToIndex(
        BiFunction<String, List<Integer>, ? extends RuntimeException> oobe,
        int fromIndex, int toIndex, int length)
    {
        return outOfBounds(oobe, "checkFromToIndex", fromIndex, toIndex, length);
    }

    private static RuntimeException outOfBoundsCheckFromIndexSize(
        BiFunction<String, List<Integer>, ? extends RuntimeException> oobe,
        int fromIndex, int size, int length)
    {
        return outOfBounds(oobe, "checkFromIndexSize", fromIndex, size, length);
    }

    private static String outOfBoundsMessage(String checkKind, List<Integer> args)
    {
        if (checkKind == null && args == null)
        {
            return "Range check failed";
        } else if (checkKind == null)
        {
            return String.format("Range check failed: %s", args);
        } else if (args == null)
        {
            return String.format("Range check failed: %s", checkKind);
        }

        int argSize = 0;
        switch (checkKind)
        {
            case "checkIndex":
                argSize = 2;
                break;
            case "checkFromToIndex":
            case "checkFromIndexSize":
                argSize = 3;
                break;
            default:
        }

        // Switch to default if fewer or more arguments than required are supplied
        switch ((args.size() != argSize) ? "" : checkKind)
        {
            case "checkIndex":
                return String.format("Index %d out of bounds for length %d",
                    args.get(0), args.get(1));
            case "checkFromToIndex":
                return String.format("Range [%d, %d) out of bounds for length %d",
                    args.get(0), args.get(1), args.get(2));
            case "checkFromIndexSize":
                return String.format("Range [%d, %<d + %d) out of bounds for length %d",
                    args.get(0), args.get(1), args.get(2));
            default:
                return String.format("Range check failed: %s %s", checkKind, args);
        }
    }

    /**
     * Checks if the {@code index} is within the bounds of the range from
     * {@code 0} (inclusive) to {@code length} (exclusive).
     *
     * <p>The {@code index} is defined to be out of bounds if any of the
     * following inequalities is true:
     * <ul>
     *  <li>{@code index < 0}</li>
     *  <li>{@code index >= length}</li>
     *  <li>{@code length < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * <p>If the {@code index} is out of bounds, then a runtime exception is
     * thrown that is the result of applying the following arguments to the
     * exception formatter: the name of this method, {@code checkIndex};
     * and an unmodifiable list integers whose values are, in order, the
     * out-of-bounds arguments {@code index} and {@code length}.
     *
     * @param <X>    the type of runtime exception to throw if the arguments are
     *               out of bounds
     * @param index  the index
     * @param length the upper-bound (exclusive) of the range
     * @param oobef  the exception formatter that when applied with this
     *               method name and out-of-bounds arguments returns a runtime
     *               exception.  If {@code null} or returns {@code null} then, it is as
     *               if an exception formatter produced from an invocation of
     *               {@code outOfBoundsExceptionFormatter(IndexOutOfBounds::new)} is used
     *               instead (though it may be more efficient).
     *               Exceptions thrown by the formatter are relayed to the caller.
     * @return {@code index} if it is within bounds of the range
     * @throws X                         if the {@code index} is out of bounds and the exception
     *                                   formatter is non-{@code null}
     * @throws IndexOutOfBoundsException if the {@code index} is out of bounds
     *                                   and the exception formatter is {@code null}
     * @implNote This method is made intrinsic in optimizing compilers to guide them to
     * perform unsigned comparisons of the index and length when it is known the
     * length is a non-negative value (such as that of an array length or from
     * the upper bound of a loop)
     * @since 9
     */
    public static <X extends RuntimeException>
    int checkIndex(int index, int length,
                   BiFunction<String, List<Integer>, X> oobef)
    {
        if (index < 0 || index >= length)
            throw outOfBoundsCheckIndex(oobef, index, length);
        return index;
    }

    /**
     * Checks if the sub-range from {@code fromIndex} (inclusive) to
     * {@code toIndex} (exclusive) is within the bounds of range from {@code 0}
     * (inclusive) to {@code length} (exclusive).
     *
     * <p>The sub-range is defined to be out of bounds if any of the following
     * inequalities is true:
     * <ul>
     *  <li>{@code fromIndex < 0}</li>
     *  <li>{@code fromIndex > toIndex}</li>
     *  <li>{@code toIndex > length}</li>
     *  <li>{@code length < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * <p>If the sub-range is out of bounds, then a runtime exception is
     * thrown that is the result of applying the following arguments to the
     * exception formatter: the name of this method, {@code checkFromToIndex};
     * and an unmodifiable list integers whose values are, in order, the
     * out-of-bounds arguments {@code fromIndex}, {@code toIndex}, and {@code length}.
     *
     * @param <X>       the type of runtime exception to throw if the arguments are
     *                  out of bounds
     * @param fromIndex the lower-bound (inclusive) of the sub-range
     * @param toIndex   the upper-bound (exclusive) of the sub-range
     * @param length    the upper-bound (exclusive) the range
     * @param oobef     the exception formatter that when applied with this
     *                  method name and out-of-bounds arguments returns a runtime
     *                  exception.  If {@code null} or returns {@code null} then, it is as
     *                  if an exception formatter produced from an invocation of
     *                  {@code outOfBoundsExceptionFormatter(IndexOutOfBounds::new)} is used
     *                  instead (though it may be more efficient).
     *                  Exceptions thrown by the formatter are relayed to the caller.
     * @return {@code fromIndex} if the sub-range within bounds of the range
     * @throws X                         if the sub-range is out of bounds and the exception factory
     *                                   function is non-{@code null}
     * @throws IndexOutOfBoundsException if the sub-range is out of bounds and
     *                                   the exception factory function is {@code null}
     * @since 9
     */
    public static <X extends RuntimeException>
    int checkFromToIndex(int fromIndex, int toIndex, int length,
                         BiFunction<String, List<Integer>, X> oobef)
    {
        if (fromIndex < 0 || fromIndex > toIndex || toIndex > length)
            throw outOfBoundsCheckFromToIndex(oobef, fromIndex, toIndex, length);
        return fromIndex;
    }

    /**
     * Checks if the sub-range from {@code fromIndex} (inclusive) to
     * {@code fromIndex + size} (exclusive) is within the bounds of range from
     * {@code 0} (inclusive) to {@code length} (exclusive).
     *
     * <p>The sub-range is defined to be out of bounds if any of the following
     * inequalities is true:
     * <ul>
     *  <li>{@code fromIndex < 0}</li>
     *  <li>{@code size < 0}</li>
     *  <li>{@code fromIndex + size > length}, taking into account integer overflow</li>
     *  <li>{@code length < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * <p>If the sub-range is out of bounds, then a runtime exception is
     * thrown that is the result of applying the following arguments to the
     * exception formatter: the name of this method, {@code checkFromIndexSize};
     * and an unmodifiable list integers whose values are, in order, the
     * out-of-bounds arguments {@code fromIndex}, {@code size}, and
     * {@code length}.
     *
     * @param <X>       the type of runtime exception to throw if the arguments are
     *                  out of bounds
     * @param fromIndex the lower-bound (inclusive) of the sub-interval
     * @param size      the size of the sub-range
     * @param length    the upper-bound (exclusive) of the range
     * @param oobef     the exception formatter that when applied with this
     *                  method name and out-of-bounds arguments returns a runtime
     *                  exception.  If {@code null} or returns {@code null} then, it is as
     *                  if an exception formatter produced from an invocation of
     *                  {@code outOfBoundsExceptionFormatter(IndexOutOfBounds::new)} is used
     *                  instead (though it may be more efficient).
     *                  Exceptions thrown by the formatter are relayed to the caller.
     * @return {@code fromIndex} if the sub-range within bounds of the range
     * @throws X                         if the sub-range is out of bounds and the exception factory
     *                                   function is non-{@code null}
     * @throws IndexOutOfBoundsException if the sub-range is out of bounds and
     *                                   the exception factory function is {@code null}
     * @since 9
     */
    public static <X extends RuntimeException>
    int checkFromIndexSize(int fromIndex, int size, int length,
                           BiFunction<String, List<Integer>, X> oobef)
    {
        if ((length | fromIndex | size) < 0 || size > length - fromIndex)
            throw outOfBoundsCheckFromIndexSize(oobef, fromIndex, size, length);
        return fromIndex;
    }
}
