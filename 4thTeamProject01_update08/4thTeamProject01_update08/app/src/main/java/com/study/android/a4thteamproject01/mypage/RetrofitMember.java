package com.study.android.a4thteamproject01.mypage;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMember {

    Retrofit mRetrofit=new Retrofit.Builder()
            .baseUrl("http://52.78.115.228:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitMemSvc mService = mRetrofit.create(RetrofitMemSvc.class);
}
