package com.study.android.a4thteamproject01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoneReserveActivity extends AppCompatActivity {
    private static final String TAG="lecture";
    public static Context context_main;
    RetrofitMain retroservice;

    private String r_rsvnum;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    Button button;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_reserve);
        context_main=this;
        retroservice=new RetrofitMain();

        textView=findViewById(R.id.rsvnumber);
        textView1=findViewById(R.id.reserver);
        textView2=findViewById(R.id.rsvphone);
        textView3=findViewById(R.id.restaurant);
        textView4=findViewById(R.id.rsvdate);
        textView5=findViewById(R.id.rsvtime);
        textView6=findViewById(R.id.rsvnum);
        textView7=findViewById(R.id.rsvreq);
        layout=findViewById(R.id.rsvreqlin);

        Intent intent=getIntent();
        r_rsvnum=intent.getStringExtra("예약번호");
        Log.d(TAG,"get intent: "+r_rsvnum);
        textView.setText(r_rsvnum);

        //예약번호로 예약정보 가져오기
        retroservice.service1.getRsvInfo(r_rsvnum).enqueue(new Callback<BookedListDto>() {
            @Override
            public void onResponse(Call<BookedListDto> call, Response<BookedListDto> response) {
                if(response.isSuccessful()){
                    BookedListDto list=response.body();
                    Log.d(TAG,"list result: "+list);
                    textView1.setText(list.getNickname());
                    textView2.setText(list.getC_phone());
                    textView3.setText(list.getR_name());
                    textView4.setText(list.getTdate());
                    textView5.setText(list.getTime());
                    textView6.setText(Integer.toString(list.getB_party()));
                    if(!(list.getRequest()).equals(null)){
                        Log.d(TAG, "request 값 not null");
                        textView7.setText(list.getRequest());
                    }else{
                        textView7.setVisibility(View.GONE);
                    }
                }else{
                    Log.d(TAG,"onFailure");
                }
            }
            @Override
            public void onFailure(Call<BookedListDto> call, Throwable t) {
                Log.d(TAG,"onFailuer "+t.getMessage());
            }
        });

        button=findViewById(R.id.restaurantInfo2);

        //버튼클릭시 메인으로 이동
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main=new Intent(context_main,MainActivity.class);
                context_main.startActivity(main);
            }
        });
    }
}