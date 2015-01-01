package com.howto.exchange.reciever;


import org.json.JSONObject;

import com.howto.exchange.IApplication;
import com.howto.exchange.utils.BroadcastConstants;
import com.howto.exchange.utils.PublicUtils;
import com.howto.exchange.utils.SharedPreferencesUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "MyReceiver";
	private String myValue;
	private Intent mIntent;
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Intent mIntent = null;
		SharedPreferences spNew = context.getSharedPreferences("newMsg", Context.MODE_PRIVATE);
		Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "接收Registration Id : " + regId);
			//send the Registration Id to your server...
		}else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())){
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "接收UnRegistration Id : " + regId);
			//send the UnRegistration Id to your server...
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			context.sendBroadcast(new Intent(BroadcastConstants.BROADCAST_NEWMSG));
			context.sendBroadcast(new Intent(BroadcastConstants.BROADCAST_NEWMSG_SYSTEM_VIEW));
		
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			SharedPreferencesUtil.getinstance(context).saveNews(true);
			Log.d(TAG, "接收到推送下来的通知");
			
			
			//第一
			PreOpenNotificationType(context, bundle);		
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "接收到推送下来的通知的ID: " + notifactionId);
			myValue = SharedPreferencesUtil.getinstance(context).getOpenContent();
			if(myValue.equals("2")){
				spNew.edit().putBoolean("hasNew", true).commit();
			}		
			context.sendBroadcast(new Intent(BroadcastConstants.BROADCAST_NEWMSG));
			context.sendBroadcast(new Intent(BroadcastConstants.BROADCAST_NEWMSG_SYSTEM_VIEW));
			
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "用户点击打开了通知");
			myValue = SharedPreferencesUtil.getinstance(context).getOpenContent();
			
			

		} else {
			Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
	}

	private void PreOpenNotificationType(Context context, Bundle bundle) {
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		myValue = ""; 
		try {
			JSONObject extrasJson = new JSONObject(extras);
			myValue = extrasJson.optString("type");
			SharedPreferencesUtil.getinstance(context).saveOpenContent(myValue);
			if(myValue.equals("3")){
			LocalBroadcastManager.getInstance(context).sendBroadcast(
					new Intent("Alarm_Count"));
			}
		} catch (Exception e) {
			Log.w(TAG, "Unexpected: extras is not a valid json", e);
			return ;
		}

		return ;
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	//朋友分享   type = 1;   系统通知; type = 2;
	private void openNotification(String myValue, Context context, Bundle bundle){
		/*	//跳转位置
		PublicUtils.log("myValue:"+myValue);
		if(myValue.equals("1")){
			Intent mIntent = new Intent(context, MainActivity.class);
			IApplication.curTab = 3;
			mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(mIntent);
		}
		else if(myValue.equals("2")){

			IApplication.getInstance().childChanged = true;
			Intent mIntent = new Intent(context, SliderSystem.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(mIntent);
		}
		else if(myValue.equals("3")){
			Intent mIntent = new Intent(context, MainActivity.class);
			IApplication.curTab = 1;
			mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(mIntent);
		}*/
	}

}
