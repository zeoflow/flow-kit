package com.zeoflow.utils;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

import androidx.annotation.AnyRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.provider.FontsContractCompat;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

import static android.content.Context.ACCESSIBILITY_SERVICE;
import static android.content.Context.ACCOUNT_SERVICE;
import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.APPWIDGET_SERVICE;
import static android.content.Context.APP_OPS_SERVICE;
import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.BATTERY_SERVICE;
import static android.content.Context.BLUETOOTH_SERVICE;
import static android.content.Context.CAMERA_SERVICE;
import static android.content.Context.CAPTIONING_SERVICE;
import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.CONSUMER_IR_SERVICE;
import static android.content.Context.DEVICE_POLICY_SERVICE;
import static android.content.Context.DISPLAY_SERVICE;
import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.DROPBOX_SERVICE;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.INPUT_SERVICE;
import static android.content.Context.JOB_SCHEDULER_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.LAUNCHER_APPS_SERVICE;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MEDIA_PROJECTION_SERVICE;
import static android.content.Context.MEDIA_ROUTER_SERVICE;
import static android.content.Context.MEDIA_SESSION_SERVICE;
import static android.content.Context.NFC_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.NSD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.content.Context.PRINT_SERVICE;
import static android.content.Context.RESTRICTIONS_SERVICE;
import static android.content.Context.SEARCH_SERVICE;
import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.STORAGE_SERVICE;
import static android.content.Context.TELECOM_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.Context.TELEPHONY_SUBSCRIPTION_SERVICE;
import static android.content.Context.TEXT_SERVICES_MANAGER_SERVICE;
import static android.content.Context.TV_INPUT_SERVICE;
import static android.content.Context.UI_MODE_SERVICE;
import static android.content.Context.USAGE_STATS_SERVICE;
import static android.content.Context.USB_SERVICE;
import static android.content.Context.USER_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;
import static android.content.Context.WALLPAPER_SERVICE;
import static android.content.Context.WIFI_P2P_SERVICE;
import static android.content.Context.WIFI_SERVICE;
import static android.content.Context.WINDOW_SERVICE;
import static android.os.Build.VERSION.SDK_INT;
import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX;
import static com.zeoflow.initializer.ZeoFlowApp.getContext;

public class ContentCompat
{

    @AnyRes
    public static final int ID_NULL = 0;
    private static final Object sLock = new Object();
    private static TypedValue sTempValue;

