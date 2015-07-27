package com.kindleassistant.common;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.kindleassistant.App;
import com.kindleassistant.manager.UserInfoMgr;

public abstract class BaseRequest<T> extends Request<T> {

	public BaseRequest(int method, String url, ErrorListener listener) {
		super(method, url, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("User-Id", UserInfoMgr.getInstance().getUserId());
		headers.put("App-Version", App.getVersionName());
		return headers;
	}

}
