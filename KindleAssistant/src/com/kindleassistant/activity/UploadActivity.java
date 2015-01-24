package com.kindleassistant.activity;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kindleassistant.R;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.net.FileUploadAsyncTask;
import com.kindleassistant.utils.LogUtil;

public class UploadActivity extends BaseActivity implements OnClickListener {
	private String uploads_url;
	private String user_email;
	// 要上传的文件路径，放在SD卡根目录下
	private String uploadFile = Environment.getExternalStorageDirectory()
			.getPath() + "/Download/A.jpg";
	private TextView file;
	private ImageView image;
	private Button upload;
	private Button download;
	private static final int FILE_SELECT_CODE = 0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uploads);

		file = (TextView) findViewById(R.id.file);
		file.setText(uploadFile);
		image = (ImageView) findViewById(R.id.image);
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
            Uri uri = data.getData();  
            String realPathFromURI = getRealPathFromURI(uri);
//            url = FileUtils.getPath(getActivity(), uri);
//			Log.i("ht", "url" + url);
//			String fileName = url.substring(url.lastIndexOf("/") + 1);
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

			File file = new File(uploadFile);
			new FileUploadAsyncTask(UploadActivity.this).execute(file);
			break;

		default:
			break;
		}
		
	}
}
