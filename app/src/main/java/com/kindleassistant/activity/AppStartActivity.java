package com.kindleassistant.activity;

import android.content.Intent;
import android.os.Bundle;

import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.utils.ChannelUtil;
import com.umeng.analytics.AnalyticsConfig;

public class AppStartActivity extends BaseActivity{

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.appstart);
		AnalyticsConfig.setChannel(ChannelUtil.getUmengChannal(this));
		Intent intent = new Intent (AppStartActivity.this,MainActivity.class);			
		startActivity(intent);			
		AppStartActivity.this.finish();
	}
}
