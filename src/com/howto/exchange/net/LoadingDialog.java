package com.howto.exchange.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class LoadingDialog extends ProgressDialog {
	private Activity mActivity;
	private TextView loadingText;

	public LoadingDialog(Activity context) {
//		super(context, R.style.dialog_normal);
		super(context);
		mActivity = context;
//		setContentView(R.layout.loading);
//		setProperty();
//		setCancelable(false);
//		loadingText = (TextView) findViewById(R.id.loading_text);
	}
	
	public void setLoadingMessage(String message){
		if(!TextUtils.isEmpty(message)){
//			loadingText.setText(message);
			setMessage(message);
		}
	}

	public void show(String message) {
		if(!TextUtils.isEmpty(message)){
//			loadingText.setText(message);
			setMessage(message);
		}
		super.show();
	}

	public void close() {
		if (!mActivity.isFinishing()) {
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (isShowing()) {
						dismiss();
					}
				}
			});
		}
	}

	private void setProperty() {
		Window window = getWindow();
		WindowManager.LayoutParams p = window.getAttributes();
		Display d = getWindow().getWindowManager().getDefaultDisplay();

		p.height = (int) (d.getHeight() * 1);
		p.width = (int) (d.getWidth() * 1);
		window.setAttributes(p);
	}

}
