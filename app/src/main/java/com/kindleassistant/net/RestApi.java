package com.kindleassistant.net;

import com.kindleassistant.entity.PreViewRequest;
import com.kindleassistant.entity.PreViewRsp;
import com.kindleassistant.entity.SendUrl;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestApi {

    @POST("send")
    Call<Void> send(@Body SendUrl send);

    @POST("preview")
    Call<PreViewRsp> preview(@Body PreViewRequest send);

}
