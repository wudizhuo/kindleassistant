package com.kindleassistant.manager;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.kindleassistant.App;
import com.kindleassistant.AppPreferences;

/**
 * @author SunZhuo
 * 
 */
public class UserInfoMgr {
	private static UserInfoMgr mInstance = null;
	protected boolean hasNewVersion;
	private String appUid;

	private UserInfoMgr() {
	}

	/**
	 * 返回单例对象
	 * 
	 * @return
	 */
	public static UserInfoMgr getInstance() {
		if (null == mInstance) {
			mInstance = new UserInfoMgr();
		}
		return mInstance;
	}

	public String getUserId() {
		if (TextUtils.isEmpty(appUid)) {
			appUid = AppPreferences.getAppUid();
		}
		return appUid;
	}

	public String getImei() {
		String deviceId = ((TelephonyManager) App.getContext()
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		return deviceId;
	}

	public String getMac() {
		WifiManager wm = (WifiManager) App.getContext().getSystemService(
				Context.WIFI_SERVICE);
		return wm.getConnectionInfo().getMacAddress();
	}

	public void saveAppUid(String appUid) {
		this.appUid = appUid;
		AppPreferences.saveAppUid(appUid);
	}
}
