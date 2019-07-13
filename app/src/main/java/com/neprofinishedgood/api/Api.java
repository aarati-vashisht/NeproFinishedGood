package com.neprofinishedgood.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String BASE_URL = "http://10.10.10.238:8012/api/Nepro/";///client url

    private static Retrofit retrofit = null;
    private static OkHttpClient client;

    public static Retrofit getClient() {

        if (retrofit == null) {
            client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.MINUTES).connectTimeout(5, TimeUnit.MINUTES).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}