    protected ContentCompat()
    {
        // Not publicly instantiable, but may be extended.
    }
    @Nullable
    public static File getDataDir()
    {
        if (Build.VERSION.SDK_INT >= 24)
        {
            return getContext().getDataDir();
        } else
        {
            final String dataDir = getContext().getApplicationInfo().dataDir;
            return dataDir != null ? new File(dataDir) : null;
        }
    }
    @NonNull
    public static File[] getObbDirs()
    {
        return getContext().getObbDirs();
    }
    @NonNull
    public static File[] getExternalFilesDirs(@Nullable String type)
    {
        return getContext().getExternalFilesDirs(type);
    }
    @NonNull
    public static File[] getExternalCacheDirs()
    {
        return getContext().getExternalCacheDirs();
    }
    @Nullable
    public static Drawable getDrawable(@DrawableRes int id)
    {
        return ContentCompat.getDrawable(getContext().getResources(), id, getContext().getTheme());
    }
    @ColorInt
    public static int getColor(@ColorRes int id)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            return getContext().getColor(id);
        } else
        {
            return getContext().getResources().getColor(id);
        }
    }
    public static int checkSelfPermission(@NonNull String permission)
    {
        return getContext().checkPermission(permission, android.os.Process.myPid(), Process.myUid());
    }
    @Nullable
    public static File getNoBackupFilesDir()
    {
        if (Build.VERSION.SDK_INT >= 21)
        {
            return getContext().getNoBackupFilesDir();
        } else
        {
            ApplicationInfo appInfo = getContext().getApplicationInfo();
            return createFilesDir(new File(appInfo.dataDir, "no_backup"));
        }
    }
    public static File getCodeCacheDir()
    {
        if (Build.VERSION.SDK_INT >= 21)
        {
            return getContext().getCodeCacheDir();
        } else
        {
            ApplicationInfo appInfo = getContext().getApplicationInfo();
            return createFilesDir(new File(appInfo.dataDir, "code_cache"));
        }
    }
    private synchronized static File createFilesDir(File file)
    {
        if (!file.exists())
        {
            if (!file.mkdirs())
            {
                if (file.exists())
                {
                    // spurious failure; probably racing with another process for this app
                    return file;
                }
                return null;
            }
        }
        return file;
    }
    @Nullable
    public static Context createDeviceProtectedStorageContext()
    {
        if (Build.VERSION.SDK_INT >= 24)
        {
            return getContext().createDeviceProtectedStorageContext();
        } else
        {
            return null;
        }
    }
    public static boolean isDeviceProtectedStorage()
    {
        if (Build.VERSION.SDK_INT >= 24)
        {
            return getContext().isDeviceProtectedStorage();
        } else
        {
            return false;
        }
    }
    public static void startForegroundService(@NonNull Intent intent)
    {
        if (Build.VERSION.SDK_INT >= 26)
        {
            getContext().startForegroundService(intent);
        } else
        {
            // Pre-O behavior.
            getContext().startService(intent);
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T getSystemService(@NonNull Class<T> serviceClass)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            return getContext().getSystemService(serviceClass);
        }

        String serviceName = getSystemServiceName(serviceClass);
        return serviceName != null ? (T) getContext().getSystemService(serviceName) : null;
    }

    @Nullable
    public static String getSystemServiceName(@NonNull Class<?> serviceClass)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            return getContext().getSystemServiceName(serviceClass);
        }
        return LegacyServiceMapHolder.SERVICES.get(serviceClass);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    public static Drawable getDrawable(@NonNull Resources res, @DrawableRes int id,
                                       @Nullable Resources.Theme theme) throws Resources.NotFoundException
    {
        if (SDK_INT >= 21)
        {
            return res.getDrawable(id, theme);
        } else
        {
            return res.getDrawable(id);
        }
    }
    @Nullable
    @SuppressWarnings("deprecation")
    public static Drawable getDrawableForDensity(@NonNull Resources res, @DrawableRes int id,
                                                 int density, @Nullable Resources.Theme theme) throws Resources.NotFoundException
    {
        if (SDK_INT >= 21)
        {
            return res.getDrawableForDensity(id, density, theme);
        } else
        {
            return res.getDrawableForDensity(id, density);
        }
    }
    @ColorInt
    @SuppressWarnings("deprecation")
    public static int getColor(@NonNull Resources res, @ColorRes int id, @Nullable Resources.Theme theme)
            throws Resources.NotFoundException
    {
        if (SDK_INT >= 23)
        {
            return res.getColor(id, theme);
        } else
        {
            return res.getColor(id);
        }
    }
    @NonNull
    @SuppressLint("UseCompatLoadingForColorStateLists")
    public static ColorStateList getColorStateList(@NonNull Resources res, @ColorRes int id,
                                                   @Nullable Resources.Theme theme) throws Resources.NotFoundException
    {
        if (SDK_INT >= 23)
        {
            return res.getColorStateList(id, theme);
        } else
        {
            return res.getColorStateList(id);
        }
    }
    public static float getFloat(@NonNull Resources res, @DimenRes int id)
    {
        // TODO call into platform on Q+

        TypedValue value = new TypedValue();
        res.getValue(id, value, true);
        if (value.type == TypedValue.TYPE_FLOAT)
        {
            return value.getFloat();
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(id)
                + " type #0x" + Integer.toHexString(value.type) + " is not valid");
    }

    private static class MainHandlerExecutor implements Executor
    {

        private final Handler mHandler;

        MainHandlerExecutor(@NonNull Handler handler)
        {
            mHandler = handler;
        }

        @Override
        public void execute(Runnable command)
        {
            if (!mHandler.post(command))
            {
                throw new RejectedExecutionException(mHandler + " is shutting down");
            }
        }

    }

    private static final class LegacyServiceMapHolder
    {

        static final HashMap<Class<?>, String> SERVICES = new HashMap<>();

        static
        {
            if (Build.VERSION.SDK_INT >= 22)
            {
                SERVICES.put(SubscriptionManager.class, TELEPHONY_SUBSCRIPTION_SERVICE);
                SERVICES.put(UsageStatsManager.class, USAGE_STATS_SERVICE);
            }
            if (Build.VERSION.SDK_INT >= 21)
            {
                SERVICES.put(AppWidgetManager.class, APPWIDGET_SERVICE);
                SERVICES.put(BatteryManager.class, BATTERY_SERVICE);
                SERVICES.put(CameraManager.class, CAMERA_SERVICE);
                SERVICES.put(JobScheduler.class, JOB_SCHEDULER_SERVICE);
                SERVICES.put(LauncherApps.class, LAUNCHER_APPS_SERVICE);
                SERVICES.put(MediaProjectionManager.class, MEDIA_PROJECTION_SERVICE);
                SERVICES.put(MediaSessionManager.class, MEDIA_SESSION_SERVICE);
                SERVICES.put(RestrictionsManager.class, RESTRICTIONS_SERVICE);
                SERVICES.put(TelecomManager.class, TELECOM_SERVICE);
                SERVICES.put(TvInputManager.class, TV_INPUT_SERVICE);
            }
            SERVICES.put(AppOpsManager.class, APP_OPS_SERVICE);
            SERVICES.put(CaptioningManager.class, CAPTIONING_SERVICE);
            SERVICES.put(ConsumerIrManager.class, CONSUMER_IR_SERVICE);
            SERVICES.put(PrintManager.class, PRINT_SERVICE);
            SERVICES.put(BluetoothManager.class, BLUETOOTH_SERVICE);
            SERVICES.put(DisplayManager.class, DISPLAY_SERVICE);
            SERVICES.put(UserManager.class, USER_SERVICE);
            SERVICES.put(InputManager.class, INPUT_SERVICE);
            SERVICES.put(MediaRouter.class, MEDIA_ROUTER_SERVICE);
            SERVICES.put(NsdManager.class, NSD_SERVICE);
            SERVICES.put(AccessibilityManager.class, ACCESSIBILITY_SERVICE);
            SERVICES.put(AccountManager.class, ACCOUNT_SERVICE);
            SERVICES.put(ActivityManager.class, ACTIVITY_SERVICE);
            SERVICES.put(AlarmManager.class, ALARM_SERVICE);
            SERVICES.put(AudioManager.class, AUDIO_SERVICE);
            SERVICES.put(ClipboardManager.class, CLIPBOARD_SERVICE);
            SERVICES.put(ConnectivityManager.class, CONNECTIVITY_SERVICE);
            SERVICES.put(DevicePolicyManager.class, DEVICE_POLICY_SERVICE);
            SERVICES.put(DownloadManager.class, DOWNLOAD_SERVICE);
            SERVICES.put(DropBoxManager.class, DROPBOX_SERVICE);
            SERVICES.put(InputMethodManager.class, INPUT_METHOD_SERVICE);
            SERVICES.put(KeyguardManager.class, KEYGUARD_SERVICE);
            SERVICES.put(LayoutInflater.class, LAYOUT_INFLATER_SERVICE);
            SERVICES.put(LocationManager.class, LOCATION_SERVICE);
            SERVICES.put(NfcManager.class, NFC_SERVICE);
            SERVICES.put(NotificationManager.class, NOTIFICATION_SERVICE);
            SERVICES.put(PowerManager.class, POWER_SERVICE);
            SERVICES.put(SearchManager.class, SEARCH_SERVICE);
            SERVICES.put(SensorManager.class, SENSOR_SERVICE);
            SERVICES.put(StorageManager.class, STORAGE_SERVICE);
            SERVICES.put(TelephonyManager.class, TELEPHONY_SERVICE);
            SERVICES.put(TextServicesManager.class, TEXT_SERVICES_MANAGER_SERVICE);
            SERVICES.put(UiModeManager.class, UI_MODE_SERVICE);
            SERVICES.put(UsbManager.class, USB_SERVICE);
            SERVICES.put(Vibrator.class, VIBRATOR_SERVICE);
            SERVICES.put(WallpaperManager.class, WALLPAPER_SERVICE);
            SERVICES.put(WifiP2pManager.class, WIFI_P2P_SERVICE);
            SERVICES.put(WifiManager.class, WIFI_SERVICE);
            SERVICES.put(WindowManager.class, WINDOW_SERVICE);
        }
    }

    public abstract static class FontCallback
    {

        /**
         * Called when an asynchronous font was finished loading.
         *
         * @param typeface The font that was loaded.
         */
        public abstract void onFontRetrieved(@NonNull Typeface typeface);

        /**
         * Called when an asynchronous font failed to load.
         *
         * @param reason The reason the font failed to load. One of
         *               {@link FontsContractCompat.FontRequestCallback.FontRequestFailReason#FAIL_REASON_PROVIDER_NOT_FOUND},
         *               {@link FontsContractCompat.FontRequestCallback.FontRequestFailReason#FAIL_REASON_WRONG_CERTIFICATES},
         *               {@link FontsContractCompat.FontRequestCallback.FontRequestFailReason#FAIL_REASON_FONT_LOAD_ERROR},
         *               {@link FontsContractCompat.FontRequestCallback.FontRequestFailReason#FAIL_REASON_SECURITY_VIOLATION},
         *               {@link FontsContractCompat.FontRequestCallback.FontRequestFailReason#FAIL_REASON_FONT_NOT_FOUND},
         *               {@link FontsContractCompat.FontRequestCallback.FontRequestFailReason#FAIL_REASON_FONT_UNAVAILABLE} or
         *               {@link FontsContractCompat.FontRequestCallback.FontRequestFailReason#FAIL_REASON_MALFORMED_QUERY}.
         */
        public abstract void onFontRetrievalFailed(@FontsContractCompat.FontRequestCallback.FontRequestFailReason int reason);

        /**
         * Call {@link #onFontRetrieved(Typeface)} on the handler given, or the Ui Thread if it is
         * null.
         *
         * @hide
         */
        @RestrictTo(LIBRARY_GROUP_PREFIX)
        public final void callbackSuccessAsync(final Typeface typeface, @Nullable Handler handler)
        {
            if (handler == null)
            {
                handler = new Handler(Looper.getMainLooper());
            }
            handler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    onFontRetrieved(typeface);
                }
            });
        }

        /**
         * Call {@link #onFontRetrievalFailed(int)} on the handler given, or the Ui Thread if it is
         * null.
         *
         * @hide
         */
        @RestrictTo(LIBRARY_GROUP_PREFIX)
        public final void callbackFailAsync(
                @FontsContractCompat.FontRequestCallback.FontRequestFailReason final int reason, @Nullable Handler handler)
        {
            if (handler == null)
            {
                handler = new Handler(Looper.getMainLooper());
            }
            handler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    onFontRetrievalFailed(reason);
                }
            });
        }

    }

    public static final class ThemeCompat
    {

        private ThemeCompat()
        {
        }

        /**
         * Rebases the theme against the parent Resource object's current configuration by
         * re-applying the styles passed to {@link Resources.Theme#applyStyle(int, boolean)}.
         * <p>
         * Compatibility behavior:
         * <ul>
         * <li>API 29 and above, this method matches platform behavior.
         * <li>API 23 through 28, this method attempts to match platform behavior by calling into
         *     hidden platform APIs, but is not guaranteed to succeed.
         * <li>API 22 and earlier, this method does nothing.
         * </ul>
         *
         * @param theme the theme to rebase
         */
        public static void rebase(@NonNull Resources.Theme theme)
        {
            if (SDK_INT >= 29)
            {
                ContentCompat.ThemeCompat.ImplApi29.rebase(theme);
            } else if (SDK_INT >= 23)
            {
                ContentCompat.ThemeCompat.ImplApi23.rebase(theme);
            }
        }

        @RequiresApi(29)
        static class ImplApi29
        {

            private ImplApi29()
            {
            }

            static void rebase(@NonNull Resources.Theme theme)
            {
                theme.rebase();
            }

        }

        @RequiresApi(23)
        static class ImplApi23
        {

            private static final Object sRebaseMethodLock = new Object();
            private static Method sRebaseMethod;
            private static boolean sRebaseMethodFetched;
            private ImplApi23()
            {
            }
            static void rebase(@NonNull Resources.Theme theme)
            {
                synchronized (sRebaseMethodLock)
                {
                    if (!sRebaseMethodFetched)
                    {
                        try
                        {
                            sRebaseMethod = Resources.Theme.class.getDeclaredMethod("rebase");
                            sRebaseMethod.setAccessible(true);
                        } catch (NoSuchMethodException e)
                        {
//                            Log.i(TAG, "Failed to retrieve rebase() method", e);
                        }
                        sRebaseMethodFetched = true;
                    }
                    if (sRebaseMethod != null)
                    {
                        try
                        {
                            sRebaseMethod.invoke(theme);
                        } catch (IllegalAccessException | InvocationTargetException e)
                        {
//                            Log.i(TAG, "Failed to invoke rebase() method via reflection", e);
                            sRebaseMethod = null;
                        }
                    }
                }
            }

        }

    }

}

