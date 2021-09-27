package com.study.android.a4thteamproject01.manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.study.android.a4thteamproject01.JSONObjectResult;
import com.study.android.a4thteamproject01.MenulistAdapter;
import com.study.android.a4thteamproject01.PostResult;
import com.study.android.a4thteamproject01.R;
import com.study.android.a4thteamproject01.ResInfoFragment1;
import com.study.android.a4thteamproject01.ResInfoFragment2;
import com.study.android.a4thteamproject01.ResInfoFragment3;
import com.study.android.a4thteamproject01.ResInfoPageAdapter;
import com.study.android.a4thteamproject01.RetrofitMain;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerMainActivity extends AppCompatActivity {
    private static final String TAG = "ManagerMain";

    public static Context context_main;

    private String m_storename;
    private String m_storenumber;
    private String m_phonenumber;
    private String m_storeaddress;
    private String m_storeaddress2="";
    private String menu;
    private String latitude, longitude, address;
    RetrofitManager retromg;
    //    RecyclerView mRecyclerView;
    MenulistAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    ImageView imageView;

    ResInfoFragment1 fragment1;
    ResInfoFragment2 fragment2;
    ResInfoFragment3 fragment3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
        String id = pref.getString("id","");
        Log.d(TAG, id);

        adapter=new MenulistAdapter(this);
        context_main=this;

        textView1=findViewById(R.id.m_storename);
        textView2=findViewById(R.id.m_storeaddress);
        textView3=findViewById(R.id.m_storetype);
        textView4=findViewById(R.id.m_phone);
        textView5=findViewById(R.id.m_storeaddress2);

        fragment1=new ResInfoFragment1();
        fragment2=new ResInfoFragment2();
        fragment3=new ResInfoFragment3();
        viewPager=findViewById(R.id.m_container2);
        tabLayout=findViewById(R.id.Tab3);

        retromg=new RetrofitManager();

        retromg.mgService.getResInfoMg(id).enqueue(new Callback<JSONObjectResult3>(){
            @Override
            public void onResponse(Call<JSONObjectResult3> call, Response<JSONObjectResult3> response) {
                if(response.isSuccessful()){
                    JSONObjectResult3 info=response.body();
                    Log.d(TAG, "receive info: "+String.valueOf(info));

                    ArrayList<PostResult> infodata=info.infolist_mg;
                    textView1.setText(infodata.get(0).getR_name());
                    textView2.setText(infodata.get(0).getR_adress1());
                    textView5.setText(infodata.get(0).getR_adress2());
                    m_storeaddress=infodata.get(0).getR_adress1();
                    m_storename=infodata.get(0).getR_name();
                    m_storenumber=infodata.get(0).getM_number();
                    //좌표 가져요기
//                    latitude = infodata.get(0).getCoordinates_y();
//                    Log.d(TAG,"lati : " + latitude);
//                    longitude = infodata.get(0).getCoordinates_x();
//                    Log.d(TAG,"long : " + longitude);
//                    if(infodata.get(0).getR_adress2()==(null)){
//                        Log.d(TAG, "address2 null");
//                    }else{
//                        m_storeaddress2=infodata.get(0).getR_adress2();
//                    }
//                    Log.d(TAG, "menu : "+menu);
                    textView3.setText(infodata.get(0).getT_name()+"\n"+infodata.get(0).getH_name());

                    //fragment1으로 값 전달
//                    Bundle bundle1=new Bundle();
//                    bundle1.putString("매장명",infodata.get(0).getR_name());
//                    bundle1.putString("매장주소",infodata.get(0).getR_adress1());
//                    bundle1.putString("매장분류",infodata.get(0).getT_name()+"\n"+infodata.get(0).getH_name());
//                    fragment1.setArguments(bundle1);

                    textView4.setText(infodata.get(0).getR_number());

                    //fragment2로 메뉴값 전달
                    Bundle bundle2=new Bundle();
                    if(infodata.get(0).getR_menu()==null){
                        menu="MENU_IS_NULL";
                    }else{
                        menu=infodata.get(0).getR_menu();
                    }

                    Log.d(TAG, menu);
                    bundle2.putString("메뉴",menu);
                    bundle2.putString("r_name",m_storename);
                    bundle2.putString("m_number",m_storenumber);

//                    fragment2.setArguments(bundle2);

                    ResInfoPageAdapter pageadapter=new ResInfoPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), bundle2);
                    viewPager.setAdapter(pageadapter);
                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
                        public void onTabSelected(TabLayout.Tab tab){
                            viewPager.setCurrentItem(tab.getPosition());
                        }
                        public void onTabUnselected(TabLayout.Tab tab){

                        }
                        public void onTabReselected(TabLayout.Tab tab){

                        }
                    });

                }else{
                    Log.d(TAG, "onResponse-likelist:실패");
                }
            }

            @Override
            public void onFailure(Call<JSONObjectResult3> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    public void onClickMod(View v) {
        Intent intent = new Intent(getApplicationContext(), ModifyManagerActivity.class);
        startActivity(intent);
    }

}