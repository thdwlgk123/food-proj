package com.study.android.a4thteamproject01.hyungdoo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;
import com.study.android.a4thteamproject01.R;
import com.study.android.a4thteamproject01.RestaurantInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NaverMap extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "lecture";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private FusedLocationSource locationSource;
    private com.naver.maps.map.NaverMap naverMap;
    Marker marker;
    Geocoder coder;


    InfoWindow infoWindow;
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    JSONObject jsonObject;
    List<Address> listToAddress;
    List<Address> geocoder = null;
    List<Marker> markers;
    Handler handler = new Handler(Looper.getMainLooper());

    JSONArray jsonArray;
    String gu_name;
    String address;
    String result2;
    double latitude ;
    double longtitude ;
    Button restaurantInfo;
    LinearLayout restaurantLayout;
    HashMap forIntent;

    String forNextActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navermap);


        restaurantInfo = findViewById(R.id.restaurantInfo);
        restaurantLayout = findViewById(R.id.forRestaurant);
        restaurantLayout.setVisibility(View.GONE);
        Intent intent = getIntent();
        latitude = Double.valueOf(intent.getStringExtra("latitude"));
        longtitude = Double.valueOf(intent.getStringExtra("longtitude"));
        markers = new ArrayList<>();
        marker = new Marker();
        forIntent = new HashMap();

        coder = new Geocoder(this);
        try{
            geocoder = coder.getFromLocation(Double.parseDouble(String.valueOf(latitude)),
                    Double.parseDouble(String.valueOf(longtitude)),10);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (IOException e){

            e.printStackTrace();
        }
        Log.d(TAG, geocoder.get(0).toString());
        this.address =geocoder.get(0).getAddressLine(0);
//        //http통신(okhttp)

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        getActionForaddress getActionForaddress = new getActionForaddress();
        getActionForaddress.execute();

       // 맵레디 메서드 호출용
        mapFragment.getMapAsync(this);
        restaurantInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NaverMap.this, RestaurantInfoActivity.class);
                String key = String.valueOf(forIntent.get(marker.getTag()));
                Log.d(TAG,"key" + key);
                intent.putExtra("업소관리번호",key);
                intent.putExtra("매장명",String.valueOf(marker.getTag()));
                startActivity(intent);

            }
        });

    }

    @Override
    public void onMapReady(@NonNull com.naver.maps.map.NaverMap naverMap) {
        this.naverMap = naverMap;


        //현재 위치 모드
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);


        //예약 버튼 사라지게
        naverMap.setOnMapClickListener(new com.naver.maps.map.NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF,  LatLng latLng) {
                restaurantInfo.setVisibility(View.GONE);
                restaurantLayout.setVisibility(View.GONE);
                infoWindow.close();
            }
        });

            infoWindow = new InfoWindow();
            infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
                @NonNull
                @Override
                public CharSequence getText(@NonNull InfoWindow infoWindow) {
                    return (CharSequence) infoWindow.getMarker().getTag();
                }
            });

    }

        public void onBtnClicked(View v) {
            Log.d(TAG, "onBtnClicked");
            Log.d(TAG, forNextActivity);
            Log.d(TAG, "클릭 마지막 (업체 정보 보기) " + forIntent.get(forNextActivity));

        }

    public class getActionForaddress extends AsyncTask<Void, Void, String> {
        ProgressDialog customCircleProgressDialog = new ProgressDialog(NaverMap.this);
        String result;
        String url = getString(R.string.ipv4) +"android/ForMarker"+ "?latitude=" + latitude + "&longitude=" + longtitude;



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
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                result = response.body().string();
            }catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute (String s){
            try{
                result2 = s;
                jsonObject  = new JSONObject(s);

                jsonArray = jsonObject.getJSONArray("restaurnatList");
                Log.d(TAG, result2);

                for(int i=0; i<jsonArray.length(); i++){
                    try{
                        jsonObject = jsonArray.getJSONObject(i);

                        listToAddress = coder.getFromLocationName(jsonObject.getString("r_adress1"), 10);
                        marker = new Marker();
                        marker.setIcon(MarkerIcons.BLACK);
                        marker.setIconTintColor(Color.parseColor("#FF9800"));
                        marker.setPosition(new LatLng(listToAddress.get(0).getLatitude(),  listToAddress.get(0).getLongitude()));

                        String r_name = jsonObject.getString("r_name");
                        String m_number = jsonObject.getString("m_number");

                        forIntent.put(r_name,m_number);
                        Log.d(TAG,"키 : " + r_name + " 밸류 : " + m_number);


                        marker.setTag(r_name);
                        markers.add(marker);


                    }catch (IOException | JSONException e){

                        e.printStackTrace();
                    }

                }
                for (Marker marker : markers) {

                    marker.setMap(naverMap);

                    marker.setOnClickListener(o -> {
                        restaurantInfo.setVisibility(View.VISIBLE);
                        restaurantLayout.setVisibility(View.VISIBLE);
                        infoWindow.open(marker);
                        forNextActivity = marker.getTag().toString();

                        return true;
                    });
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
            customCircleProgressDialog.dismiss();

        }
    }



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(NaverMap.this, com.study.android.a4thteamproject01.MainActivity.class);
        startActivity(intent);
        finish();
    }
}