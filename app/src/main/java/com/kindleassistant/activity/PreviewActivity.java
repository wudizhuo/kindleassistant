package com.kindleassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.kindleassistant.AppPreferences;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.entity.SendUrl;
import com.kindleassistant.net.HttpHelper;

import java.io.UnsupportedEncodingException;

public class PreviewActivity extends BaseActivity implements OnClickListener {
    private String user_url;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        WebView webview = (WebView) findViewById(R.id.et_content);
        Button btn_title_right = (Button) findViewById(R.id.btn_title_right);
        btn_title_right.setText("发送");
        btn_title_right.setVisibility(View.VISIBLE);
        findViewById(R.id.btn_title_right).setOnClickListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.user_url = bundle.getString("user_url");
        String content = bundle.getString("content");
        try {
            content = new String(content.getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        webview.loadData(content, "text/html; charset=UTF-8", "utf-8");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_title_right:
                if (TextUtils.isEmpty(AppPreferences.getToEmail())
                        || TextUtils.isEmpty(AppPreferences.getFromEmail())) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "请先设置邮箱", Toast.LENGTH_SHORT);
                    toast.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(PreviewActivity.this, SettingActivity.class);
                            startActivity(intent);
                        }
                    }, 100);
                } else {
                    HttpHelper.send(this, new SendUrl(this.user_url, AppPreferences.getToEmail(), AppPreferences.getFromEmail()));
                }
                break;

            default:
                break;
        }
    }
}
