package com.kindleassistant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;

public class SettingActivity extends BaseActivity implements OnClickListener {
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		findViewById(R.id.bt_save_useremail).setOnClickListener(this);
		WebView webview = (WebView) findViewById(R.id.web_howset);
		webview.loadUrl("file:///android_asset/kindle.html");  
		// webview.loadData("file:///android_asset/kindle.html", "text/html; charset=UTF-8", "utf-8");
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_save_useremail:
			//实例化SharedPreferences对象（第一步） 
			SharedPreferences mySharedPreferences= getSharedPreferences("user_email", 
			this.MODE_PRIVATE); 
			//实例化SharedPreferences.Editor对象（第二步） 
			SharedPreferences.Editor editor = mySharedPreferences.edit(); 
			EditText et_user_email = (EditText) findViewById(R.id.et_user_email);
			 Spinner sp_emails = (Spinner) findViewById(R.id.sp_emails);
			 String user_email = et_user_email.getText().toString() + sp_emails.getSelectedItem().toString();
			//用putString的方法保存数据 
			editor.putString("email",user_email ); 
			//提交当前数据 
			editor.commit(); 
			Toast toast = Toast.makeText(getApplicationContext(),
					"设置成功", Toast.LENGTH_SHORT);
			toast.show();
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run(){
					Intent intent = new Intent (SettingActivity.this, MainActivity.class);			
					startActivity(intent);	
				}
			}, 800);
		
			
			break;

		default:
			break;
		}
	}
}
