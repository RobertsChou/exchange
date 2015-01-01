package com.howto.exchange.net;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * 在原有的基础上封装了dialog显示
 * 
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 */
public abstract class ItotemAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	private LoadingDialog ld;
	protected Activity taskContext;
	private boolean isShow = true;
	protected String errorStr;
	public boolean isRquesting;

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	
	public ItotemAsyncTask(Activity activity, final DialogInterface.OnCancelListener l, final boolean interruptTask,
			final boolean interruptIfRunning, boolean isShow, String message) {
		super();
		taskContext = activity;
		this.isShow = isShow;
		if (isShow) {
			ld = new LoadingDialog(activity);
			ld.setLoadingMessage(message);
			ld.setCancelable(true);
			ld.setCanceledOnTouchOutside(false);
			ld.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
				    isRquesting = false;
					if (interruptTask) {
						ItotemAsyncTask.this.cancel(interruptIfRunning);
					}
					if (l != null) {
						l.onCancel(dialog);
					}
				}
			});
		}
	}
	
	public ItotemAsyncTask(Activity activity, final DialogInterface.OnCancelListener l, final boolean interruptTask,
			final boolean interruptIfRunning, boolean isShow, String message,final boolean isExit) {
		super();
		taskContext = activity;
		this.isShow = isShow;
		if (isShow) {
			ld = new LoadingDialog(activity);
			ld.setLoadingMessage(message);
			ld.setCancelable(true);
			ld.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
				    isRquesting = false;
					if (interruptTask) {
						ItotemAsyncTask.this.cancel(interruptIfRunning);
					}
					if (l != null) {
						l.onCancel(dialog);
						
					}
					if(isExit){
						System.exit(0);
						}
				}
			});
		}
	}
	public ItotemAsyncTask(Activity activity) {
		this(activity, null, true, true, true, "正在加载,请稍后...");
	}
	public ItotemAsyncTask(Activity activity,boolean showDlg,boolean isExit) {
		this(activity, null, true, true, showDlg, "正在加载,请稍后...",isExit);
	}
	
	/**
	 * 
	 * @param activity
	 * @param showDlg 是否显示加载框
	 */
	public ItotemAsyncTask(Activity activity, boolean showDlg) {
		this(activity, null, true, true, showDlg, "正在加载,请稍后...");
	}

	@Override
	protected Result doInBackground(Params... params) {
		isRquesting = true;
		return null;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		isRquesting = false;
		if (null != taskContext && !taskContext.isFinishing() && ld != null) {
			ld.close();
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (null != taskContext && !taskContext.isFinishing() && ld != null && isShow) {
			ld.show();
		}
	}
}
