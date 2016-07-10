package com.kindleassistant.net;

import android.content.Intent;
import android.widget.Toast;

import com.kindleassistant.App;
import com.kindleassistant.activity.MainActivity;
import com.kindleassistant.activity.PreviewActivity;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.entity.PreViewRequest;
import com.kindleassistant.entity.PreViewRsp;
import com.kindleassistant.entity.SendUrl;

import retrofit2.Call;
import retrofit2.Callback;

public class HttpHelper {

    public static void send(BaseActivity activity, SendUrl request) {
        activity.showProgressDialog("发送中...");
        RestManager.getInstance().getRestApi().send(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                activity.dismissProgressDialog();
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

    public static void PreView(MainActivity activity, String url, PreViewRequest request) {
        activity.showProgressDialog();

        RestManager.getInstance().getRestApi().preview(request).enqueue(new Callback<PreViewRsp>() {
            @Override
            public void onResponse(Call<PreViewRsp> call, retrofit2.Response<PreViewRsp> response) {
                activity.dismissProgressDialog();

                if(response.isSuccessful()){
                    Intent intent = new Intent();
                    intent.setClass(activity,
                            PreviewActivity.class);
                    intent.putExtra("content",
                            response.body().getContent());
                    intent.putExtra("user_url",
                            url);
                    activity.startActivity(intent);
                }else {
                    ErrorUtils.showError(response);
                }
            }

            @Override
            public void onFailure(Call<PreViewRsp> call, Throwable t) {

            }
        });
    }
}
