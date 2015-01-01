package com.howto.exchange.utils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.howto.exchange.BuildConfig;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.TextView;


public class PublicUtils {
	private   Context myContext;

	public PublicUtils(Context context){
		myContext = context;
	}
	/***
	 * 给输入汉字的text加粗
	 */
	public static void blodChineseText(TextView textView){
		TextPaint tp = textView.getPaint(); 
		tp.setFakeBoldText(true); 
	}

	/***
	 * 
	 * @param pic
	 * @return
	 */
	public static String[] operationPicformget2(String pic) {
		if (!TextUtils.isEmpty(pic)) {
			String[] split = pic.split("[|]");
			if (split != null && split.length > 0) {
				return split;
			}
		}
		return null;
	}

	//判断是否为url
	public static boolean isUrl(String url){
		boolean isUrl = false;
		if(URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)){
			isUrl = true;
		}
		return isUrl;
	}

	/**
	 * @return
	 */
	public static Bitmap obtainPictureBitmap(Context myContext,int position) {
		try {
			InputStream inputStream = myContext.getResources().openRawResource(
					position);
			BitmapFactory.Options opt = new BitmapFactory.Options();  
			opt.inPreferredConfig = Bitmap.Config.RGB_565;   
			opt.inPurgeable = true;  
			opt.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, opt);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDeviceSystemVersion() {
		return System.getProperty("os.version") + Build.VERSION.INCREMENTAL;
	}


	public static  String getSystemType() {
		return android.os.Build.VERSION.SDK;//android.os.Build.VERSION.SDK //Build.MODEL + Build.PRODUCT
	}

	/***
	 */
	public static void setConfigChanges(Context context){
		String systemType = getSystemType();
		int systemTypeInt = Integer.parseInt(systemType);
		if(systemTypeInt > 13){

		}
	}

	/***
	 * 判断当前网络是否连接
	 * @param con
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			return false;
		}
		if (netinfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前网络类型
	 * @param context
	 * @return
	 */
	public static boolean isWifiOrData(Context context){

		ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()//获取应用上下文
		.getSystemService(Context.CONNECTIVITY_SERVICE);//获取系统的连接服务
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();//获取网络的连接情况
		if(activeNetInfo.getType()==ConnectivityManager.TYPE_WIFI){
			return true;
			//判断WIFI网
		}else if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
			//判断3G网
			return false;
		}
		return false;
	}

	/**
	 * 打开wifi网络
	 * @param context
	 * @return
	 */
	public static void openWifi(Context context ,boolean enabled){
		WifiManager wm = (WifiManager) context  
		                      .getSystemService(Context.WIFI_SERVICE);  
		wm.setWifiEnabled(enabled);  
	}

	// 获取应用程序的版本号
	public static String getAppVersion(Context context) {
		String appVersionName;
		try {
			appVersionName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
			return appVersionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void log(String tag, String message) {
		Log.e(tag, message);
	}

	public static void log(String message) {
		if(BuildConfig.DEBUG){
			Log.e("COOL", message);
		}
	}

	public static boolean isEmpty(String str) {
		return null == str || "".equals(str);
	}


	public String setWebView(String html) {
		if(html == null) {
			return "";
		}
		String str = html.replaceAll("BACKGROUND\\s*:\\s*rgb\\([^\\)]*\\)", "BACKGROUND : rgb(242,241,239)");
		if(!PublicUtils.isEmpty(str)) {
			html = str;
		}
		html = "<!DOCTYPE HTML><html><head><meta http-equiv=\"ConteOnt-Type\" content=\"text/html; charset=utf-8\"><meta content=\"width=device-width; initial-scale=1; minimum-scale=1.0; maximum-scale=2.0\" name=\"viewport\" /></head><body color='#414141' bgcolor='#F2F1EF'>"+html + "</body></html>";
		PublicUtils.log("TEST", html);
		return html;
	}

	public static String formatTime(long t) {
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = dateformat1.format(new Date(t));
		return str;
	}
	public static void bold(TextView tv) {
		TextPaint tp = tv.getPaint();
		tp.setFakeBoldText(true);
	}
}
