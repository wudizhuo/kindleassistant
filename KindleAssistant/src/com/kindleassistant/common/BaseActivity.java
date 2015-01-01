package com.kindleassistant.common;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.kindleassistant.view.CustomerProgressDialog;

public abstract class BaseActivity extends FragmentActivity {

	private CustomerProgressDialog progressDialog;
	private boolean progressOnShow = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 显示等待对话框
	 * 
	 * @param msg
	 *            对话框显示的字体
	 */
	public void showProgressDialog(String msg) {
		if (!isFinishing() && !progressOnShow) {
			progressDialog = new CustomerProgressDialog(this);
			if (!TextUtils.isEmpty(msg)) {
				progressDialog.setMessage(msg);
			}
			progressDialog.show();
			progressOnShow = true;
		}
	}

	/**
	 * 显示等待对话框
	 */
	public void showProgressDialog() {
		this.showProgressDialog(null);
	}

	/**
	 * 取消等待对话框
	 */
	public void dismissProgressDialog() {
		if (!isFinishing() && progressDialog != null
				&& progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressOnShow = false;
		}
	}

	public void onBackPressed(View v) {
		onBackPressed();
	}

}
