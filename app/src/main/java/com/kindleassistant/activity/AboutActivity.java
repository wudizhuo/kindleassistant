package com.kindleassistant.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kindleassistant.App;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.umeng.fb.FeedbackAgent;

/**
 * 欢迎页 显示图片
 *
 * @author SunZhuo
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView tv_about_ver = (TextView) findViewById(R.id.tv_about_ver);
        tv_about_ver.setText("版本号 : " + App.getVersionName());
        findViewById(R.id.tv_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackAgent agent = new FeedbackAgent(AboutActivity.this);
                agent.startFeedbackActivity();
            }
        });
    }
}
