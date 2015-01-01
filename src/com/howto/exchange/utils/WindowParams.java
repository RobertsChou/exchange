package com.howto.exchange.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class WindowParams {
  public static float dencity;
  public static int with;
  public static int height;
  private static WindowParams instance;
  
  public static void init(Activity cotext) {
    if(instance != null) {
      return ;
    }
    DisplayMetrics dm = new DisplayMetrics();
    cotext.getWindowManager().getDefaultDisplay().getMetrics(dm);
    dencity = dm.density;
    with = dm.widthPixels;
    height = dm.heightPixels;
    instance = new WindowParams();
  }
}
