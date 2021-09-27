package com.study.android.a4thteamproject01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResInfoFragment1 extends Fragment {
    private static final String TAG="lecture";

//    RetrofitMain retroservice;
    TextView textView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"ResInfoFragment1");
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_res_info1, container, false);

//        retroservice=new RetrofitMain();

//        Bundle bundle=getArguments();
//        String storename=bundle.getString("매장명");
//        Log.d(TAG,"RESINFOFRAG1 : "+storename+","+storeadress2+", "+storetype);

//        textView1.setText(storename);
        return rootView;
    }
}