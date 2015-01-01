package com.howto.exchange.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class PhoneParam {
  public static String IMEI;
  public static String MACHINE_NAME;
  public static String MACHINE_VERSION;
  public static String OS_VERSION;

  public static void init(Context context) {
    IMEI = getIMEI(context);
    MACHINE_NAME = Build.BRAND;
    MACHINE_VERSION = Build.MODEL;
    OS_VERSION = Build.VERSION.RELEASE;
  }

  /**
   * 获取IMEI号
   * */
  private static String getIMEI(Context context) {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getDeviceId();
  }
}
