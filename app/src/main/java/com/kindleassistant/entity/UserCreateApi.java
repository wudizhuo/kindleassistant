package com.kindleassistant.entity;

import com.kindleassistant.common.BaseResponse;
import com.kindleassistant.manager.UserInfoMgr;

public class UserCreateApi {

	public static class UserCreateRqt {

		private String imei;
		private String mac;

		public UserCreateRqt() {
			this.imei = UserInfoMgr.getInstance().getImei();
			this.mac = UserInfoMgr.getInstance().getMac();
		}
	}

	public static class UserCreateRsp extends BaseResponse {

		private String app_uid;

		public String getApp_uid() {
			return app_uid;
		}
	}

}
