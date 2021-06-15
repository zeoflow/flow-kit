package com.zeoflow.initializer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;

import androidx.collection.ArrayMap;

import com.zeoflow.annotation.GuardedBy;
import com.zeoflow.annotation.KeepForApi;
import com.zeoflow.annotation.NonNull;
import com.zeoflow.annotation.RequiresApi;
import com.zeoflow.annotation.SuppressLint;
import com.zeoflow.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.zeoflow.os.UserManagerCompat.isUserUnlocked;
import static com.zeoflow.core.utils.Preconditions.checkState;
import static com.zeoflow.core.utils.Preconditions.requireNonNull;

public class ZeoFlowApp
{

    public static final String TAG = ZeoFlowApp.class
        .getSimpleName();
    @GuardedBy("LOCK")
    static final Map<String, ZeoFlowApp> INSTANCES = new ArrayMap<>();
    private static final @NonNull
    String DEFAULT_APP_NAME = "[DEFAULT]";
    private static final Object LOCK = new Object();
    private final static AtomicBoolean deleted = new AtomicBoolean();
    private final Context applicationContext;
    private final String name;
    private final ZeoFlowOptions options;

    @SuppressLint("RestrictedApi")
    protected ZeoFlowApp(Context applicationContext, String name, ZeoFlowOptions options)
    {
        this.applicationContext = requireNonNull(applicationContext);
        this.options = requireNonNull(options);
        this.name = null;
    }

    @SuppressLint("RestrictedApi")
    protected ZeoFlowApp(Context applicationContext, String name)
    {
        this.applicationContext = requireNonNull(applicationContext);
        this.options = null;
        this.name = null;
    }

    @NonNull
    public static List<ZeoFlowApp> getApps(@NonNull Context context)
    {
        synchronized (LOCK)
        {
            return new ArrayList<>(INSTANCES.values());
        }
    }

    @Deprecated
    @NonNull
    public static ZeoFlowApp getInstance()
    {
        synchronized (LOCK)
        {
            ZeoFlowApp defaultApp = INSTANCES.get(DEFAULT_APP_NAME);
            if (defaultApp == null)
            {
                throw new IllegalStateException(
                    "Default ZeoFlowApp is not initialized in this "
                        + "process "
                        + ". Make sure to call "
                        + "ZeoFlowApp.initializeApp(Context) first.");
            }
            return defaultApp;
        }
    }

    @Deprecated
    @NonNull
    public static Context getContext()
    {
        synchronized (LOCK)
        {
            ZeoFlowApp defaultApp = INSTANCES.get(DEFAULT_APP_NAME);
            if (defaultApp == null)
            {
                throw new IllegalStateException(
                    "Default ZeoFlowApp is not initialized in this "
                        + "process "
                        + ". Make sure to call "
                        + "ZeoFlowApp.initializeApp(Context) first.");
            }
            checkNotDeleted();
            return defaultApp.applicationContext;
        }
    }

    @Deprecated
    @NonNull
    public static ZeoFlowApp getInstance(@NonNull String name)
    {
        synchronized (LOCK)
        {
            ZeoFlowApp zeoflowApp = INSTANCES.get(normalize(name));
            if (zeoflowApp != null)
            {
                return zeoflowApp;
            }

            List<String> availableAppNames = getAllAppNames();
            String availableAppNamesMessage;
            if (availableAppNames.isEmpty())
            {
                availableAppNamesMessage = "";
            } else
            {
                availableAppNamesMessage =
                    "Available app names: " + TextUtils.join(", ", availableAppNames);
            }
            String errorMessage =
                String.format(
                    "ZeoFlowApp with name %s doesn't exist. %s", name, availableAppNamesMessage);
            throw new IllegalStateException(errorMessage);
        }
    }

    @Deprecated
    public static ZeoFlowApp initializeApp(@NonNull Context context)
    {
        String normalizedName = normalize(DEFAULT_APP_NAME);
        Context applicationContext;
        if (context.getApplicationContext() == null)
        {
            applicationContext = context;
        } else
        {
            applicationContext = context.getApplicationContext();
        }

        ZeoFlowApp zeoflowApp;
        synchronized (LOCK)
        {
            checkState(!INSTANCES.containsKey(normalizedName), "ZeoFlowApp name " + normalizedName + " already exists!");
            requireNonNull(applicationContext, "Application context cannot be null.");
            zeoflowApp = new ZeoFlowApp(applicationContext, normalizedName);
            INSTANCES.put(normalizedName, zeoflowApp);
        }
        INSTANCES.put(normalizedName, zeoflowApp);
        return zeoflowApp;
    }

