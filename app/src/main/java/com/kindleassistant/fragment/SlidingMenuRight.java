package com.kindleassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.kindleassistant.App;
import com.kindleassistant.R;
import com.kindleassistant.activity.AboutActivity;
import com.kindleassistant.activity.SettingActivity;
import com.kindleassistant.activity.ShareExplainActivity;
import com.kindleassistant.activity.UploadActivity;
import com.kindleassistant.common.BaseFragment;
import com.kindleassistant.utils.StatServiceUtil;
import com.kindleassistant.utils.ToastUtil;
import com.qihoo.updatesdk.lib.UpdateHelper;

/**
 * 1 在设置页面添加清除缓存功能 2 添加设置页面
 *
 * @author SunZhuo
 */
public class SlidingMenuRight extends BaseFragment implements OnClickListener {
    private boolean isClickCheckUpdate;
    private ImageView unread;
    private View checkUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slidingright, null);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        unread = (ImageView) view.findViewById(R.id.unread);
        view.findViewById(R.id.tv_setting).setOnClickListener(this);
        view.findViewById(R.id.feedback).setOnClickListener(this);
        checkUpdate = view.findViewById(R.id.check_update);
        checkUpdate.setOnClickListener(this);
        view.findViewById(R.id.share).setOnClickListener(this);
        view.findViewById(R.id.about).setOnClickListener(this);
        view.findViewById(R.id.uploads).setOnClickListener(this);
        initBtn();
        return view;
    }

    private void initBtn() {
        if (getContext().getPackageName().equals("com.googleplay.kindleassistant")) {
            checkUpdate.setVisibility(View.GONE);
        } else {
            checkUpdate.setVisibility(View.VISIBLE);
        }
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

    private void share() {
        startActivity(new Intent(mContext, ShareExplainActivity.class));

    }

    private void checkUpdate() {
        isClickCheckUpdate = true;
        showProgressDialog("正在检查更新...");
        StatServiceUtil.trackEvent("检查更新");
        UpdateHelper.getInstance().manualUpdate(App.getContext().getPackageName());
    }

    private void settingFeedback() {
        ToastUtil.show("同样也欢迎到我的微博：无敌卓，反馈沟通。");
        FeedbackAPI.openFeedbackActivity();
    }

    private void toSetting() {
        startActivity(new Intent(mContext, SettingActivity.class));
    }

}
