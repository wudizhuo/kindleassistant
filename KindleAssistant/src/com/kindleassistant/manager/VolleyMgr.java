package com.kindleassistant.manager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kindleassistant.App;

public class VolleyMgr {

	private static VolleyMgr mInstance;
	private RequestQueue mRequestQueue;

	private VolleyMgr() {
		mRequestQueue = Volley.newRequestQueue(App.getContext());
	}

	public static VolleyMgr getInstance() {
		if (mInstance == null) {
			mInstance = new VolleyMgr();
		}
		return mInstance;
	}

	public void sendRequest(Request request) {
//		try {
//			request.getHeaders().put("custom_header1", "123");
//		} catch (AuthFailureError e) {
//			e.printStackTrace();
//		}
		mRequestQueue.add(request);
	}
}