    @Deprecated
    @NonNull
    public static ZeoFlowApp initializeApp(@NonNull Context context, @NonNull ZeoFlowOptions options)
    {
        return initializeApp(context, options, "[DEFAULT]");
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    public static ZeoFlowApp initializeApp(@NonNull Context context, @NonNull ZeoFlowOptions options, @NonNull String name)
    {
        String normalizedName = normalize(name);
        Context applicationContext;
        if (context.getApplicationContext() == null)
        {
            applicationContext = context;
        } else
        {
            applicationContext = context.getApplicationContext();
        }

        ZeoFlowApp zeoflowApp;
        synchronized (LOCK)
        {
            checkState(!INSTANCES.containsKey(normalizedName), "ZeoFlowApp name " + normalizedName + " already exists!");
            requireNonNull(applicationContext, "Application context cannot be null.");
            zeoflowApp = new ZeoFlowApp(applicationContext, normalizedName, options);
            INSTANCES.put(normalizedName, zeoflowApp);
        }

        zeoflowApp.initializeAllApis();
        return zeoflowApp;
    }

    @SuppressLint("RestrictedApi")
    private static void checkNotDeleted()
    {
        checkState(!deleted.get(), "ZeoFlowApp was deleted");
    }

    @VisibleForTesting
    public static void clearInstancesForTest()
    {
        // TODO: also delete, once functionality is implemented.
        synchronized (LOCK)
        {
            INSTANCES.clear();
        }
    }

    private static List<String> getAllAppNames()
    {
        List<String> allAppNames = new ArrayList<>();
        synchronized (LOCK)
        {
            for (ZeoFlowApp app : INSTANCES.values())
            {
                allAppNames.add(app.getName());
            }
        }
        Collections.sort(allAppNames);
        return allAppNames;
    }

    private static String normalize(@NonNull String name)
    {
        return name.trim();
    }

    @NonNull
    public Context getApplicationContext()
    {
        checkNotDeleted();
        return applicationContext;
    }

    @NonNull
    public String getName()
    {
        checkNotDeleted();
        return name;
    }

    @NonNull
    public ZeoFlowOptions getOptions()
    {
        checkNotDeleted();
        return this.options;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof ZeoFlowApp))
        {
            return false;
        }
        return name.equals(((ZeoFlowApp) o).getName());
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    public void delete()
    {
        boolean valueChanged = deleted.compareAndSet(false /* expected */, true);
        if (!valueChanged)
        {
            return;
        }

        synchronized (LOCK)
        {
            INSTANCES.remove(this.name);
        }
    }

    @KeepForApi
    @VisibleForTesting
    public boolean isDefaultApp()
    {
        return DEFAULT_APP_NAME.equals(getName());
    }

    private void initializeAllApis()
    {
        boolean inDirectBoot = !isUserUnlocked(applicationContext);
        if (inDirectBoot)
        {
            // Ensure that all APIs are initialized once the user unlocks the phone.
            UserUnlockReceiver.ensureReceiverRegistered(applicationContext);
        }
    }

    private static class UserUnlockReceiver extends BroadcastReceiver
    {

        private static AtomicReference<UserUnlockReceiver> INSTANCE = new AtomicReference<>();
        private final Context applicationContext;

        public UserUnlockReceiver(Context applicationContext)
        {
            this.applicationContext = applicationContext;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private static void ensureReceiverRegistered(Context applicationContext)
        {
            if (INSTANCE.get() == null)
            {
                UserUnlockReceiver receiver = new UserUnlockReceiver(applicationContext);
                if (INSTANCE.compareAndSet(null /* expected */, receiver))
                {
                    IntentFilter intentFilter = new IntentFilter(Intent.ACTION_USER_UNLOCKED);
                    applicationContext.registerReceiver(receiver, intentFilter);
                }
            }
        }

        @Override
        public void onReceive(Context context, Intent intent)
        {
            // API initialization is idempotent.
            synchronized (LOCK)
            {
                for (ZeoFlowApp app : INSTANCES.values())
                {
                    app.initializeAllApis();
                }
            }
            unregister();
        }

        public void unregister()
        {
            applicationContext.unregisterReceiver(this);
        }
    }

}
