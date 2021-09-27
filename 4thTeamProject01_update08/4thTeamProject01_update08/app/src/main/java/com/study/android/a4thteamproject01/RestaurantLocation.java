package com.study.android.a4thteamproject01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import org.jetbrains.annotations.NotNull;

public class RestaurantLocation extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG="lecture";
    private com.naver.maps.map.NaverMap naverMap;

    double latitude ;
    double longitude ;
    String storename,storenumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_location);

        Intent intent = getIntent();
        latitude = Double.valueOf(intent.getStringExtra("latitude"));
        Log.d(TAG,""+ intent.getStringExtra("latitude"));
        longitude = Double.valueOf(intent.getStringExtra("longitude"));
        Log.d(TAG,""+ longitude);
        storename = intent.getStringExtra("storename");
        storenumber = intent.getStringExtra("storenumber");

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        // 맵레디 메서드 호출용
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull @NotNull NaverMap naverMap) {
        this.naverMap = naverMap;

        //현재 위치 모드
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
        naverMap.moveCamera(cameraUpdate);

        Marker marker = new Marker();
        marker.setIcon(MarkerIcons.BLACK);
        marker.setIconTintColor(Color.parseColor("#FF9800"));
        marker.setPosition(new LatLng(latitude,longitude));
        marker.setTag(storename);
        marker.setMap(naverMap);
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return (CharSequence) infoWindow.getMarker().getTag();
            }
        });

        marker.setOnClickListener(o -> {

            infoWindow.open(marker);
            return true;
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(RestaurantLocation.this, RestaurantInfoActivity.class);
        setResult(10, intent);
        finish();
    }
}