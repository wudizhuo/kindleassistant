package com.kindleassistant.manager;

import com.kindleassistant.App;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * 升级管理器
 * 
 * @author SunZhuo
 * 
 */
public class UserInfoMgr {
	private static UserInfoMgr mInstance = null;
	protected boolean hasNewVersion;

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
		return 1 + "";
	}

}
