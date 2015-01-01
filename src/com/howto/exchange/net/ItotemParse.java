package com.howto.exchange.net;

import android.content.Context;

public class ItotemParse {
	protected Context mContext;

	public ItotemParse(Context context) {
		mContext = context;
	}
	

	/*public PushInfo parseAndroidPush(String json) throws JSONException {
		if(TextUtils.isEmpty(json)) {
			return null;
		}
		return new PushInfo().parseJSON(new JSONObject(json), mContext);
	}*/
}
