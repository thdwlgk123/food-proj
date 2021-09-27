package com.study.android.a4thteamproject01.mypage;

import com.google.gson.annotations.SerializedName;
import com.study.android.a4thteamproject01.ReviewListDto;

import java.util.ArrayList;

public class JSONObjectResult2 {
    @SerializedName("myreservation")
    ArrayList<ReservationDto> myreservation;

    @SerializedName("mod_data")
    ArrayList<UserDto> mod_data;

    @SerializedName("myReviewList")
    ArrayList<ReviewListDto> myReviewList;

    @SerializedName("siren")
    ArrayList<SirenDto> siren;
}
