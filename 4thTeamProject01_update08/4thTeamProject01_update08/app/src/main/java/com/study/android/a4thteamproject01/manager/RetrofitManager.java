package com.study.android.a4thteamproject01.manager;

import com.study.android.a4thteamproject01.JSONObjectResult;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManager {

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://52.78.115.228:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    RetrofitManagerSvc mgService = retrofit.create(RetrofitManagerSvc.class);

}
