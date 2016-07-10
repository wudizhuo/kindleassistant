package com.kindleassistant.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.kindleassistant.R;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    protected ProgressDialog loadingDialog;
    protected boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            return false;
        }
    };

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    public void showProgressDialog(String message) {
        if (isLoading) {
            loadingDialog.setMessage(message);
            return;
        }
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(message);
        loadingDialog.setIndeterminate(false);
        loadingDialog.setCanceledOnTouchOutside(false);

        if (!isFinishing()) {
            loadingDialog.show();
        }

        isLoading = true;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initTitleBar();
    }

    private void initTitleBar() {
        if (getSupportActionBar() == null && findViewById(R.id.view_title) != null) {
            toolbar = (Toolbar) findViewById(R.id.view_title);
            setSupportActionBar(toolbar);
        }
    }

    /**
     * 显示等待对话框
     */
    public void showProgressDialog() {
        this.showProgressDialog("");
    }

    /**
     * 取消等待对话框
     */
    public void dismissProgressDialog() {
        try {
            if (loadingDialog != null && !isFinishing()) {
                loadingDialog.dismiss();
                loadingDialog = null;
                isLoading = false;
            }
        } catch (IllegalArgumentException e) {
            Log.d("View", "loaded -> dialog already dismissed");
        }
    }

    public void onBackPressed(View v) {
        onBackPressed();
    }

}
