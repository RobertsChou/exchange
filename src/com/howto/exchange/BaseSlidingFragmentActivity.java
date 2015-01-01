package com.howto.exchange;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.howto.exchange.R;
import com.howto.exchange.bean.BaseBean;
import com.howto.exchange.bean.BaseGsonBean;
import com.howto.exchange.net.ItotemAsyncTask;
import com.howto.exchange.net.ItotemNetLib;
import com.howto.exchange.utils.BitmapHelp;
import com.howto.exchange.utils.PublicUtils;
import com.howto.exchange.utils.SharedPreferencesUtil;
import com.howto.exchange.utils.WindowParams;
import com.lidroid.xutils.BitmapUtils;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseSlidingFragmentActivity extends SlidingFragmentActivity implements View.OnClickListener
      ,View.OnCreateContextMenuListener {

	protected ItotemNetLib netLib;
	protected static Activity mContext;
	private DisplayMetrics dm;
	protected SharedPreferencesUtil spUtile;
	protected IApplication application;
	protected Object resultData;
	protected AlertDialog alert;
	protected ActionBar actionBar;
	protected SlidingMenu sm;
	protected BitmapUtils bitmapUtils;
	private String appId;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        bitmapUtils = BitmapHelp.getBitmapUtils(getApplicationContext());

		//requestWindowFeature(Window.FEATURE_NO_TITLE);  //使用actionbar不能使用这个method();
		//MobclickAgent.onError(this);
		mContext = this;
		netLib = ItotemNetLib.getInstance(this);
		spUtile = SharedPreferencesUtil.getinstance(this);
		application = (IApplication) getApplication();
		actionBar = getSupportActionBar();
		initSliding(savedInstanceState);
		WindowParams.init(this);
		loadXml();
		getIntentData(savedInstanceState);
		init();
		setData();

	}

	private void initSliding(Bundle savedInstanceState) {
		

		// customize the SlidingMenu
		sm = getSlidingMenu();
		//sm.setShadowWidthRes(R.dimen.shadow_width);
		//sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);    
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	/**
	 * 设置xml文件
	 */
	public abstract void loadXml();

	/**
	 * 获取intent数据
	 * 
	 * @param savedInstanceState
	 */
	public abstract void getIntentData(Bundle savedInstanceState);

	/**
	 * view 初始
	 */
	public abstract void init();

	/**
	 * 数据设置
	 */
	public abstract void setData();

	public abstract void onClick(int id);

	@Override
	public void onClick(View v) {
		onClick(v.getId());
	}


	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		return super.onCreatePanelMenu(featureId, menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		return super.onMenuItemSelected(featureId, item);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(onOptionsItemSelected(item.getItemId())){
			return true;
		}


		switch (item.getItemId()) {
		case android.R.id.home:		
			toggle();
			return true;
		default:
			return false;
		}
	}

	public abstract boolean onOptionsItemSelected(int itemId);

	@Override
	protected void onResume() {
		super.onResume();  

		MobclickAgent.onResume(this);
	}



	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * 提示�?
	 * 
	 * @param text 提示内容
	 */
	public void showText(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	public void showDlg(int icon, String msg, String ok, final DialogInterface.OnClickListener y,
			String cancel, final DialogInterface.OnClickListener n) {
		AlertDialog.Builder build = new Builder(this);
		build.setTitle("提醒");
		build.setIcon(icon);
		build.setMessage(msg);
		build.setNegativeButton(cancel, n);
		build.setPositiveButton(ok, y);
		alert = build.create();
		alert.setCanceledOnTouchOutside(true);
		alert.show();
	}

	public void showDlgItems(String title, int icon,final String[] items, DialogInterface.OnClickListener y) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setItems(items, y);
		builder.setIcon(icon);
		AlertDialog alert = builder.create();
		alert.setCanceledOnTouchOutside(true);
		alert.show();
		Window dialogWindow = alert.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		// dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽�?高用

		lp.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.6
		lp.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
		dialogWindow.setAttributes(lp);
	}

	public void showDlgView(String title,int icon ,final View arg0, String ok, DialogInterface.OnClickListener y, String cancel, final DialogInterface.OnClickListener n) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setView(arg0);
		builder.setIcon(icon);
		builder.setNegativeButton(cancel, n);
		builder.setPositiveButton(ok, y);
		AlertDialog alert = builder.create();
		alert.setCanceledOnTouchOutside(true);
		alert.show();
		Window dialogWindow = alert.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		// dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽�?高用

		lp.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.6
		lp.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
		dialogWindow.setAttributes(lp);
	}
	
	@Override
	protected void onPause() {
		super.onPause();

		try {
			MobclickAgent.onPause(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String method;
	private NetSycTask updateTask;
	protected boolean showNewVersion = false; // show the version message

	public class NetSycTask extends ItotemAsyncTask<String, String, BaseGsonBean> {


		private String requestIngM;

		public NetSycTask(Activity activity, String m) {
			super(activity);
			method = m;
			requestIngM = m;
		}

		public NetSycTask(Activity activity, String m, boolean showDlg) {
			super(activity, showDlg);
			method = m;
			requestIngM = m;
		}

		public NetSycTask(Activity mContext, String string, String string2) {
			super(mContext, null, true, true, true, string2);
			method = string;
			requestIngM = string;
		}

		@Override
		protected BaseGsonBean doInBackground(String... params) {
			super.doInBackground(params);
			BaseGsonBean data = null;
			try {
				Class c[] = null;
				if (params != null) {// 存在
					int len = params.length;
					c = new Class[len];
					for (int i = 0; i < len; ++i) {
						if (params[i] != null) {
							c[i] = params[i].getClass();
						} else {
							c[i] = String.class;
						}
					}
				}
				Method m = netLib.getClass().getMethod(requestIngM, c);
				Object arrayObj = Array.newInstance(String.class, params.length);
				data = (BaseGsonBean) m.invoke(netLib, params);
				if (data == null) {
					errorStr = getResources().getString(R.string.data_exception);
				} else if (!IApplication.code.equals(data.status)) {
					errorStr = data.info;
				}
			} catch (Exception e) {
				e.printStackTrace();
				errorStr = getResources().getString(R.string.net_exception);
			}
			return data;
		}

		@Override
		protected void onPostExecute(BaseGsonBean result) {
			super.onPostExecute(result);
			synchronized (method) {
				method = requestIngM;
				if (!interceptResult()) {
					if (errorStr != null) {
						showErrorText(errorStr);
						return;
					}
					if (!showResult(result)) {
						if (result.data == null
								|| !((result.data instanceof BaseBean) || (result.data instanceof List))) {
							showNoData();
							return;
						}
						showSuccess(result.data);
					}
				}
			}
		}

	}

	public void showSuccess(Object contents) {}

	public void showNoData(String string) {
		showText(string);
	}

	public void showNoData() {

	}
	public void showErrorText(String errorStr) {
		showText(errorStr);
	}

	public void goNext(Class<?> class1) {
		Intent intent = new Intent(mContext, class1);
		startActivity(intent);
	}

	/**
	 * 
	 * @return
	 */
	public boolean interceptResult() {
		return false;
	}

	public boolean showResult(Object result) {
		resultData = result;
		return false;
	}

	/**
	 * �?��版本返回接口
	 * 
	 * @param result
	 */
	/*
	 * ======= protected void goNext(Class<?> c) { Intent intent = new Intent(mContext, c);
	 * startActivity(intent); }
	 * 
	 * /** �?��版本返回接口
	 * 
	 * @param result
	 *//*
	 * >>>>>>> .r71 private void showUpdateDlg(final NewVersion result) { if (result.softUrl !=
	 * null) { AlertDialog.Builder build = new Builder(this); build.setTitle("更新提示");
	 * build.setMessage("发现新版�?是否下载更新�?); build.setNegativeButton("取消", null);
	 * build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { if (result.softUrl !=
	 * null) { Intent intent = new Intent(Intent.ACTION_VIEW);
	 * intent.setData(Uri.parse(result.softUrl)); startActivity(intent); } }
	 * 
	 * });
	 * 
	 * build.create(); build.show(); } else { if (showNewVersion) { showText("已经是最新版�?); } } }
	 */

	public boolean isUpadate(String eVip) {
		if (PublicUtils.isEmpty(eVip)) {
			return false;
		}
		String[] local = application.clientVersion.split("\\.");
		String[] server = eVip.split("\\.");
		String localCode="";
		String serverCode="";
		
		try {
			for (int i = 0; i < server.length; i++) {
				localCode=localCode+local[i].toString();
				serverCode=serverCode+server[i].toString();
				
				
			}
			PublicUtils.log("localCode"+localCode);
			PublicUtils.log("serverCode"+serverCode);
			if( Integer.parseInt(serverCode.trim()) > Integer.parseInt(localCode.trim()))
			{
			return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected void checkUpdate(boolean showDlg) {
		updateTask = new NetSycTask(this, "getVersion", showDlg);
		updateTask.execute(application.clientVersion);
	}
}
