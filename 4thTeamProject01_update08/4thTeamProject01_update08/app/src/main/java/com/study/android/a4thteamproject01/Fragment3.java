package com.study.android.a4thteamproject01;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.study.android.a4thteamproject01.JSONObjectResult;
import com.study.android.a4thteamproject01.RestaurantVertListAdapter;
import com.study.android.a4thteamproject01.RetrofitMain;

import retrofit2.Callback;
import retrofit2.Response;

public class Fragment3 extends Fragment {
    private static final String TAG="lecture";
    SharedPreferences.Editor editor;

    RestaurantVertListAdapter adapter;
    RecyclerView mRecyclerView;
    RetrofitMain retroservice;

    private String id;

    TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        retroservice=new RetrofitMain();
        adapter=new RestaurantVertListAdapter(rootView.getContext());
        mRecyclerView= rootView.findViewById(R.id.recyclerView);
        message=rootView.findViewById(R.id.whennull);
        
        SharedPreferences pref=getActivity().getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor=pref.edit();
        id=pref.getString("id","");
        Log.d(TAG, "User ID: "+id);

        //내가 좋아요한 목록
        retroservice.service1.getLikeList(id).enqueue(new Callback<JSONObjectResult>() {
            @Override
            public void onResponse(retrofit2.Call<JSONObjectResult> call, Response<JSONObjectResult> response) {
                if (response.isSuccessful()) {
                    JSONObjectResult order = response.body();
                    Log.d(TAG, String.valueOf(order.likelist));

                    if(order.likelist==null){
                        if(id.equals("")){
                            message.setText("로그인을 해주세요.");
                        } else {
                            message.setText("찜한 매장이 없습니다.");
                        }


                    }else {
                        for (PostResult data : order.likelist) {
                            SingerItem item = new SingerItem(data.getM_number(), data.getR_name(), data.getGu_name(), data.getT_name());
                            adapter.addItem(item);
//                        Log.d(TAG,"postresult: "+data.getTitle()+","+data.getWriter()+","+data.getCountlike());
                        }
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
                        mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    }
                } else {
                    Log.d(TAG, "onResponse-orderbylist:실패");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<JSONObjectResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return rootView;
    }
}