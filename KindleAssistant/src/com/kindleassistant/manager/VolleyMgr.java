package com.kindleassistant.manager;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kindleassistant.App;

/**
 * 
 * @author wuzhenyu
 *
 */
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

	public void getJson(String url) {
		mRequestQueue.add(new JsonObjectRequest(Method.POST, url, null,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("sunzhuo", "response : " + response.toString());
					}
				}, null) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("category_id", "100");
				return params;
			}
		});
	}
}
