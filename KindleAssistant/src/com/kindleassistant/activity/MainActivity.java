package com.kindleassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kindleassistant.AppConstants;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.manager.VolleyMgr;

public class MainActivity extends BaseActivity {

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		VolleyMgr.getInstance().getJson(AppConstants.SEND_URL);
	}

	public void Setting(View v){
			Intent intent = new Intent (this,MainActivity.class);			
			startActivity(intent);			
			this.finish();
	}
}
