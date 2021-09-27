package com.study.android.a4thteamproject01;

import com.google.gson.annotations.SerializedName;
import com.study.android.a4thteamproject01.mypage.UserDto;

import java.util.ArrayList;

public class JSONObjectResult {
    @SerializedName("likeboard")
    ArrayList<PostResult> likeresult;
    @SerializedName("recentboard")
    ArrayList<PostResult> recentresult;
    @SerializedName("searchboard")
    ArrayList<PostResult> searchresult;
    @SerializedName("menulist")
    String menulist;
    @SerializedName("infolist")
    ArrayList<PostResult> infolist;
    @SerializedName("likelistboard")
    ArrayList<PostResult> likelist;
    @SerializedName("myprofile")
    ArrayList<UserDto> myprofile;
}
