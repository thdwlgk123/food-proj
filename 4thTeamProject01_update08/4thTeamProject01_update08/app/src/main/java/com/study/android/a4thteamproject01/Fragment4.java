package com.study.android.a4thteamproject01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.study.android.a4thteamproject01.mypage.LoginActivity;
import com.study.android.a4thteamproject01.mypage.ReservationPageAdapter;


public class Fragment4 extends Fragment {
    private static final String TAG="lecture";

    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tv_notLogin;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment4, container, false);


        tv_notLogin = rootView.findViewById(R.id.tv_res_notlogin);
        linearLayout = rootView.findViewById(R.id.linearLayout3);
        viewPager = rootView.findViewById(R.id.res_container);
        tabLayout = rootView.findViewById(R.id.res_tabmenu);

        SharedPreferences pref = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        String numAutoLogin = pref.getString("autoLogin", "a");
        Log.d(TAG ,"frag4 enabled:"+pref.getString("enabled","a"));
        String numEnabled = pref.getString("enabled", "a");
        Log.d(TAG, "id = " + id);
        Log.d(TAG, "autoLogin = " + numAutoLogin);
        Log.d(TAG, "enabled = " + numEnabled);

        if(numEnabled.equals("b")) {

            ReservationPageAdapter adapter = new ReservationPageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                public void onTabUnselected(TabLayout.Tab tab) {

                }

                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            tv_notLogin.setVisibility(View.GONE);

        } else {
            tv_notLogin.setText("로그인시\n이용가능합니다.\n(로그인 하러가기)");
            tv_notLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });

            linearLayout.setVisibility(View.GONE);

        }
        return rootView;
    }
}