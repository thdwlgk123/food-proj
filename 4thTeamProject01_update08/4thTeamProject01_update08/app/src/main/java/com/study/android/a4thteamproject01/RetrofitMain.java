package com.study.android.a4thteamproject01;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitMain {

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://52.78.115.228:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    RetrofitService service1=retrofit.create(RetrofitService.class);
}
