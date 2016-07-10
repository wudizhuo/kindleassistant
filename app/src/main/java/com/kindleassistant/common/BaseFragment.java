package com.kindleassistant.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;

public class BaseFragment extends Fragment {
    protected Activity mContext;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = this.getActivity();
    }

    /**
     * 显示等待对话框
     *
     * @param msg 对话框显示的字体
     */
    public void showProgressDialog(final String msg) {
        if (this.getView() == null) {
            return;
        }
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).showProgressDialog(msg);
        }
    }

    public void dismissProgressDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).dismissProgressDialog();
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName());
    }

    public void onPause() {
        MobclickAgent.onPageEnd(this.getClass().getName());
        super.onPause();
    }
}
