package com.kindleassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;

public class AppStart extends BaseActivity{

	public AppStart() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.appstart);
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				Intent intent = new Intent (AppStart.this,MainActivity.class);			
				startActivity(intent);			
				AppStart.this.finish();
			}
		}, 1000);
	}
}
