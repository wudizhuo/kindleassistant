package com.kindleassistant.activity;

import java.io.UnsupportedEncodingException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.kindleassistant.AppConstants;
import com.kindleassistant.R;
import com.kindleassistant.R.string;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.entity.PreView;
import com.kindleassistant.entity.SendUrl;
import com.kindleassistant.entity.SendUrlRsp;
import com.kindleassistant.manager.VolleyMgr;
import com.kindleassistant.net.GsonRequest;

public class PreviewActivity extends BaseActivity implements OnClickListener{
	private String user_url;
	private String user_email;
	private String url;
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
		this.url = new AppConstants().SEND_URL;
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
		
			SharedPreferences sharedPreferences = getSharedPreferences(
					"user_email", this.MODE_PRIVATE);
			this.user_email = sharedPreferences.getString("email", "");
			if (this.user_email == null || this.user_email.length() <= 0) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"请您先去设置邮箱", Toast.LENGTH_SHORT);
				toast.show();
			} else {
				SendPost();
			}
			break;

		default:
			break;
		}
	}
	
	//发送到kindle
		public void SendPost() {
			showProgressDialog();
			SendUrl requst = new SendUrl(this.user_url, this.user_email);

			VolleyMgr.getInstance().sendRequest(
					new GsonRequest<SendUrlRsp>(this.url, requst, SendUrlRsp.class,
							new Listener<SendUrlRsp>() {

								@Override
								public void onResponse(SendUrlRsp arg0) {
									dismissProgressDialog();
									if (arg0.getStatus() == 0) {
				
										Toast toast = Toast.makeText(
												getApplicationContext(), "发送成功",
												Toast.LENGTH_SHORT);

										toast.show();
										
										finish();
									} else {
										Toast toast = Toast.makeText(
												getApplicationContext(),
												arg0.getMsg(), Toast.LENGTH_SHORT);
										toast.show();

									}
								}

							}));

		}
}
