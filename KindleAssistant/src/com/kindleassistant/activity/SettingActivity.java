package com.kindleassistant.activity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kindleassistant.AppPreferences;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;

public class SettingActivity extends BaseActivity implements OnClickListener {
	private Spinner sp_emails;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		EditText et_user_email = (EditText) findViewById(R.id.et_user_email);
		sp_emails = (Spinner) findViewById(R.id.sp_emails);
		String email = AppPreferences.getEmail();
		if (!TextUtils.isEmpty(email)) {
			String[] split = email.split("@");
			if (split != null && split.length == 2) {
				et_user_email.setText(split[0]);
				String[] stringArray = getResources().getStringArray(
						R.array.sp_emails);
				List<String> asList = Arrays.asList(stringArray);
				int position = asList.indexOf("@" + split[1]);
				sp_emails.setSelection(position, true);
			}
		}
		findViewById(R.id.bt_save_useremail).setOnClickListener(this);
		WebView webview = (WebView) findViewById(R.id.web_howset);
		webview.loadUrl("file:///android_asset/kindle.html");
		// webview.loadData("file:///android_asset/kindle.html",
		// "text/html; charset=UTF-8", "utf-8");
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_save_useremail:
			EditText et_user_email = (EditText) findViewById(R.id.et_user_email);
			String user_email = et_user_email.getText().toString()
					+ sp_emails.getSelectedItem().toString();
			if (TextUtils.isEmpty(user_email)) {
				Toast toast = Toast.makeText(getApplicationContext(), "请填入邮箱",
						Toast.LENGTH_SHORT);
				return;
			}
			AppPreferences.setEmail(user_email);
			Toast toast = Toast.makeText(getApplicationContext(), "设置成功",
					Toast.LENGTH_SHORT);
			toast.show();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			}, 300);

			break;

		default:
			break;
		}
	}
}
