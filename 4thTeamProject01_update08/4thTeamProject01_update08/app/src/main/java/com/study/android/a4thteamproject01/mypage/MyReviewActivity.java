package com.study.android.a4thteamproject01.mypage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.android.a4thteamproject01.R;
import com.study.android.a4thteamproject01.RetrofitMain;
import com.study.android.a4thteamproject01.ReviewListAdapter;
import com.study.android.a4thteamproject01.ReviewListDto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReviewActivity extends AppCompatActivity {
    private static final String TAG = "MyReview";

    RecyclerView mRecyclerView;
    MyReviewListAdapter adapter;
    RetrofitMember mRetrofit;
    Toolbar reviewToolbar;

    TextView reviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        reviewToolbar = (Toolbar)findViewById(R.id.reviewToolbar);

        setSupportActionBar(reviewToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences pref =getSharedPreferences("login", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");

        mRetrofit=new RetrofitMember();
        mRecyclerView=findViewById(R.id.ReviewList2);
        adapter=new MyReviewListAdapter(getApplicationContext());
        reviewText = findViewById(R.id.review_text);
        reviewText.setVisibility(View.GONE);

        mRetrofit.mService.getMyReviewList(id).enqueue(new Callback<JSONObjectResult2>() {
            @Override
            public void onResponse(Call<JSONObjectResult2> call, Response<JSONObjectResult2> response) {
                if (response.isSuccessful()) {
                    JSONObjectResult2 rvlist = response.body();
                    if(rvlist.myReviewList!=null) {

                        for (ReviewListDto data : rvlist.myReviewList) {
                            if (data.getFilename() == null) {
                                ReviewListDto dto = new ReviewListDto(data.getR_name(), data.getGrade(), "", data.getContents(), data.getTdate());
                                adapter.addItem(dto);
                            } else {
                                ReviewListDto dto = new ReviewListDto(data.getR_name(), data.getGrade(), data.getFilename(), data.getContents(), data.getTdate());
                                adapter.addItem(dto);
                            }
                        }
                    }else{
                        reviewText.setVisibility(View.VISIBLE);
                        reviewText.setText("등록하신 리뷰가 없습니다");
                    }

                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                    mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                } else {
                    Log.d(TAG, "response but fail");
                }
            }

            @Override
            public void onFailure(Call<JSONObjectResult2> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
               finish();
               return true;
            default :
                return super.onOptionsItemSelected(item);

        }
    }

}