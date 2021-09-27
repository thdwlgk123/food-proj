package com.study.android.a4thteamproject01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.naver.maps.geometry.LatLng;
import com.study.android.a4thteamproject01.hyungdoo.NaverMap;
import com.study.android.a4thteamproject01.manager.ChatMainActivity;
import com.study.android.a4thteamproject01.mypage.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="lecture";
    // 자동로그인 //
    SharedPreferences.Editor editor;
    String numEnabled;

    ViewPager viewPager;
    TabLayout tabLayout;

    JSONObject jsonObject;
    JSONArray jsonArray;
    //툴바 / 실시간 채
    TextView toolbarTitle;
    ImageView chatbutton;

    Geocoder coder ;
    List<Address> geocoder = null;


    //위도 경도 주소//
    String latitude="";
    String longtitude="";
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"MainActivity");
        //권한 설정
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

        }



        coder = new Geocoder(this);
        requestMyLocation();

        //임의로 sharedpreference 설정
        SharedPreferences pref=getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor=pref.edit();
        numEnabled = pref.getString("enabled", "a");

        viewPager=findViewById(R.id.container);
        tabLayout=findViewById(R.id.tabMenu);
        toolbarTitle=findViewById(R.id.toolbarTitle);
        chatbutton = findViewById(R.id.chatbutton);

        // 툴바
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar2) ;
        setSupportActionBar(tb) ;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getActionForaddress getActionForaddress = new getActionForaddress();
        getActionForaddress.execute();

        PageAdapter adapter=new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        chatbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (numEnabled.equals("a")) {
                    Log.d(TAG,"chatbutton");
                    builder.setTitle("로그인 필요").setMessage("로그인 후에 가능합니다. 로그인을 진행해주세요.");
                    builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //로그인 화면으로 넘어가기
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                } else {
                    Intent intent = new Intent(MainActivity.this, ChatMainActivity.class);
                    Log.d(TAG, "채팅 버튼 클릭");
                    startActivity(intent);
                }
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainpage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void requestMyLocation() {
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {

            long minTime = 10000;
            float minDistance = 0;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,

                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
//                        Log.d(TAG, "onLocationChanged111111");
                            showCurrentLocation(location);
                            manager.removeUpdates(this);
                        }

                        @Override
                        public void
                        onStatusChanged(String provider, int status, Bundle extras) {
                            Log.d(TAG, "onLocationChanged222222");
                        }

                        @Override
                        public void
                        onProviderEnabled(String provider) {
                            Log.d(TAG, "onLocationChanged33333");
                        }

                        @Override
                        public void
                        onProviderDisabled(String provider) {
                            Log.d(TAG, "onLocationChanged444444");
                        }
                    }

            );

            Location lastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastLocation != null) {
                this.latitude = String.valueOf(lastLocation.getLatitude());
                Log.d(TAG,"lastLocation : " + String.valueOf(lastLocation.getLatitude()));
                this.longtitude = String.valueOf(lastLocation.getLongitude());
                Log.d(TAG,"lastLocation : " + String.valueOf(lastLocation.getLongitude()));
            }

            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
//                        Log.d(TAG, "onLocationChanged55555");
                            showCurrentLocation(location);
                            manager.removeUpdates(this);
                        }

                        @Override
                        public void
                        onStatusChanged(String provider, int status, Bundle extras) {
                            Log.d(TAG, "onLocationChanged666666");
                        }

                        @Override
                        public void
                        onProviderEnabled(String provider) {
                            Log.d(TAG, "onLocationChanged77777");
                        }

                        @Override
                        public void
                        onProviderDisabled(String provider) {
                            Log.d(TAG, "onLocationChanged88888");
                        }
                    }
            );

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d(TAG,"showCurrentLocation111111111111111");
        this.latitude = String.valueOf(location.getLatitude());
        Log.d(TAG,latitude);
        this.longtitude = String.valueOf(location.getLongitude());
        Log.d(TAG,longtitude);
    }

    public class getActionForaddress extends AsyncTask<Void, Void, String> {
        ProgressDialog customCircleProgressDialog = new ProgressDialog(MainActivity.this);
        String result;

        @Override
        protected void onPreExecute() {
            customCircleProgressDialog.setMessage("화면 준비 중....");
            customCircleProgressDialog.setCancelable(false);
            customCircleProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
            customCircleProgressDialog.show();

            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... voids) {
            try {

                geocoder = coder.getFromLocation(Double.parseDouble(String.valueOf(latitude)),
                        Double.parseDouble(String.valueOf(longtitude)), 10);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

            address = geocoder.get(0).getAddressLine(0);
            String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?"
                    + "query=" + address;
            Log.d(TAG, url);


            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .addHeader("X-NCP-APIGW-API-KEY-ID", "f1k23335ck")
                    .addHeader("X-NCP-APIGW-API-KEY", "j4WwbbSwbZHghEGaCkBEV7svDetkh4p2UHeZdBEb")
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                result = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            customCircleProgressDialog.dismiss();
            try {
                result = s;
                Log.d(TAG, result);
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("addresses");
                Log.d(TAG, "11111" + jsonArray.toString());
                jsonObject = jsonArray.getJSONObject(0);
                String address = jsonObject.getString("jibunAddress");
                Log.d(TAG, address);


                toolbarTitle.setText(address);

                toolbarTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, NaverMap.class);
                        Log.d(TAG, "버튼 클릭" + latitude + ", " + longtitude);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longtitude", longtitude);
                        startActivity(intent);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}