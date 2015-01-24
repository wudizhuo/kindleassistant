package com.kindleassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.kindleassistant.R;
import com.kindleassistant.activity.AboutActivity;
import com.kindleassistant.activity.SettingActivity;
import com.kindleassistant.activity.ShareExplainActivity;
import com.kindleassistant.activity.UploadActivity;
import com.kindleassistant.common.BaseFragment;
import com.kindleassistant.manager.UpdateMgr;
import com.kindleassistant.manager.UpdateMgr.UpdateListener;
import com.kindleassistant.utils.StatServiceUtil;
import com.kindleassistant.utils.ToastUtil;
import com.umeng.fb.FeedbackAgent;

/**
 * 1 在设置页面添加清除缓存功能 2 添加设置页面
 * 
 * @author SunZhuo
 * 
 */
public class SlidingMenuRight extends BaseFragment implements OnClickListener {
	private boolean isClickCheckUpdate;
	private ImageView unread;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_slidingright, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		unread = (ImageView) view.findViewById(R.id.unread);
		view.findViewById(R.id.tv_setting).setOnClickListener(this);
		view.findViewById(R.id.feedback).setOnClickListener(this);
		view.findViewById(R.id.check_update).setOnClickListener(this);
		view.findViewById(R.id.share).setOnClickListener(this);
		view.findViewById(R.id.about).setOnClickListener(this);
		view.findViewById(R.id.uploads).setOnClickListener(this);
		initBtn();
		return view;
	}

	private void initBtn() {

		/**
		 * 检查更新 回调
		 */
		UpdateMgr.getInstance().setUpdateListener(new UpdateListener() {

			@Override
			public void onUpdateReturned(boolean hasNewVersion) {
				if (hasNewVersion) {
					unread.setVisibility(View.VISIBLE);
				} else {
					unread.setVisibility(View.GONE);
				}
				if (isClickCheckUpdate) {
					if (hasNewVersion) {
						ToastUtil.showInCenter("请更新版本！");
					} else {
						ToastUtil.showInCenter("当前已是最新版本");
					}
				}

				dismissProgressDialog();
			}
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_setting:
			toSetting();
			break;
		case R.id.check_update:
			checkUpdate();
			break;
		case R.id.feedback:
			settingFeedback();
			break;
		case R.id.share:
			share();
			break;
		case R.id.about:
			startActivity(new Intent(mContext, AboutActivity.class));
			break;
		case R.id.uploads:
			startActivity(new Intent(mContext, UploadActivity.class));
			break;
		default:
			break;
		}
	}
	private void share(){
		startActivity(new Intent(mContext, ShareExplainActivity.class));
		
	}
	private void checkUpdate() {
		isClickCheckUpdate = true;
		showProgressDialog("正在检查更新...");
		StatServiceUtil.trackEvent("检查更新");
		UpdateMgr.getInstance().checkUpdate();
	}

	/**
	 * 意见反馈的点击事件
	 */
	private void settingFeedback() {
		StatServiceUtil.trackEvent("意见反馈");
		FeedbackAgent agent = new FeedbackAgent(getActivity());
		agent.startFeedbackActivity();
	}

	/**
	 * 跳转到设置界面
	 */
	private void toSetting() {
		startActivity(new Intent(mContext, SettingActivity.class));
	}

}
