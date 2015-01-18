package com.kindleassistant;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class App extends Application {
	private static Context applicationContext;

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this.getApplicationContext();
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

	public static Context getContext() {
		return applicationContext;
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
