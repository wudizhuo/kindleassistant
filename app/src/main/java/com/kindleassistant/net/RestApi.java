package com.kindleassistant.net;

import com.kindleassistant.entity.PreViewRequest;
import com.kindleassistant.entity.PreViewRsp;
import com.kindleassistant.entity.SendUrl;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApi {

    @POST("send")
    Call<Void> send(@Body SendUrl send);

    @POST("preview")
    Call<PreViewRsp> preview(@Body PreViewRequest send);

    @Multipart
    @POST("upload")
    Call<Void> upload(@Part List<MultipartBody.Part> multipartBody);
}
