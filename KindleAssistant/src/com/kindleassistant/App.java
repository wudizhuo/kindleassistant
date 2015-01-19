package com.kindleassistant;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.kindleassistant.entity.UserCreateApi.UserCreateRqt;
import com.kindleassistant.entity.UserCreateApi.UserCreateRsp;
import com.kindleassistant.manager.UpdateMgr;
import com.kindleassistant.manager.UserInfoMgr;
import com.kindleassistant.manager.VolleyMgr;
import com.kindleassistant.net.GsonRequest;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

public class App extends Application {
	private static Context applicationContext;
	private static App app;

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this.getApplicationContext();
		app = this;
		checkConfig();
	}

	/**
	 * 检查应用配置
	 */
	private void checkConfig() {
		// 友盟的配置
		// 调试模式
		MobclickAgent.setDebugMode(BuildConfig.DEBUG);
		// 使用在线配置功能
		MobclickAgent.updateOnlineConfig(getContext());
		// 检查友盟的res是否都复制进去了
		UmengUpdateAgent.setUpdateCheckConfig(BuildConfig.DEBUG);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
	}

	public void appinit() {
		if (TextUtils.isEmpty(UserInfoMgr.getInstance().getUserId())) {
			userCreate();
		}
		UpdateMgr.getInstance().checkUpdate();
		if (!AppPreferences.getRegisterPush()) {
			Context context = getApplicationContext();
			XGPushManager.registerPush(context, new XGIOperateCallback() {
				@Override
				public void onSuccess(Object data, int flag) {
					Log.d("TPush", "注册成功，设备token为：" + data);
					AppPreferences.setRegisterPush(true);
				}

				@Override
				public void onFail(Object data, int errCode, String msg) {
					Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
				}
			});
		}

		FeedbackAgent agent = new FeedbackAgent(this);
		agent.sync();
	}

	private void userCreate() {
		UserCreateRqt requst = new UserCreateRqt();
		VolleyMgr.getInstance().sendRequest(
				new GsonRequest<UserCreateRsp>(AppConstants.USER_CREATE,
						requst, UserCreateRsp.class,
						new Listener<UserCreateRsp>() {

							@Override
							public void onResponse(UserCreateRsp arg0) {
								if (arg0.getStatus() == 0) {
									UserInfoMgr.getInstance().saveAppUid(
											arg0.getApp_uid());
								}
							}
						}));
	}

	public static Context getContext() {
		return applicationContext;
	}

	public static App getApp() {
		return app;
	}

	/**
	 * 获取app版本号。
	 */
	public static String getVersionName() {
		try {
			return getContext().getPackageManager().getPackageInfo(
					getContext().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
