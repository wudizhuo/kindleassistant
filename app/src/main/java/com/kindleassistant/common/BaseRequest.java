package com.kindleassistant.common;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.kindleassistant.App;
import com.kindleassistant.manager.UserInfoMgr;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRequest<T> extends Request<T> {

	public BaseRequest(int method, String url, ErrorListener listener) {
		super(method, url, listener);
		// TODO Auto-generated constructor stub
	}

	//TODO 重发机制一定要搞定  老是自动重发 太差劲了
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("User-Id", UserInfoMgr.getInstance().getUserId());
		headers.put("App-Version", App.getVersionName());
		return headers;
	}

}
