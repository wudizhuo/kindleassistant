package com.kindleassistant.net;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Response;
import com.kindleassistant.App;
import com.kindleassistant.AppConstants;
import com.kindleassistant.activity.MainActivity;
import com.kindleassistant.activity.PreviewActivity;
import com.kindleassistant.common.BaseActivity;
import com.kindleassistant.entity.PreView;
import com.kindleassistant.entity.PreViewRsp;
import com.kindleassistant.entity.SendUrl;
import com.kindleassistant.entity.SendUrlRsp;
import com.kindleassistant.manager.VolleyMgr;
import com.kindleassistant.utils.StatServiceUtil;

public class HttpHelper {

    public HttpHelper() {
        RestManager.getInstance().getRestApi();
    }



    // 发送到kindle
    public static void SendPost(BaseActivity activity, String user_url, String user_email, String user_from_email) {
        activity.showProgressDialog();
        SendUrl requst = new SendUrl(user_url, user_email, user_from_email);

        VolleyMgr.getInstance().sendRequest(
                new GsonRequest<SendUrlRsp>(AppConstants.SEND_URL, requst, SendUrlRsp.class,
                        new Response.Listener<SendUrlRsp>() {

                            @Override
                            public void onResponse(SendUrlRsp arg0) {
                                activity.dismissProgressDialog();
                                if (arg0.getStatus() == 0) {

                                    Toast toast = Toast.makeText(
                                            App.getContext(), "发送成功",
                                            Toast.LENGTH_SHORT);
                                    StatServiceUtil.trackEvent("发送按钮-发送成功");

                                    toast.show();
                                } else {
                                    Toast toast = Toast.makeText(
                                            App.getContext(),
                                            arg0.getMsg(), Toast.LENGTH_SHORT);
                                    StatServiceUtil.trackEvent("发送按钮-发送失败--"
                                            + arg0.getMsg());
                                    toast.show();

                                }
                            }

                        }));

    }

    // 预览内容
    public static void PreView(MainActivity mainActivity, String preview_url, String user_url) {
        mainActivity.showProgressDialog();
        PreView requst = new PreView(user_url);

        VolleyMgr.getInstance().sendRequest(
                new GsonRequest<PreViewRsp>(preview_url, requst,
                        PreViewRsp.class, new Response.Listener<PreViewRsp>() {

                            @Override
                            public void onResponse(PreViewRsp arg0) {
                                mainActivity.dismissProgressDialog();
                                if (arg0.getStatus() == 0) {

                                    Intent intent1 = new Intent();
                                    intent1.setClass(mainActivity,
                                            PreviewActivity.class);
                                    intent1.putExtra("content",
                                            arg0.getContent());
                                    intent1.putExtra("user_url",
                                            user_url);
                                    mainActivity.startActivity(intent1);
                                    StatServiceUtil.trackEvent("预览成功");
                                } else {
                                    Toast toast = Toast.makeText(
                                            App.getContext(),
                                            arg0.getMsg(), Toast.LENGTH_SHORT);
                                    StatServiceUtil.trackEvent("预览失败--"
                                            + arg0.getMsg());
                                    toast.show();

                                }
                            }

                        }));

    }
}
