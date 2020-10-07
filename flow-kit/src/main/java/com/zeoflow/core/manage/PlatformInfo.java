package com.zeoflow.core.manage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.TimeZone;
import java.util.UUID;

import static com.zeoflow.initializer.ZeoFlowApp.getContext;

public class PlatformInfo
{

    private static String uniqueID = null;

    public PlatformInfo()
    {

    }

    private synchronized static String id()
    {
        if (uniqueID == null)
        {
            String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
            SharedPreferences sharedPrefs = getContext().getSharedPreferences(
                PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null)
            {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.apply();
            }
        }
        return uniqueID;
    }

    public synchronized static String getDeviceID()
    {
        return id();
    }

    public synchronized static String getDeviceModel()
    {
        return Build.MODEL;
    }

    public synchronized static String getDeviceManufacturer()
    {
        return Build.MANUFACTURER;
    }

    public synchronized static String getPackage()
    {
        return getContext().getPackageName();
    }

    public synchronized static int getAppVersion()
    {
        try
        {
            PackageInfo packageInfo = getContext().getPackageManager()
                .getPackageInfo(getContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public synchronized static String timeZone()
    {
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }


}
