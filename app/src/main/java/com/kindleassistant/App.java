package com.kindleassistant;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.kindleassistant.utils.ChannelUtil;
import com.qihoo.updatesdk.lib.UpdateHelper;
import com.umeng.analytics.AnalyticsConfig;

public class App extends Application {
    private static Context applicationContext;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        app = this;
    }

    public void appinit() {
        AnalyticsConfig.setChannel(ChannelUtil.getUmengChannal(this));
        FeedbackAPI.init(this, "23639140");

        initUpdateHelper();
    }

    private void initUpdateHelper() {
        if (getContext().getPackageName().equals("com.googleplay.kindleassistant")) {
            return;
        }
        UpdateHelper.getInstance().init(getApplicationContext(), ContextCompat.getColor(getContext(), R.color.primary));
        UpdateHelper.getInstance().setDebugMode(BuildConfig.DEBUG);
        UpdateHelper.getInstance().autoUpdate(getContext().getPackageName());
    }

    public static Context getContext() {
        return applicationContext;
    }

    public static App getApp() {
        return app;
    }
}
