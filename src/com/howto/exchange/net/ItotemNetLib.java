package com.howto.exchange.net;

import android.content.Context;

import com.howto.exchange.utils.SharedPreferencesUtil;

public class ItotemNetLib {
	private static ItotemNetLib mLib;
	public ItotemRequest mRequest;
	protected ItotemParse mParse;
	protected Context mContext;
	SharedPreferencesUtil spUtile;

	private ItotemNetLib(Context context) {
		mRequest = new ItotemRequest(context);
		mParse = new ItotemParse(context);
		mContext = context;
		spUtile = SharedPreferencesUtil.getinstance(mContext);
	}
	
	public synchronized static ItotemNetLib getInstance(Context context) {
		if (mLib == null) {
			mLib = new ItotemNetLib(context);
		}
		return mLib;
	}
	
	/*public BaseGsonBean<ArrayList<AuthorInfo>> getFollow(String user_id, String type) throws IOException, TimeoutException {
		String json = mRequest.getFollow(user_id,type);
		PublicUtils.log("getFollow:"+json);
		return parseJson(json, new TypeToken<BaseGsonBean<ArrayList<AuthorInfo>>>(){});
	}*/
}

