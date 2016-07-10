package com.kindleassistant.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kindleassistant.App;
import com.kindleassistant.AppPreferences;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.net.ErrorUtils;
import com.kindleassistant.net.ProgressRequestBody;
import com.kindleassistant.net.RestManager;
import com.kindleassistant.utils.ToastUtil;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UploadActivity extends BaseActivity implements OnClickListener {
    private String uploadFile = "";
    private static final int FILE_SELECT_CODE = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploads);
        findViewById(R.id.upload).setOnClickListener(this);
        findViewById(R.id.btn_select).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select:
                showFileChooser();
                break;
            case R.id.upload:
                updateClick();
                break;

            default:
                break;
        }

    }

    private void updateClick() {
        if (TextUtils.isEmpty(AppPreferences.getToEmail())
                || TextUtils.isEmpty(AppPreferences.getFromEmail())) {
            ToastUtil.showInCenter("请先设置发送邮箱或者信任邮箱");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(UploadActivity.this,
                            SettingActivity.class);
                    startActivity(intent);
                }
            }, 100);
            return;
        }
        uploadFile();
    }

    private void uploadFile() {
        File file = new File(uploadFile);
        List<MultipartBody.Part> list = new ArrayList<>();

        ProgressRequestBody progressRequestBody = new ProgressRequestBody(file, new ProgressRequestBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                showProgressDialog("文件上传中     " + percentage + "%");
            }
        });
        list.add(MultipartBody.Part.createFormData
                ("file", file.getName(), progressRequestBody));
        list.add(MultipartBody.Part.createFormData
                ("from_email", AppPreferences.getFromEmail()));
        list.add(MultipartBody.Part.createFormData
                ("to_email", AppPreferences.getToEmail()));

        showProgressDialog("文件上传中...");
        RestManager.getInstance().getRestApi().upload(list).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                UploadActivity.this.dismissProgressDialog();
                if (response.isSuccessful()) {
                    Toast.makeText(
                            App.getContext(), "发送成功",
                            Toast.LENGTH_SHORT).show();
                } else {
                    ErrorUtils.showError(response);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent(this, FilePickerActivity.class);

        intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(intent, FILE_SELECT_CODE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_SELECT_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            uploadFile = uri.getPath();

            File file = new File(uploadFile);
            float length = file.length();
            TextView file_text = (TextView) findViewById(R.id.file);
            file_text.setText(this.uploadFile + "   大小为：" + String.format("%.5f", length / (1024 * 1024)) + "M");
        }
    }
}
