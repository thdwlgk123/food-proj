package com.study.android.a4thteamproject01.mypage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.android.a4thteamproject01.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckReservation extends AppCompatActivity {
    private static final String TAG = "Login";
    RecyclerView recyclerView;
    RetrofitMember retroservice;
    Toolbar reviewToolbar;
    int nCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reservation);

        reviewToolbar = (Toolbar)findViewById(R.id.reviewToolbar2);

        setSupportActionBar(reviewToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.recyclerView);
        SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        String numAutoLogin = pref.getString("autoLogin", "");
        String numEnabled = pref.getString("enabled", "");
        Log.d(TAG, "id = " + id);
        Log.d(TAG, "autoLogin = " + numAutoLogin);
        Log.d(TAG, "enabled = " + numEnabled);
        CheckResListAdapter resListAdapter = new CheckResListAdapter(this);

        if (numEnabled.equals("b") == true) {
            retroservice = new RetrofitMember();
            retroservice.mService.getMyReservationList(id).enqueue(new Callback<JSONObjectResult2>() {
                @Override
                public void onResponse(Call<JSONObjectResult2> call, Response<JSONObjectResult2> response) {
                    if (response.isSuccessful()) {
                        JSONObjectResult2 res_list = response.body();
                        if(res_list.myreservation==null){
                            Log.d(TAG, "res_list resfrag1 is null");
//                                TextView text=new TextView(rootView.getContext());

                        }else{
                            for (ReservationDto data:res_list.myreservation) {
                                ReservationDto item = new ReservationDto(data.getR_rsvnumber(), data.getR_name(), data.getM_number(), data.getNickname(),
                                        data.getB_party(), data.getCondition_check(), data.getRequest(), data.getTdate(), data.getTime());

                                resListAdapter.resAddItem(item);
                            }

                            recyclerView.setAdapter(resListAdapter);
                            recyclerView.scrollToPosition(resListAdapter.getItemCount() - 1);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                        }
                    }
                }

                @Override
                public void onFailure(Call<JSONObjectResult2> call, Throwable t) {
                    t.printStackTrace();
                    Log.d(TAG, "onFailure: "+t.getMessage());
                }
            });

            nCount = 1;

        } else {
            Log.d(TAG, "실패");
        }
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