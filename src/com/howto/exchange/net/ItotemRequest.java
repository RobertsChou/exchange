package com.howto.exchange.net;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.howto.exchange.utils.SharedPreferencesUtil;

public class ItotemRequest {

	private BaseRequest baseRequest;
	ArrayList<NameValuePair> baseParams;
	private Context mContext;
	private SharedPreferencesUtil spUtile;

	public ItotemRequest(Context context) {
		baseRequest = new BaseRequest(context);
		baseParams = new ArrayList<NameValuePair>();
		mContext = context;
		spUtile = SharedPreferencesUtil.getinstance(context);
	}

	
	
	/**
	 * 
	 * getChildInfo(获取孩子信息)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param child_id
	 * @param limit
	 * @param page
	 * @return
	 * @throws IOException
	 * @throws TimeoutException 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	/*public String getChildWork(String child_id, String limit, String page) throws IOException, TimeoutException {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(); 
		params.add(new BasicNameValuePair("child_id", child_id));
		params.add(new BasicNameValuePair("limit", limit ));
		params.add(new BasicNameValuePair("page", page ));
		return baseRequest.postSignRequest(params, Urls.GetChildInfo);
	}*/
}

