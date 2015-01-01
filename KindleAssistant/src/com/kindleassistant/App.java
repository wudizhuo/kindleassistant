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
