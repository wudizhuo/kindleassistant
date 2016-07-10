package com.kindleassistant.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kindleassistant.AppPreferences;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.net.FileUploadAsyncTask;
import com.kindleassistant.utils.StatServiceUtil;
import com.kindleassistant.utils.ToastUtil;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;

public class UploadActivity extends BaseActivity implements OnClickListener {
    private String user_email, user_from_email;
    // 要上传的文件路径，放在SD卡根目录下
    private String uploadFile = "";
    private File file;
    private Button upload;
    private static final int FILE_SELECT_CODE = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploads);
        upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(this);
        findViewById(R.id.btn_select).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select:
                showFileChooser();
                break;
            case R.id.upload:
                this.user_email = AppPreferences.getEmail();
                this.user_from_email = AppPreferences.getFromEmail();
                if (this.user_email == null || this.user_email.length() <= 0 || this.user_from_email == null || this.user_from_email.length() <= 0) {

                    ToastUtil.showInCenter("请您先去设置发送邮箱或者信任邮箱");
                    StatServiceUtil.trackEvent("未设置邮箱前上传点击");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(UploadActivity.this,
                                    SettingActivity.class);
                            startActivity(intent);
                        }
                    }, 100);
                }
                File file = new File(uploadFile);
                new FileUploadAsyncTask(UploadActivity.this).execute(file);
                break;

            default:
                break;
        }

    }

    private void showFileChooser() {
        // This always works
        Intent intent = new Intent(this, FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(intent, FILE_SELECT_CODE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_SELECT_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            uploadFile = uri.getPath();

            file = new File(uploadFile);
            float length = file.length();
            TextView file_text = (TextView) findViewById(R.id.file);
            file_text.setText(this.uploadFile + "   大小为：" + String.format("%.5f", length / (1024 * 1024)) + "M");
        }
    }
}
