package com.kindleassistant.net;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApi {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
