package com.study.android.a4thteamproject01;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.a4thteamproject01.manager.ManagerMainActivity;
import com.study.android.a4thteamproject01.mypage.CheckReservation;
import com.study.android.a4thteamproject01.mypage.LoginActivity;
import com.study.android.a4thteamproject01.mypage.ModifyMyInfoActivity;
import com.study.android.a4thteamproject01.mypage.MyReviewActivity;
import com.study.android.a4thteamproject01.mypage.UserDto;

import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment5 extends Fragment {

    private static final String TAG="lecture";

    ImageView profileimg;
    TextView forNickname;
    TextView nickChange, myReview, modifyMyInfo, mgLogin, checkReservation;

    RetrofitMain retroservice;
    MyProfileAdapter adapter;
    LinearLayout linearLayout;

    int nCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment5, container, false);
        forNickname = rootView.findViewById(R.id.beforeLogin);
        profileimg = rootView.findViewById(R.id.profileimg);
        myReview = rootView.findViewById(R.id.checkMyReview);
        modifyMyInfo = rootView.findViewById(R.id.modifyMyInfo2);
        linearLayout = rootView.findViewById(R.id.linearLayout);
        mgLogin = rootView.findViewById(R.id.supervisor);
        checkReservation = rootView.findViewById(R.id.checkReservation);

        SharedPreferences pref = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        String numAutoLogin = pref.getString("autoLogin", "");
        String numEnabled = pref.getString("enabled", "");
        Log.d(TAG, "id = " + id);
        Log.d(TAG, "autoLogin = " + numAutoLogin);
        Log.d(TAG, "enabled = " + numEnabled);

        adapter = new MyProfileAdapter(rootView.getContext());

        if (numEnabled.equals("b") == true) {
            retroservice=new RetrofitMain();
            retroservice.service1.getMyprofile(id).enqueue(new Callback<JSONObjectResult>() {
                @Override
                public void onResponse(Call<JSONObjectResult> call, Response<JSONObjectResult> response) {
                    if (response.isSuccessful()) {
                        JSONObjectResult profile = response.body();

                        for(UserDto data:profile.myprofile){
                            forNickname.setText(data.getNickname() + "님 환영합니다.");

                        }

                    }
                }

                @Override
                public void onFailure(Call<JSONObjectResult> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            nCount = 1;


        } else {
          forNickname.setText("로그인을 해주세요");
        }


        Intent intent1 = new Intent(getActivity(), LoginActivity.class);
        Intent intent2 = new Intent(getActivity(), MyReviewActivity.class);
        Intent intent3 = new Intent(getActivity(), ModifyMyInfoActivity.class);
        Intent intent4 = new Intent(getActivity(), ManagerMainActivity.class);
        Intent intent5 = new Intent(getActivity(), CheckReservation.class);
        mgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent4);
            }
        });

        forNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });


            myReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numEnabled.equals("b") == true) {
                        startActivity(intent2);
                    } else {
                        Toast.makeText(getContext(), "로그인 후 사용가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            modifyMyInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numEnabled.equals("b") == true) {
                        startActivity(intent3);
                    } else {
                        Toast.makeText(getContext(), "로그인 후 사용가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        checkReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numEnabled.equals("b") == true) {
                    startActivity(intent5);
                } else {
                    Toast.makeText(getContext(), "로그인 후 사용가능합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;

    }
}