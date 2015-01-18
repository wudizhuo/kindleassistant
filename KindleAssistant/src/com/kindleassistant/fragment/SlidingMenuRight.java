package com.kindleassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.kindleassistant.R;
import com.kindleassistant.activity.SettingActivity;
import com.kindleassistant.common.BaseFragment;

/**
 * 1 在设置页面添加清除缓存功能 2 添加设置页面
 * 
 * @author SunZhuo
 * 
 */
public class SlidingMenuRight extends BaseFragment implements OnClickListener {
	private TextView tv_download;
	private TextView tv_setting;
	private ImageView iv_setting_night;
	private boolean downloading = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_slidingright, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		tv_download = (TextView) view.findViewById(R.id.tv_download);
		tv_setting = (TextView) view.findViewById(R.id.tv_setting);
		tv_download.setOnClickListener(this);
		tv_setting.setOnClickListener(this);
		return view;
	}


	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}


	/**
	 * 跳转到设置界面
	 */
	private void toSetting() {
		startActivity(new Intent(mContext, SettingActivity.class));
	}


}
