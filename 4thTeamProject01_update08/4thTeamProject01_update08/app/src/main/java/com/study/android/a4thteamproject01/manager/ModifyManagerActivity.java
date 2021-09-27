package com.study.android.a4thteamproject01.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.a4thteamproject01.PostResult;
import com.study.android.a4thteamproject01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyManagerActivity extends AppCompatActivity {
    private static final String TAG = "ModifyManager";

    TextView tv_storename;
    TextView tv_storeaddress1;
    TextView tv_storeaddress2;
    TextView tv_storetype;
    EditText et_phone;
    EditText et_opentime;
    EditText et_holiday;
    EditText et_info;
    EditText et_menulist;
    Button btn_regist;

    RetrofitManager maRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_manager);

        tv_storename = findViewById(R.id.m_storename2);
        tv_storeaddress1 = findViewById(R.id.m_storeaddress3);
        tv_storeaddress2 = findViewById(R.id.m_storeaddress4);
        tv_storetype = findViewById(R.id.m_storetype2);
        et_phone = findViewById(R.id.et_mPhone);
        et_opentime = findViewById(R.id.et_opentime);
        et_holiday = findViewById(R.id.et_holiday);
        et_info = findViewById(R.id.et_info);
        et_menulist = findViewById(R.id.et_menu);
        btn_regist = findViewById(R.id.btn_modregist);
        maRetrofit = new RetrofitManager();

        SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
        String id = pref.getString("id","");
        Log.d(TAG, id);

        maRetrofit.mgService.getResInfoMg(id).enqueue(new Callback<JSONObjectResult3>() {
            @Override
            public void onResponse(Call<JSONObjectResult3> call, Response<JSONObjectResult3> response) {
                if(response.isSuccessful()) {
                    JSONObjectResult3 info=response.body();
                    Log.d(TAG, "receive info: "+String.valueOf(info));
                    ArrayList<PostResult> infodata=info.infolist_mg;

                    tv_storename.setText(infodata.get(0).getR_name());
                    tv_storeaddress1.setText(infodata.get(0).getR_adress1());
                    if (infodata.get(0).getR_adress2() == null) {
                        tv_storeaddress2.setVisibility(View.GONE);
                    } else {
                        tv_storeaddress2.setText(infodata.get(0).getR_adress2());
                    }
                    tv_storetype.setText(infodata.get(0).getT_name()+"\n"+infodata.get(0).getH_name());
                    et_phone.setText(infodata.get(0).getR_number());
                    et_menulist.setText(infodata.get(0).getR_menu());
                } else {
                    Log.d(TAG, "onResponse-likelist:실패");
                }
            }

            @Override
            public void onFailure(Call<JSONObjectResult3> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("r_number", et_phone.getText().toString());
                map.put("r_menu", et_menulist.getText().toString());
                map.put("m_id", id);

                maRetrofit.mgService.postStoreData((HashMap<String, String>) map).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            if (response.body() == 1) {
                                Toast.makeText(getApplicationContext(), "등록완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ManagerMainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "등록실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
            }
        });

    }

}