package com.howto.exchange.reciever;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.howto.exchange.utils.PublicUtils;

import android.app.Activity;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

public class SMSContent extends ContentObserver {

	public static final String SMS_URI_INBOX = "content://sms/inbox";
	private Activity activity = null;
	private String smsContent = "";
	private EditText verifyText = null;
	public SMSContent(Activity activity, Handler handler, EditText verifyText) {
		super(handler);
		this.activity = activity;
		this.verifyText = verifyText;
	}
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Cursor cursor = null;// 光标
		// 读取收件箱中指定号码的短信
		cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX), 
				new String[] { "_id", "address", "body", "read" }, "address=? and read=?",
				new String[] { "106902722511824236", "0" }, "date desc");

		if (cursor != null) {// 如果短信为未读模式
			ContentValues values = new ContentValues();
			values.put("read", "1"); // 修改短信为已读模式
			
			cursor.moveToFirst();
		
			if (cursor.moveToFirst()) {
				String smsbody = cursor.getString(cursor.getColumnIndex("body"));
				PublicUtils.log("smsbody=======================" + smsbody);
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(smsbody.toString());
				smsContent = m.replaceAll("").trim().toString();
				smsContent = smsContent.replace("01069614031", "");
				verifyText.setText(smsContent.trim());
			}
		}
	}
}
