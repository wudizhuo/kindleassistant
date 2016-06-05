package com.kindleassistant.activity;

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

import java.util.Arrays;
import java.util.List;

public class SettingActivity extends BaseActivity implements OnClickListener {
	private Spinner sp_emails, sp_emails2;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		//to_email start
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
		//from_email

		EditText et_from_email = (EditText) findViewById(R.id.et_from_email);



		String user_from_email = AppPreferences.getFromEmail();
		if (!TextUtils.isEmpty(user_from_email)) {
			et_from_email.setText(user_from_email);

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

			EditText et_from_email = (EditText) findViewById(R.id.et_from_email);
			String user_email = et_user_email.getText().toString()+
					sp_emails.getSelectedItem().toString();
			String user_from_email = et_from_email.getText().toString();

			if (TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_from_email)) {
				Toast toast = Toast.makeText(getApplicationContext(), "请设置完整",
						Toast.LENGTH_SHORT);
				return;
			}

			AppPreferences.setEmail(user_email);
			AppPreferences.setFromEmail(user_from_email);
			Toast toast = Toast.makeText(getApplicationContext(), "设置成功",
					Toast.LENGTH_SHORT);
			toast.show();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			}, 100);

			break;

		default:
			break;
		}
	}
}
