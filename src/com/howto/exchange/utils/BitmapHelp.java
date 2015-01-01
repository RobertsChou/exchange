package com.howto.exchange.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.Settings.Global;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapGlobalConfig;
//import com.lidroid.xutils.cache.MD5FileNameGenerator;
//import com.lidroid.xutils.cache.MD5FileNameGenerator;

/**
 * Author: wyouflf
 * Date: 13-11-12
 * Time: 上午10:24
 */
public class BitmapHelp {
    private BitmapHelp() {
    }

    private static BitmapUtils bitmapUtils;

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     *
     * @param appContext application context
     * @return
     */
    public static synchronized BitmapUtils getBitmapUtils(Context appContext) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(appContext);
            bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
            bitmapUtils.configDefaultAutoRotation(true);
            bitmapUtils.configMemoryCacheEnabled(true);
            bitmapUtils.configDiskCacheEnabled(true);
            //bitmapUtils.configDiskCacheFileNameGenerator(new MD5FileNameGenerator());
            //bitmapUtils.configDefaultImageLoadAnimation(animation);
            //bitmapUtils.configDefaultLoadingImage(appContext.getResources().getDrawable(R.drawable.empty));
            bitmapUtils.configGlobalConfig(new BitmapGlobalConfig(appContext, appContext.getCacheDir().getAbsolutePath()));

        }
      
        return bitmapUtils;
    }
    public static synchronized BitmapUtils getBitmapUtilsWithDefaultIcon(Context appContext,int id) {
    	if (bitmapUtils == null) {
    		bitmapUtils = new BitmapUtils(appContext);
    		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
    		bitmapUtils.configDefaultAutoRotation(true);
    		bitmapUtils.configMemoryCacheEnabled(true);
    		bitmapUtils.configDiskCacheEnabled(true);
    		//bitmapUtils.configDiskCacheFileNameGenerator(new MD5FileNameGenerator());
    		//bitmapUtils.configDefaultLoadingImage(appContext.getResources().getDrawable(R.drawable.empty));
    		bitmapUtils.configGlobalConfig(new BitmapGlobalConfig(appContext, appContext.getCacheDir().getAbsolutePath()));
    	}
    	
    	return bitmapUtils;
    }
}
