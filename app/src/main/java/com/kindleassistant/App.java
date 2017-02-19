package com.kindleassistant;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.kindleassistant.utils.ChannelUtil;
import com.qihoo.updatesdk.lib.UpdateHelper;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;

public class App extends Application {
    private static Context applicationContext;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        app = this;
        checkConfig();
    }

    /**
     * 检查应用配置
     */
    private void checkConfig() {
        // 友盟的配置
        // 调试模式
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
    }

    public void appinit() {
        AnalyticsConfig.setChannel(ChannelUtil.getUmengChannal(this));
        FeedbackAPI.init(this, "23639140");

        UpdateHelper.getInstance().init(getApplicationContext(), ContextCompat.getColor(getContext(), R.color.primary));
        UpdateHelper.getInstance().setDebugMode(BuildConfig.DEBUG);
        UpdateHelper.getInstance().autoUpdate(getContext().getPackageName());

        Context context = getApplicationContext();
        XGPushManager.registerPush(context, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.d("TPush", "注册成功，设备token为：" + data);
                AppPreferences.setRegisterPush(true);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });

        FeedbackAgent agent = new FeedbackAgent(this);
        agent.sync();
    }

    public static Context getContext() {
        return applicationContext;
    }

    public static App getApp() {
        return app;
    }

    /**
     * 获取app版本号。
     */
    public static String getVersionName() {
        try {
            return getContext().getPackageManager().getPackageInfo(
                    getContext().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
