package com.kindleassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.kindleassistant.App;
import com.kindleassistant.AppConstants;
import com.kindleassistant.AppPreferences;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.entity.PreView;
import com.kindleassistant.entity.PreViewRsp;
import com.kindleassistant.entity.SendUrl;
import com.kindleassistant.entity.SendUrlRsp;
import com.kindleassistant.fragment.SlidingMenuRight;
import com.kindleassistant.manager.VolleyMgr;
import com.kindleassistant.net.GsonRequest;
import com.kindleassistant.utils.StatServiceUtil;

public class MainActivity extends BaseActivity implements OnClickListener {
	private String url;
	private String preview_url;
	private String user_url;
	private String shared_url;
	private String user_email;
	private EditText et_user_url;
	public static String RIGHT_FRAGMENT = "slidingmenu_right";
	private SlidingMenu slidingmenu;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		App.getApp().appinit();

		initRightMenu();

		et_user_url = (EditText) findViewById(R.id.et_user_url);
		// 监听点击事件
		findViewById(R.id.bt_send).setOnClickListener(this);
		findViewById(R.id.btn_title_right).setOnClickListener(this);
		findViewById(R.id.bt_clear).setOnClickListener(this);
		findViewById(R.id.bt_preview).setOnClickListener(this);
		this.url = AppConstants.SEND_URL;
		this.preview_url = AppConstants.PREVIEW_URL;

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
				this.shared_url = sharedUrl;
				et_user_url.setText(sharedUrl);
			}
		}

	}

	protected void onResume() {
		super.onResume();
	
		// 获取剪切板里面的内容
		ClipboardManager clipboarManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

		// 如果剪切板里面有内容就赋值给textview
		if (clipboarManager.getText() != null && TextUtils.isEmpty(shared_url)) {
			et_user_url.setText(clipboarManager.getText().toString());
		}
	}

	private void initRightMenu() {
		Button btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_left.setVisibility(View.INVISIBLE);
		Button btn_title_right = (Button) findViewById(R.id.btn_title_right);
		btn_title_right.setVisibility(View.VISIBLE);
		btn_title_right.setText("菜单");

		slidingmenu = new SlidingMenu(this);
		slidingmenu.setMode(SlidingMenu.RIGHT);
		slidingmenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingmenu.setBehindOffset(getWindowManager().getDefaultDisplay()
				.getWidth() / 3);
		slidingmenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingmenu.setMenu(R.layout.layout_menu_right);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.slidingmenu_right, new SlidingMenuRight(),
						RIGHT_FRAGMENT).commit();
		slidingmenu.setOnOpenedListener(new OnOpenedListener() {

			@Override
			public void onOpened() {
				StatServiceUtil.trackEvent("打开右菜单");
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_send:
			this.user_url = et_user_url.getText().toString();
			this.user_email = AppPreferences.getEmail();
			if (this.user_email == null || this.user_email.length() <= 0) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"请您先去设置邮箱", Toast.LENGTH_SHORT);
				toast.show();
				StatServiceUtil.trackEvent("未设置邮箱前发送点击");
				new Handler().postDelayed(new Runnable(){
					@Override
					public void run(){
						Intent intent = new Intent ( MainActivity.this,SettingActivity.class);			
						startActivity(intent);	
					}
				}, 300);
			} else if (TextUtils.isEmpty(this.user_url)) {
				StatServiceUtil.trackEvent("未填写url前发送点击");
				Toast toast = Toast.makeText(getApplicationContext(),
						"请填写文章链接", Toast.LENGTH_SHORT);

				toast.show();

			} else {
				StatServiceUtil.trackEvent("设置邮箱后发送按钮点击");
				SendPost();
			}
			break;
		case R.id.btn_title_right:
			slidingmenu.showMenu();
			break;
		case R.id.bt_clear:
			et_user_url.setText("");
			break;
		case R.id.bt_preview:
			this.user_url = et_user_url.getText().toString();
			if (TextUtils.isEmpty(this.user_url)) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"请填写文章链接", Toast.LENGTH_SHORT);
				StatServiceUtil.trackEvent("未填写url前预览点击");
				toast.show();

			} else {
				StatServiceUtil.trackEvent("预览点击");
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
									StatServiceUtil.trackEvent("发送按钮-发送成功");

									toast.show();
								} else {
									Toast toast = Toast.makeText(
											getApplicationContext(),
											arg0.getMsg(), Toast.LENGTH_SHORT);
									StatServiceUtil.trackEvent("发送按钮-发送失败--" + arg0.getMsg());
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
									StatServiceUtil.trackEvent("预览成功");
								} else {
									Toast toast = Toast.makeText(
											getApplicationContext(),
											arg0.getMsg(), Toast.LENGTH_SHORT);
									StatServiceUtil.trackEvent("预览失败--" + arg0.getMsg());
									toast.show();

								}
							}

						}));

	}

}