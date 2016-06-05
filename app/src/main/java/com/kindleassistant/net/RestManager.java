package com.kindleassistant.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {
    private static RestManager instance;
    private final RestApi restApi;

    public RestManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.kindlezhushou.com/v3/")
                .build();

        restApi = retrofit.create(RestApi.class);
    }

    public static RestManager getInstance() {
        if (instance == null) {
            instance = new RestManager();
        }
        return instance;
    }

    public RestApi getRestApi() {
        return restApi;
    }
}
