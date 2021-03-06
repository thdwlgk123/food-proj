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
    //??????
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
        //?????????????????? permission ??????
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
            Toast.makeText(getApplicationContext(), "?????? ????????? ???????????? ?????? ?????????????????????.",
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
        //activity?????? ????????? ???
        Intent intent=getIntent();
        storename=intent.getStringExtra("?????????");
        storenumber=intent.getStringExtra("??????????????????");
        Log.d(TAG, "?????????:"+storename+"????????????:"+storenumber);


        //???????????? ????????? ??? ????????? ?????? ?????? ??????
        if(id.equals("")){
            Log.d(TAG, "like button ?????? if???0");
            like.setVisibility(View.GONE);
            unlike.setVisibility(View.VISIBLE);
        }else{
//????????? ????????? ?????? db ??? ?????? ????????? ?????? ??????
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
        //???????????? ?????? ?????????
       share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Sharing_intent = new Intent(Intent.ACTION_SEND);
                Sharing_intent.setType("text/plain");
                String Test_Message = storename+" \n"+textView2.getText()+"\n"+phonenumber;
                Sharing_intent.putExtra(Intent.EXTRA_TEXT, Test_Message);
                Intent Sharing = Intent.createChooser(Sharing_intent, "????????????");
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
                    //?????? ????????????
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

                    //fragment2??? ????????? ??????
                    Bundle bundle2=new Bundle();
                    if(infodata.get(0).getR_menu()==null){
                        menu="MENU_IS_NULL";
                    }else{
                        menu=infodata.get(0).getR_menu();
                    }

                    Log.d(TAG, menu);
                    bundle2.putString("??????",menu);
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
                    Log.d(TAG, "onResponse-likelist:??????");
                }
            }

            @Override
            public void onFailure(Call<JSONObjectResult> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });



    }
    //???????????? ????????? ???????????? ??????
    public void onCallBtnClicked(View v){
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+phonenumber));
        startActivity(intent);
    }
    //??????????????? ?????? ????????????
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

    //????????? ??????
    public void checkLike(){
        Log.d(TAG, "method: checkLIKE ");
        retroservice.service1.checkLike("checklike", storenumber, storename, id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    likecheck=response.body();
                    if(likecheck==0){
                        Log.d(TAG, "like button ?????? if???1");
                        like.setVisibility(View.GONE);
                        unlike.setVisibility(View.VISIBLE);
                    }else{
                        Log.d(TAG, "like button ?????? else???");
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
    //?????? ??????
    public void putmarker(){
        //??????????????? null??? ????????? ????????? ????????? ????????? ??????????????????.
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

    //????????? ????????????
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
                        Toast.makeText(getApplicationContext(), "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
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
    //????????? ??????
    public void doLikebutton() {
        Log.d(TAG, "dolikeButton");
        if (id.equals("")) {
            MyUtil.AlertShow(RestaurantInfoActivity.this, "???????????? ????????????.");
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
                            Toast.makeText(getApplicationContext(), "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
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
    String[] storelist=new String[]{"????????? (??????)","?????????","??????????????????","?????????????????????","??????????????????","?????????????????????"};
    int checkstore = 0;


    //????????? ?????? ?????? ?????? ??? ????????? ?????????
//    public void onSirenBtnClicked(View v) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
////        ???????????? ??????????????? ?????? ?????? ??????
//        Log.d(TAG, "siren login alert");
//        Log.d(TAG,"storename : " + storename);
//        //?????? ????????? ????????? ????????? ??????x
//        for(String store:storelist){
////          Log.d(TAG ,"RESINFOFRAG2:for??? storename: "+store);
//            if(storename.equals(store) == true){
////              Log.d(TAG ,"RESINFOFRAG2:for??? if??? ??????");
//                checkstore=1;
//                break;
//            }
//        }
//        if (menu.equals("MENU_IS_NULL")|checkstore==0) {
//            MyUtil.AlertShow(RestaurantInfoActivity.this, "????????? ?????? ???????????? ????????????.");
//        } else {
//
//            if (logincheck.equals("a") == true) {
//                builder.setTitle("????????? ??????").setMessage("????????? ?????? ?????????????????????. ???????????? ??????????????????.");
//                builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        //????????? ???????????? ????????????
//                        Intent intent = new Intent(RestaurantInfoActivity.this, LoginActivity.class);
////                    intent.putExtra("??????????????????",storenumber);
////                    intent.putExtra("?????????",???);
//                        startActivity(intent);
////                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(getApplicationContext(), "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
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
    //???????????? ????????? ????????? ???????????? ????????? ?????????
    public void onReserveBtnClicked(View v){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        ???????????? ??????????????? ?????? ?????? ??????
        if(logincheck.equals("a") == true)
        {
            builder.setTitle("????????? ??????").setMessage("????????? ?????? ?????????????????????. ???????????? ??????????????????.");
            builder.setPositiveButton("?????????", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    //????????? ???????????? ????????????
                    Intent intent = new Intent(RestaurantInfoActivity.this, LoginActivity.class);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            Log.d(TAG,"logincheck==1");
            builder.setTitle("????????????").setMessage(storename + " ????????? ?????????????????????????");
            builder.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //?????????????????? ????????????
                    Intent intent = new Intent(context_main, ReservationActivity.class);
                    intent.putExtra("??????????????????", storenumber);
                    intent.putExtra("?????????", storename);
                    context_main.startActivity(intent);
                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getApplicationContext(), "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //???????????????
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
//                Log.d(TAG,"??????" + String.valueOf(distance));
//
//                //?????? : ??????
//                if(distance<0){
//                    MyUtil.AlertShow(RestaurantInfoActivity.this, "????????? ?????? ???????????? ??? ????????????.");
//                }else{
//
//                    Intent intent = new Intent(RestaurantInfoActivity.this, SirenOrder.class);
//                    Log.d(TAG, "?????? ??????" + String.valueOf(latitude) + longitude);
//                    intent.putExtra("r_name", storename);
//                    intent.putExtra("r_menu", menu);
//                    intent.putExtra("r_adress1",storeaddress); //address2 ?????????
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