package com.howto.exchange.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.howto.exchange.utils.PublicUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class BaseRequest {
	private static final int SOCKET_TIME_OUT = 10000;
	private String appSign = "";
	private int connectTimeout = 10000;
	private long cacheTime = 1000 * 60 * 2;
	private boolean isCache;
	private Context context;
	public boolean isNetPriority = true; // 网络优先原则

	public BaseRequest(Context context) {
		this.context = context;
	}

	public void getHttpClient() {

	}

	public String getSignRequest(List<NameValuePair> params, String url) throws HttpException, IOException {
		return getSignRequest(params, url, false);
	}

	/**
	 * 多参数的get请求
	 * 
	 * @param params
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getSignRequest(List<NameValuePair> params, String url, boolean cache) throws HttpException, IOException {
		this.isCache = cache;
		String strURL;
		StringBuffer sb = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (NameValuePair nvp : params) {
				sb.append(nvp.getName()).append('=').append(URLEncoder.encode(nvp.getValue(), "utf-8")).append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		String paramsStr = sb.toString();
		if (paramsStr != null && !paramsStr.equals("")) {
			strURL = url + "?" + paramsStr;
		} else {
			strURL = url;
		}

		return getConnRequest("", strURL);
	}

	/***
	 * 网络处理中的post签名请求。
	 * 
	 * @param params
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public synchronized String postSignRequest(List<NameValuePair> params, String url) throws IOException, TimeoutException {
		return postSignRequest(params, null, url);
	}

	/***
	 * 网络处理中的post签名请求。
	 * 
	 * @param params
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public synchronized String postSignRequest(List<NameValuePair> params, String url, boolean cache) throws IOException, TimeoutException {
		this.isCache = cache;
		return postSignRequest(params, null, url);
	}

	/***
	 * 网络处理中的post签名请求。
	 * 
	 * @param params 针对签名参数
	 * @param params_rr 针对不签名参数
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public String postSignRequest(List<NameValuePair> params, List<NameValuePair> params_rr, String url) throws IOException, TimeoutException {
		if (params_rr != null) {
			params.addAll(params_rr);
		}
		return postConnRequest(postConnRequest(params), url);
	}

	/**
	 * 通过HttpURLConnection进行get请求
	 * 
	 * @param params 想服务器提交的参数(编码问题自行解决)
	 * @param url 服务器地址
	 * @return 服务器返回的输入流
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws HttpException
	 */

	public String getConnRequest(String params, String url) throws IOException {
		String strURL = (TextUtils.isEmpty(params)) ? url : (url + "?" + params);
		PublicUtils.log(strURL);
		if (isCache) {
			String json = getDataFromLocal(strURL);
			if (json != null) {
				return json;
			}
		}
		HttpURLConnection connect = null;
		try {
			connect = (HttpURLConnection) new URL(strURL).openConnection();
			connect.setRequestMethod("GET");
			connect.setConnectTimeout(connectTimeout);
			connect.setReadTimeout(SOCKET_TIME_OUT);
			connect.connect();
			int code = connect.getResponseCode();
			if (code == 200) {
				String str = inputToString(connect.getInputStream(), "utf-8");
				connect.disconnect();
				if (isCache) {
					saveToLocal(strURL, str);
				}
				return str;
			} else {
				throw new IOException("Error Response:" + code);
			}
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
				throw new SocketTimeoutException("网络连接超时或找不到DNS服务器");
			} else {
				throw e;
			}
		} finally {
			if (connect != null) {
				try {
					connect.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void saveToLocal(String url, String str) {
		try {
			Md5FileNameGenerator md = new Md5FileNameGenerator();
			File cachePath = StorageUtils.getCacheDirectory(context);
			File file = new File(cachePath, md.generate(url));
			if (!file.exists()) {
				file.createNewFile();
			}
			ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
			IoUtils.copyStream(in, new FileOutputStream(file));
			file.setLastModified(System.currentTimeMillis());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getDataFromLocal(String strURL) {
		if (isNetPriority && isNetAvalible(context)) {
			return null;
		}
		Md5FileNameGenerator md = new Md5FileNameGenerator();
		File cachePath = StorageUtils.getCacheDirectory(context);
		File file = new File(cachePath, md.generate(strURL));
		if (file.exists() &&(isNetPriority || (System.currentTimeMillis() - file.lastModified() < cacheTime))) {
			try {
				return inputToString(new FileInputStream(file), "utf-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 通过HttpURLConnection进行post请求
	 * 
	 * @param param 要提交的字符参数(编码问题自行解决)
	 * @param url 服务器地址
	 * @return 服务器返回的输入流
	 * @throws SocketTimeoutExceptionn 服务器连接超时
	 * @throws IOException 网络连接异常
	 */
	public String postConnRequest(String param, String url) throws IOException {

		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		if (TextUtils.isEmpty(param)) throw new IOException("Error : 传入参数不能为空");
		PublicUtils.log("POST=>"+url+"/"+param);
		HttpURLConnection connect = null;
		if (isCache) {
			String json = getDataFromLocal(url + param);
			if (json != null) {
				return json;
			}
		}
		try {
			connect = (HttpURLConnection) new URL(url).openConnection();
			connect.setConnectTimeout(connectTimeout);
			connect.setReadTimeout(connectTimeout);
			connect.setDoInput(true);
			connect.setDoOutput(true);
			byte[] formData = param.getBytes();
			connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connect.setRequestProperty("Content-Length", formData.length + "");
			connect.connect();
			OutputStream output = connect.getOutputStream();
			output.write(formData);
			output.flush();
			output.close();

			int code = connect.getResponseCode();
			if (code == 200) {
				String str = inputToString(connect.getInputStream(), "utf-8");
				connect.disconnect();
				if (isCache) {
					saveToLocal(url + param, str);
				}
				return str;
			} else {
				throw new IOException("服务器返回错误：" + code);
			}
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException) {
				throw new SocketTimeoutException("网络连接超时");
			} else {
				throw e;
			}

		} finally {
			if (connect != null) {
				try {
					connect.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 通过HttpURLConnection进行post请求
	 * 
	 * @param params 一个存放NameValuePair的List集合
	 * @param url 服务器地址
	 * @return 服务器返回流
	 * @throws IOException
	 */
	public String postConnRequest(List<NameValuePair> params) throws IOException {
		if (params == null || params.size() == 0) throw new IOException("Error : 传入参数不能为空");

		StringBuffer sb = new StringBuffer();
		for (NameValuePair nvp : params) {
			if (PublicUtils.isEmpty(nvp.getValue())) {
				continue;
			}
			String value = URLEncoder.encode(nvp.getValue(), HTTP.UTF_8).replaceAll("\\+", "%20");
			sb.append(nvp.getName()).append('=').append(value).append('&');
		}
		sb.deleteCharAt(sb.length() - 1);
		return new String(sb);
	}

	/**
	 * 将服务器返回的流转化成字符串
	 * 
	 * @param inputStream输入流
	 * @param encoding 字符编码类型,如果encoding传入null，则默认使用utf-8编码。
	 * @return 字符串
	 * @throws IOException
	 */
	public String inputToString(InputStream inputStream, String encoding) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		inputStream.close();
		bos.close();
		if (TextUtils.isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		return new String(bos.toByteArray(), encoding);
	}

	public interface OnUploadListener {
		/**
		 * 
		 * @param bytes 上传的总大小
		 * @param percent 上传的文件总大小的百分比
		 */
		public void onUploading(long bytes, float percent);
	}

	public String uploadFile(String url, String filePath, ArrayList<NameValuePair> params, OnUploadListener listener, String fileName) throws Exception {
		return uploadFile(url, filePath, params, listener, fileName, null);
	}

	public String uploadFile(String url, String filePath, ArrayList<NameValuePair> params, OnUploadListener listener, String fileName, String fileKey) throws Exception {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		
		conn.setReadTimeout(30 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		if (params != null) {
			int count = params.size();
			for (int i = 0; i < count; i++) {
				NameValuePair entry = params.get(i);
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				sb.append("Content-Disposition: form-data; name=\"" + entry.getName() + "\"" + LINEND);
				sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
				sb.append("Content-Transfer-Encoding: utf-8" + LINEND);
				sb.append(LINEND);
				sb.append(entry.getValue());
				sb.append(LINEND);
			}
		}
		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		InputStream in = null;
		File file = new File(filePath);
		// 发送文件数据
		StringBuilder sb1 = new StringBuilder();
		sb1.append(PREFIX);
		sb1.append(BOUNDARY);
		sb1.append(LINEND);
		if (fileKey == null) {
			fileKey = "\"upload\"";
		}
		sb1.append("Content-Disposition: form-data; name=" + fileKey + "; filename=\"" + fileName + "\"" + LINEND);
		PublicUtils.log(fileName);
		sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
		sb1.append(LINEND);
		outStream.write(sb1.toString().getBytes());

		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			PublicUtils.log("len" + len);
			if (listener != null) {
				listener.onUploading(len, 0);
			}
			outStream.write(buffer, 0, len);
		}

		is.close();
		outStream.write(LINEND.getBytes());

		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		outStream.close();
		// 得到响应码
		int res = conn.getResponseCode();
		if (res == 200) {
			String str = inputToString(conn.getInputStream(), "utf-8");
			conn.disconnect();
			if (isCache) {
				saveToLocal(url + params, str);
			}
			/*return str;*/
			/*in = conn.getInputStream();
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
			PublicUtils.log(JSONTokener(sb2.toString()));
			in.close();
			conn.disconnect();*/
			PublicUtils.log(JSONTokener(str.toString()));
			return JSONTokener(str.toString());
		} else {
			conn.disconnect();
			throw new IOException("服务器返回错误：" + res);
		}
	}

	public static boolean isNetAvalible(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isAvailable() && info.isConnected()) {
			return true;
		}
		return false;
	}

	//json过滤Bom头
	public static String JSONTokener(String in) {
		// consume an optional byte order mark (BOM) if it exists
		if (in != null && in.startsWith("\ufeff")) {
			in = in.substring(1);
		}
		return in;
	}

}
