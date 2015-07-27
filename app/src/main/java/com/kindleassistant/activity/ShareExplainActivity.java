package com.kindleassistant.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;

public class ShareExplainActivity extends BaseActivity{
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shareexplain);
		WebView webview = (WebView) findViewById(R.id.web_howshare);
		webview.loadUrl("file:///android_asset/howuse.html");  
		// webview.loadData("file:///android_asset/kindle.html", "text/html; charset=UTF-8", "utf-8");
	}


}
