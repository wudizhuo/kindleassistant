package com.kindleassistant.net;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Gson 网络请求
 * 
 * @author SunZhuo email：sunzhuo1228@126.com
 * 
 * @param <T>
 */
public class GsonRequest<T> extends Request<T> {
	private Gson gson;
	private Class<T> clazz;
	private Listener<T> listener;

	public GsonRequest(String url, Class<T> clazz, Listener<T> listener) {
		this(Method.POST, url, clazz, null, listener);
	}

	public GsonRequest(int method, String url, Class<T> clazz,
			ErrorListener errorListener, Listener<T> listener) {
		super(method, url, errorListener);
		gson = new Gson();
		this.clazz = clazz;
		this.listener = listener;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(gson.fromJson(json, clazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}
}
