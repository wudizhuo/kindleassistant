package com.kindleassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.kindleassistant.AppPreferences;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.net.HttpHelper;
import com.kindleassistant.utils.StatServiceUtil;

import java.io.UnsupportedEncodingException;

public class PreviewActivity extends BaseActivity implements OnClickListener{
	private String user_url;
	private String user_email;
	private String user_from_email;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_preview);
		WebView webview = (WebView) findViewById(R.id.et_content);
		Button btn_title_right = (Button) findViewById(R.id.btn_title_right);
		btn_title_right.setText("发送");
		btn_title_right.setVisibility(View.VISIBLE);
		findViewById(R.id.btn_title_right).setOnClickListener(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		this.user_url = bundle.getString("user_url");
		String content = bundle.getString("content");
		try {
			content=new String(content.getBytes(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webview.loadData(content, "text/html; charset=UTF-8", "utf-8");
		// 设置右上角按钮显示

		
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_title_right:
		
			this.user_email = AppPreferences.getEmail();
			this.user_from_email = AppPreferences.getFromEmail();
			if (this.user_email == null || this.user_email.length() <= 0) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"请您先去设置邮箱", Toast.LENGTH_SHORT);
				toast.show();
				StatServiceUtil.trackEvent("未设置邮箱前发送点击");
				new Handler().postDelayed(new Runnable(){
					@Override
					public void run(){
						Intent intent = new Intent (PreviewActivity.this,SettingActivity.class);			
						startActivity(intent);	
					}
				}, 300);
			} else {
				HttpHelper.SendPost(PreviewActivity.this, this.user_url, this.user_email, this.user_from_email);
			}
			break;

		default:
			break;
		}
	}

}
