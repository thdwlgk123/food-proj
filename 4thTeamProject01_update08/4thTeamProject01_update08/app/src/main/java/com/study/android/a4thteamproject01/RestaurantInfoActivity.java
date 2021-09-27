package com.study.android.a4thteamproject01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.naver.maps.geometry.LatLng;

import com.study.android.a4thteamproject01.hyungdoo.MyUtil;
import com.study.android.a4thteamproject01.hyungdoo.SirenOrder;
import com.study.android.a4thteamproject01.mypage.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantInfoActivity extends AppCompatActivity {
    private static final String TAG="lecture";

    public static Context context_main;
    SharedPreferences.Editor editor;
//    int logincheck;
    Integer likecheck;
    String id;
    String logincheck;
    JSONObject jsonObject;
    JSONArray jsonArray;
//    private NestedScrollView nestscroll;
    private String storename;
    private String storenumber;
    private String phonenumber;
    private String storeaddress;
    private String storeaddress2="";
    private String menu;
    private String latitude, longitude, address;
    private String res_latitude, res_longitude;
    RetrofitMain retroservice;
//    RecyclerView mRecyclerView;
    MenulistAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    ImageView restaurantImage;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    Button like;
    Button unlike;
    Button share;
    //위치
    Button location;


    String[] permissions=new String[]{Manifest.permission.CALL_PHONE};
//            Manifest.permission.READ_CONTACTS,
//            Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        SharedPreferences pref=getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor=pref.edit();
        id=pref.getString("id","");
//        logincheck=Integer.parseInt(pref.getString("enabled", ""));
        logincheck=pref.getString("enabled", "a");
        Log.d(TAG, "enabled check String result: "+logincheck);
        //위치권한동의 permission 체크
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

        }
        requestMyLocation();
        restaurantImage = findViewById(R.id.restaurantIamge);
        like=findViewById(R.id.like);
        unlike=findViewById(R.id.unlike);
        share=findViewById(R.id.share);
        location = findViewById(R.id.location);

        restaurantImage.setImageResource(R.drawable.salad);

        if(!checkPermissions()){
            Toast.makeText(getApplicationContext(), "권한 설정을 해주셔야 앱이 정상동작합니다.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        adapter=new MenulistAdapter(this);
        context_main=this;

        textView1=findViewById(R.id.storename);
        textView2=findViewById(R.id.storeaddress);
        textView3=findViewById(R.id.storetype);
        viewPager=findViewById(R.id.container2);
        tabLayout=findViewById(R.id.Tab2);

        retroservice=new RetrofitMain();
        //activity에서 받아온 값
        Intent intent=getIntent();
        storename=intent.getStringExtra("매장명");
        storenumber=intent.getStringExtra("업소관리번호");
        Log.d(TAG, "매장명:"+storename+"업소번호:"+storenumber);


        //로그아웃 상태일 때 좋아요 버튼 색상 설정
        if(id.equals("")){
            Log.d(TAG, "like button 설정 if문0");
            like.setVisibility(View.GONE);
            unlike.setVisibility(View.VISIBLE);
        }else{
//로그인 상태일 시에 db 에 가서 좋아요 여부 확인
            checkLike();
        }

        like.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                undoLikebutton();
            }
        });
        unlike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doLikebutton();
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putmarker();
            }
        });
        //공유하기 버튼 클릭릭
       share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Sharing_intent = new Intent(Intent.ACTION_SEND);
                Sharing_intent.setType("text/plain");
                String Test_Message = storename+" \n"+textView2.getText()+"\n"+phonenumber;
                Sharing_intent.putExtra(Intent.EXTRA_TEXT, Test_Message);
                Intent Sharing = Intent.createChooser(Sharing_intent, "공유하기");
                startActivity(Sharing);
            }
        });
        retroservice.service1.getResInfo(storenumber, storename).enqueue(new Callback<JSONObjectResult>(){

            @Override
            public void onResponse(Call<JSONObjectResult> call, Response<JSONObjectResult> response) {
                if(response.isSuccessful()){
                    JSONObjectResult info=response.body();
                    Log.d(TAG, "receive info: "+String.valueOf(info));

                    ArrayList<PostResult> infodata=info.infolist;
                    textView1.setText(infodata.get(0).getR_name());
                    textView2.setText(infodata.get(0).getR_adress1());
                    storeaddress=infodata.get(0).getR_adress1();
                    //좌표 가져요기
                    res_latitude = infodata.get(0).getCoordinates_y();
                    Log.d(TAG,"lati : " + res_latitude);
                    res_longitude = infodata.get(0).getCoordinates_x();
                    Log.d(TAG,"long : " + res_longitude);
                    if(infodata.get(0).getR_adress2()==(null)){
                        Log.d(TAG, "address2 null");
                    }else{
                        storeaddress2=infodata.get(0).getR_adress2();
                    }
//                    Log.d(TAG, "menu : "+menu);
                    textView3.setText(infodata.get(0).getT_name()+"\n"+infodata.get(0).getH_name());

                    phonenumber=infodata.get(0).getR_number();

                    //fragment2로 메뉴값 전달
                    Bundle bundle2=new Bundle();
                    if(infodata.get(0).getR_menu()==null){
                        menu="MENU_IS_NULL";
                    }else{
                        menu=infodata.get(0).getR_menu();
                    }

                    Log.d(TAG, menu);
                    bundle2.putString("메뉴",menu);
                    bundle2.putString("r_name",storename);
                    bundle2.putString("m_number",storenumber);

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
            public void onFailure(Call<JSONObjectResult> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });



    }
    //전화버튼 클릭시 전화어플 실행
    public void onCallBtnClicked(View v){
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+phonenumber));
        startActivity(intent);
    }
    //전화번호를 위한 권한동의
    private boolean checkPermissions(){
        int result;
        List<String> listPermissionsNeeded=new ArrayList<>();
        for(String p:permissions){
            result= ContextCompat.checkSelfPermission(this, p);
            if(result!= PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(p);
            }
        }
        if(!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1);
            return false;
        }
        return true;
    }

    //좋아요 확인
    public void checkLike(){
        Log.d(TAG, "method: checkLIKE ");
        retroservice.service1.checkLike("checklike", storenumber, storename, id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    likecheck=response.body();
                    if(likecheck==0){
                        Log.d(TAG, "like button 설정 if문1");
                        like.setVisibility(View.GONE);
                        unlike.setVisibility(View.VISIBLE);
                    }else{
                        Log.d(TAG, "like button 설정 else문");
                        unlike.setVisibility(View.GONE);
                        like.setVisibility(View.VISIBLE);
                    }
                }else{
                    Log.d(TAG, "onFailure");
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "onFailure; "+t.getMessage());
            }
        });
    }
    //위치 표시
    public void putmarker(){
        //좌표값으로 null을 가지고 있는게 없어서 조건문 안넣었습니당.
        Intent intent = new Intent(RestaurantInfoActivity.this, RestaurantLocation.class);
        intent.putExtra("latitude",res_latitude);
        intent.putExtra("longitude",res_longitude);
        intent.putExtra("storename",storename);
        intent.putExtra("storenumber",storenumber);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==10){
            data.getStringExtra("storename");
            data.getStringExtra("storenumber");

        }
    }

    //좋아요 취소구현
    public void undoLikebutton(){
        Log.d(TAG, "undolikeButton");
        retroservice.service1.checkLike("undolike",storenumber, storename, id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    int rs=response.body();
                    if(rs==1){
                        like.setVisibility(View.GONE);
                        unlike.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(getApplicationContext(), "좋아요에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d(TAG, "onFailure");
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "onFailure; "+t.getMessage());
            }
        });
    }
    //좋아요 구현
    public void doLikebutton() {
        Log.d(TAG, "dolikeButton");
        if (id.equals("")) {
            MyUtil.AlertShow(RestaurantInfoActivity.this, "로그인을 해주세요.");
        } else {
            retroservice.service1.checkLike("dolike", storenumber, storename, id).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        int rs = response.body();
                        if (rs == 1) {
                            unlike.setVisibility(View.GONE);
                            like.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(), "좋아요에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(TAG, "onFailure");
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d(TAG, "onFailure; " + t.getMessage());
                }
            });
        }
    }
    String[] storelist=new String[]{"두르가 (종각)","두르가","바로화덕치킨","지호한방삼계탕","채선당종로점","독도참치종각점"};
    int checkstore = 0;


    //사이렌 오더 버튼 클릭 시 로그인 확인창
