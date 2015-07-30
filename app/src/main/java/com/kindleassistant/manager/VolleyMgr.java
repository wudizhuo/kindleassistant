package com.kindleassistant.manager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
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
        request.setRetryPolicy(getRetryPolicy());
        mRequestQueue.add(request);
    }

    //TODO 测试为什么会重发 测试 code
    public RetryPolicy getRetryPolicy() {
        RetryPolicy retryPolicy = new DefaultRetryPolicy(6 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return retryPolicy;
    }
}
