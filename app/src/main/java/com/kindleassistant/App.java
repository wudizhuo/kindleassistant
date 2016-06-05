package com.kindleassistant;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.kindleassistant.manager.UpdateMgr;
import com.kindleassistant.utils.ChannelUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

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
        // 使用在线配置功能
        MobclickAgent.updateOnlineConfig(getContext());
        // 检查友盟的res是否都复制进去了
        UmengUpdateAgent.setUpdateCheckConfig(BuildConfig.DEBUG);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
    }

    public void appinit() {
        AnalyticsConfig.setChannel(ChannelUtil.getUmengChannal(this));
        UpdateMgr.getInstance().checkUpdate();
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
