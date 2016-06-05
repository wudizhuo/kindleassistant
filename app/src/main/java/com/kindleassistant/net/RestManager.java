package com.kindleassistant.net;

import retrofit2.Retrofit;

public class RestManager {
    private static RestManager instance;
    private final RestApi restApi;

    public RestManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
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
