package com.study.android.a4thteamproject01;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import okhttp3.internal.http2.ErrorCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment2 extends Fragment {

    private static final String TAG="lecture";
    SearchView searchView;
    RestaurantListAdapter adapter;
    RecyclerView mRecyclerView;
    RetrofitMain retroservice;
    TextView textView;
//    ConstraintLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        Log.d(TAG,"Fragment2");
        searchView=rootView.findViewById(R.id.search_bar);
        adapter=new RestaurantListAdapter(rootView.getContext());
//        layout=rootView.findViewById(R.id.constraintlayout);
        textView=rootView.findViewById(R.id.whennull);

        retroservice=new RetrofitMain();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(rootView.getContext(), "검색 처리됨: "+query, Toast.LENGTH_SHORT).show();

                retroservice.service1.postSearch(query).enqueue(new Callback<JSONObjectResult>(){
                    @Override
                    public void onResponse(Call<JSONObjectResult> call, Response<JSONObjectResult> response) {
                        if(response.isSuccessful()){
                            JSONObjectResult searchlist=response.body();
                            if(searchlist.searchresult==null){
                                textView.setText("(검색 결과가 없습니다.)");

                            }else{
                                textView.setText("");
                                for(PostResult data:searchlist.searchresult){
                                    SingerItem item=new SingerItem(data.getM_number(),data.getR_name(), data.getGu_name(),data.getT_name(),data.getCountreview(), data.getGradeavg());
                                    adapter.addItem(item);

//                                Log.d(TAG,"postresult: "+data.getR_name()+","+data.getGu_name()+","+data.getT_name());
                                }
                                mRecyclerView= rootView.findViewById(R.id.RecyclerView2);
                                mRecyclerView.setAdapter(adapter);
                                mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                                mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
                                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            }

                        }else{
                            Log.d(TAG, "onResponse-searchlist:실패");
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<JSONObjectResult> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }

                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return rootView;
    }

}