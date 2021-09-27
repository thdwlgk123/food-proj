package com.study.android.a4thteamproject01.manager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitManagerSvc {
    @GET("android/getresinfo_mg")
    Call<JSONObjectResult3> getResInfoMg(@Query("m_id") String mg_id);

    @FormUrlEncoded
    @POST("android/storedata_insert")
    Call<Integer> postStoreData(@FieldMap HashMap<String, String> map);
}
