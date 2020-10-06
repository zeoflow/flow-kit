package com.zeoflow.crash.reporter.utils;

import android.os.Build;

import static com.zeoflow.core.manage.PlatformInfo.getAppVersion;
import static com.zeoflow.core.manage.PlatformInfo.getDeviceID;
import static com.zeoflow.core.manage.PlatformInfo.timeZone;


public class AppUtils
{

    public static String mdDeviceDetails()
    {
        return "### Device Information\n\n"
            + "##### DEVICE.ID\n\n`" + getDeviceID() + "`\n\n"
            + "##### APP.VERSION\n\n`" + getAppVersion() + "`\n\n"
            + "##### TIMEZONE\n\n`" + timeZone() + "`\n\n"
            + "##### VERSION.RELEASE\n\n`" + Build.VERSION.RELEASE + "`\n\n"
            + "##### VERSION.INCREMENTAL\n\n`" + Build.VERSION.INCREMENTAL + "`\n\n"
            + "##### VERSION.SDK.NUMBER\n\n`" + Build.VERSION.SDK_INT + "`\n\n"
            + "##### BOARD\n\n`" + Build.BOARD + "`\n\n"
            + "##### BOOTLOADER\n\n`" + Build.BOOTLOADER + "`\n\n"
            + "##### BRAND\n\n`" + Build.BRAND + "`\n\n"
            + "##### MANUFACTURER\n\n`" + Build.MANUFACTURER + "`\n\n"
            + "##### MODEL\n\n`" + Build.MODEL + "`\n\n"
            + "##### PRODUCT\n\n`" + Build.PRODUCT + "`\n\n"
            + "##### TIME\n\n`" + Build.TIME + "`\n\n";
    }

    public static String getDeviceDetails()
    {

        return "Device Information\n"
            + "\nDEVICE.ID : " + getDeviceID()
            + "\nAPP.VERSION : " + getAppVersion()
            + "\nTIMEZONE : " + timeZone()
            + "\nVERSION.RELEASE : " + Build.VERSION.RELEASE
            + "\nVERSION.INCREMENTAL : " + Build.VERSION.INCREMENTAL
            + "\nVERSION.SDK.NUMBER : " + Build.VERSION.SDK_INT
            + "\nBOARD : " + Build.BOARD
            + "\nBOOTLOADER : " + Build.BOOTLOADER
            + "\nBRAND : " + Build.BRAND
            + "\nMANUFACTURER : " + Build.MANUFACTURER
            + "\nMODEL : " + Build.MODEL
            + "\nPRODUCT : " + Build.PRODUCT;
    }

}
