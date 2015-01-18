package com.kindleassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.kindleassistant.AppConstants;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.entity.PreView;
import com.kindleassistant.entity.PreViewRsp;
import com.kindleassistant.entity.SendUrl;
import com.kindleassistant.entity.SendUrlRsp;
import com.kindleassistant.manager.VolleyMgr;
import com.kindleassistant.net.GsonRequest;

public class MainActivity extends BaseActivity implements OnClickListener {
	private String url;
	private String preview_url;
	private String user_url;
	private String user_email;
	private EditText et_user_url;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_user_url = (EditText) findViewById(R.id.et_user_url);
		// 设置右上角按钮显示
		Button btn_title_right = (Button) findViewById(R.id.btn_title_right);
		btn_title_right.setText("设置");
		btn_title_right.setVisibility(View.VISIBLE);
		// 监听点击事件
		findViewById(R.id.bt_send).setOnClickListener(this);
		findViewById(R.id.btn_title_right).setOnClickListener(this);
		findViewById(R.id.bt_clear).setOnClickListener(this);
		findViewById(R.id.bt_preview).setOnClickListener(this);
		this.url = new AppConstants().SEND_URL;
		this.preview_url = new AppConstants().PREVIEW_URL;
		// 获取剪切板里面的内容
		ClipboardManager clipboarManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

		// 如果剪切板里面有内容就赋值给textview
		if (clipboarManager.getText() != null) {
			this.user_url = clipboarManager.getText().toString();
			et_user_url.setText(this.user_url);
		}

		// 获取分享的网址url
		Intent intent = getIntent();
		// 获得Intent的Action
		String action = intent.getAction();
		// 获得Intent的MIME type
		String type = intent.getType();
		if (Intent.ACTION_SEND.equals(action) && type != null) {
			// 我们这里处理所有的文本类型
			if (type.startsWith("text/")) {
				// 处理获取到的文本，这里我们用TextView显示
				 String sharedUrl = intent.getStringExtra(Intent.EXTRA_TEXT);  
				 this.user_url = sharedUrl;
				 et_user_url.setText(this.user_url);
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_send:
			this.user_url = et_user_url.getText().toString();
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
		case R.id.btn_title_right:

			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_clear:
			et_user_url.setText("");
			break;
		case R.id.bt_preview:
			if (this.user_url == null) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"请填写文章链接", Toast.LENGTH_SHORT);

				toast.show();

			} else {
				PreView();
			}
			break;
		default:
			break;
		}
	}

	// 发送到kindle
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
								} else {
									Toast toast = Toast.makeText(
											getApplicationContext(),
											arg0.getMsg(), Toast.LENGTH_SHORT);
									toast.show();

								}
							}

						}));

	}

	// 预览内容
	public void PreView() {
		showProgressDialog();
		PreView requst = new PreView(this.user_url);

		VolleyMgr.getInstance().sendRequest(
				new GsonRequest<PreViewRsp>(this.preview_url, requst,
						PreViewRsp.class, new Listener<PreViewRsp>() {

							@Override
							public void onResponse(PreViewRsp arg0) {
								dismissProgressDialog();
								if (arg0.getStatus() == 0) {

									Intent intent1 = new Intent();
									intent1.setClass(MainActivity.this,
											PreviewActivity.class);
									intent1.putExtra("content",
											arg0.getContent());
									intent1.putExtra("user_url",
											MainActivity.this.user_url);
									startActivity(intent1);
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