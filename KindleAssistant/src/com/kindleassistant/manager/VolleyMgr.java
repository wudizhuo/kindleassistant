package com.kindleassistant.manager;

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
		mRequestQueue.add(request);
	}
}
