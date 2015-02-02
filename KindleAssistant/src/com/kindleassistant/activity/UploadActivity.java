package com.kindleassistant.activity;

import java.io.File;
import java.text.DecimalFormat;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kindleassistant.AppPreferences;
import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.net.FileUploadAsyncTask;
import com.kindleassistant.utils.LogUtil;
import com.kindleassistant.utils.StatServiceUtil;
import com.kindleassistant.utils.ToastUtil;

public class UploadActivity extends BaseActivity implements OnClickListener {
	private String uploads_url;
	private String user_email;
	// 要上传的文件路径，放在SD卡根目录下
	private String uploadFile = "";
	private File file;
	private ImageView image;
	private Button upload;
	private Button download;
	private static final int FILE_SELECT_CODE = 0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uploads);
		upload = (Button) findViewById(R.id.upload);
		upload.setOnClickListener(this);
		findViewById(R.id.btn_select).setOnClickListener(this);
	}
	private void showFileChooser() {  
	    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
	    intent.setType("*/*");  
	    intent.addCategory(Intent.CATEGORY_OPENABLE);  
	    try {  
	        startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"), FILE_SELECT_CODE);  
	    } catch (android.content.ActivityNotFoundException ex) {  
	        // Potentially direct the user to the Market with a Dialog  
	        Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT)  
	                .show();  
	    }  
	}  
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {  
        // TODO Auto-generated method stub  
        if (resultCode == this.RESULT_OK) {  
            // Get the Uri of the selected file  
            String uri = data.getData().toString();
            LogUtil.w("uri-----"+uri);
            String realPathFromURI = null;            
            if(uri.startsWith("file://")){
            	
            	String pattrn="file://";           
            	String[] url=uri.split(pattrn);
            	if(url == null || url.length <2 ){
            		ToastUtil.showInCenter("选取文件无效,请检查");
            		return;
            		
            	}
            	realPathFromURI = url[1];
            }else if(uri.startsWith("content://")){
            	realPathFromURI = getRealPathFromURI(data.getData());
            }
            this.uploadFile = realPathFromURI;
            file = new File(uploadFile);
            float length = file.length();
        	TextView file_text = (TextView) findViewById(R.id.file);
    		file_text.setText(this.uploadFile + "   大小为：" + String.format("%.5f", length/(1024 * 1024))+"M");
        }
    }  
	
	
	public String getRealPathFromURI(Uri contentUri) {
		  Cursor cursor = null;
		  try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = this.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
		}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_select:
			 showFileChooser();
			break;
		case R.id.upload:
			this.user_email = AppPreferences.getEmail();
			if (this.user_email == null || this.user_email.length() <= 0) {
	
				ToastUtil.showInCenter("请您先去设置邮箱");
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
}
