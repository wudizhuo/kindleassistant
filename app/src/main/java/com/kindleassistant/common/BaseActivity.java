package com.kindleassistant.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.kindleassistant.R;
import com.kindleassistant.view.CustomerProgressDialog;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends AppCompatActivity {

	private CustomerProgressDialog progressDialog;
	private boolean progressOnShow = false;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void setSupportActionBar(Toolbar toolbar) {
		super.setSupportActionBar(toolbar);
		toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
	}

	Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem menuItem) {
			return false;
		}
	};

	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
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

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initTitleBar();
	}

	private void initTitleBar() {
		if (getSupportActionBar() == null && findViewById(R.id.view_title) != null) {
			toolbar = (Toolbar) findViewById(R.id.view_title);
			setSupportActionBar(toolbar);
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
