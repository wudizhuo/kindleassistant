package com.kindleassistant.manager;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kindleassistant.net.GsonRequest;

public class VolleyMgr {
	public static <T> void sendGsonRequest(Context mContext, GsonRequest request) {
		RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
		mRequestQueue.add(request);
	}
}
