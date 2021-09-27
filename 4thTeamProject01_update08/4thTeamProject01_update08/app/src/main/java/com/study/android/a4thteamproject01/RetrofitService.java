package com.study.android.a4thteamproject01;

import com.study.android.a4thteamproject01.mypage.UserDto;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    // @GET( EndPoint-자원위치(URI) )
    @GET("android/{post}")
    Call<JSONObjectResult> getPosts(@Path("post") String post);
    @GET("android/search")
    Call<JSONObjectResult> postSearch(@Query("search") String search);
    @GET("android/getmenu")
    Call<JSONObjectResult> getMenu(@Query("mnumber") String mnumber, @Query("rname") String rname);
    @GET("android/getresinfo")
    Call<JSONObjectResult> getResInfo(@Query("mnumber") String mnumber, @Query("rname") String rname);
    @FormUrlEncoded
    @POST("android/insertreserveinfo")
    Call<Integer> getInsertReserveInfo(@FieldMap HashMap<String, Object> map);
    @GET("android/getrsvmeminfo")
    Call<UserDto> getRsvMemInfo(@Query("member_id") String c_id);
    @GET("android/getrsvinfo")
    Call<BookedListDto> getRsvInfo(@Query("r_rsvnum") String r_rsvnum);
    @GET("android/{post}")
    Call<Integer> checkLike(@Path("post") String post, @Query("m_number") String m_number,@Query("r_name") String r_name,@Query("c_id") String c_id);
    @GET("android/getlikelist")
    Call<JSONObjectResult> getLikeList(@Query("member_id") String id);
    @GET("android/getreviewlist")
    Call<ArrayList<ReviewListDto>> getReviewList(@Query("r_name") String r_name, @Query("m_number") String m_number);
    // frg5 데이터 가져오기
    @GET("android/myPage")
    Call<JSONObjectResult> getMyprofile(@Query("member_id") String id);

}