//    public void onSirenBtnClicked(View v) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
////        로그인이 안되어있을 시에 예약 불가
//        Log.d(TAG, "siren login alert");
//        Log.d(TAG,"storename : " + storename);
//        //메뉴 가격이 없으면 사이렌 오더x
//        for(String store:storelist){
////          Log.d(TAG ,"RESINFOFRAG2:for문 storename: "+store);
//            if(storename.equals(store) == true){
////              Log.d(TAG ,"RESINFOFRAG2:for문 if문 들옴");
//                checkstore=1;
//                break;
//            }
//        }
//        if (menu.equals("MENU_IS_NULL")|checkstore==0) {
//            MyUtil.AlertShow(RestaurantInfoActivity.this, "사이렌 등록 음식점이 아닙니다.");
//        } else {
//
//            if (logincheck.equals("a") == true) {
//                builder.setTitle("로그인 필요").setMessage("로그인 후에 예약가능합니다. 로그인을 진행해주세요.");
//                builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        //로그인 화면으로 넘어가기
//                        Intent intent = new Intent(RestaurantInfoActivity.this, LoginActivity.class);
////                    intent.putExtra("업소관리번호",storenumber);
////                    intent.putExtra("매장명",명);
//                        startActivity(intent);
////                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(getApplicationContext(), "예약 진행을 취소하였습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            } else {
//                sirenOrder_button();
//            }
//
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//        }
//    }
    //예약하기 버튼을 누르면 예약확인 알림창 띄우기
    public void onReserveBtnClicked(View v){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        로그인이 안되어있을 시에 예약 불가
        if(logincheck.equals("a") == true)
        {
            builder.setTitle("로그인 필요").setMessage("로그인 후에 예약가능합니다. 로그인을 진행해주세요.");
            builder.setPositiveButton("로그인", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    //로그인 화면으로 넘어가기
                    Intent intent = new Intent(RestaurantInfoActivity.this, LoginActivity.class);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "예약 진행을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            Log.d(TAG,"logincheck==1");
            builder.setTitle("예약하기").setMessage(storename + " 예약을 진행하시겠습니까?");
            builder.setPositiveButton("예약하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //예약화면으로 넘어가기
                    Intent intent = new Intent(context_main, ReservationActivity.class);
                    intent.putExtra("업소관리번호", storenumber);
                    intent.putExtra("매장명", storename);
                    context_main.startActivity(intent);
                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getApplicationContext(), "예약 진행을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //사이렌오더
//    public void sirenOrder_button() {
//        String result;
//
//        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?"
//                + "query="+ storeaddress +" "+storeaddress2+ "&coordinate=" + longitude+","+latitude;
//        Log.d(TAG, url);
//
//
//        NetworkTask networktask = new NetworkTask(url);
//        networktask.execute();
//
//    }
//
//    public class NetworkTask extends AsyncTask<Void, Void, String> {
//
//        String url;
//        String result;
//        public NetworkTask(String url) {
//            this.url = url;
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            OkHttpClient client = new OkHttpClient();
//            Log.d(TAG, "11111111");
//            Request request = new Request.Builder()
//                    .addHeader("X-NCP-APIGW-API-KEY-ID", "f1k23335ck")
//                    .addHeader("X-NCP-APIGW-API-KEY", "j4WwbbSwbZHghEGaCkBEV7svDetkh4p2UHeZdBEb")
//                    .url(url)
//                    .build();
//            Log.d(TAG, "22222222222");
//            try (okhttp3.Response response = client.newCall(request).execute()) {
//                result = response.body().string();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//        @Override
//        protected void onPostExecute(String s) {
//            Log.d(TAG, s);
//            try {
//                jsonObject = new JSONObject(s);
//                Log.d(TAG, jsonObject.toString());
//                jsonArray = jsonObject.getJSONArray("addresses");
//                Log.d(TAG, jsonArray.toString());
//                jsonObject = jsonArray.getJSONObject(0);
//                Log.d(TAG, jsonObject.toString());
//                double distance = Double.valueOf(jsonObject.getString("distance"));
//                Log.d(TAG,"결과" + String.valueOf(distance));
//
//                //단위 : 미터
//                if(distance<0){
//                    MyUtil.AlertShow(RestaurantInfoActivity.this, "거리가 멀어 주문하실 수 없습니다.");
//                }else{
//
//                    Intent intent = new Intent(RestaurantInfoActivity.this, SirenOrder.class);
//                    Log.d(TAG, "버튼 클릭" + String.valueOf(latitude) + longitude);
//                    intent.putExtra("r_name", storename);
//                    intent.putExtra("r_menu", menu);
//                    intent.putExtra("r_adress1",storeaddress); //address2 붙이기
//                    intent.putExtra("m_number",storenumber);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

    private void requestMyLocation() {
        Log.d(TAG, "requestMyLocation Method");
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
//                            Log.d(TAG, "onLocationChanged111111");
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

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                this.latitude = String.valueOf(lastLocation.getLatitude());
                Log.d(TAG,"lastLocation : " + String.valueOf(lastLocation.getLatitude()));
                this.longitude = String.valueOf(lastLocation.getLongitude());
                Log.d(TAG,"lastLocation : " + String.valueOf(lastLocation.getLongitude()));
            }

            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
//                            Log.d(TAG, "onLocationChanged55555");
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

        this.latitude = String.valueOf(location.getLatitude());
        this.longitude = String.valueOf(location.getLongitude());

    }
}