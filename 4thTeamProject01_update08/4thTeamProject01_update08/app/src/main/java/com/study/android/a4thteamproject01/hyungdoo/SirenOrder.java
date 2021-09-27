package com.study.android.a4thteamproject01.hyungdoo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.android.a4thteamproject01.R;
import com.study.android.a4thteamproject01.RestaurantInfoActivity;
import com.study.android.a4thteamproject01.hyungdoo.sirenList.SingerAdapter2;
import com.study.android.a4thteamproject01.hyungdoo.sirenList.SingerItem2;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SirenOrder extends AppCompatActivity {

    private static final String TAG = "lecture";
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private int stuck = 10;

    String r_name, r_menu, r_adress1, m_number,user_id,c_index;

    SharedPreferences.Editor editor;
    StringBuffer menu_csv;
    //메뉴 리스트 어댑터
    SingerAdapter2 adapter;

    LinearLayout forCartLayout;
    LinearLayout userLayout;

    //상품주의 사항
    ImageView contentButton;  LinearLayout contentLayout;
    EditText request;
    TextView cart_price; int total_price = 0;

    TextView userName, userPhone;

    int buttonSign = 0;

    //총 금액
    int payment;
    Thread thread;
    Runnable r;
    String url;
    String json;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sirenorder);
        // 초기설정 - 해당 프로젝트(안드로이드)의 application id 값을 설정합니다. 결제와 통계를 위해 꼭 필요합니다.
        BootpayAnalytics.init(this, "610a268e019943002dc69183");
        SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor = pref.edit();

        contentButton = findViewById(R.id.contentButton);
        contentLayout = findViewById(R.id.contentLayout);
        contentLayout.setVisibility(View.GONE);

        userLayout = findViewById(R.id.customerLayout);
        userName = findViewById(R.id.name_value);
        userPhone = findViewById(R.id.phoneNumber_value);

        contentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(buttonSign==0){
                    contentLayout.setVisibility(View.VISIBLE);
                    buttonSign=1;
                }else{
                    contentLayout.setVisibility(View.GONE);
                    buttonSign=0;
                }

            }
        });

        ScrollView scrollView = findViewById(R.id.contentScroll);
        ScrollView scrollView2 = findViewById(R.id.contentScroll2);



        scrollView2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    scrollView.requestDisallowInterceptTouchEvent(false);
                }else{
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }

                return false;
            }
        });


        forCartLayout = findViewById(R.id.forCartLayout);
        request = findViewById(R.id.requestText);
        cart_price = findViewById(R.id.total_price);
        String enabled = pref.getString("enabled", "");

//            userLayout.setVisibility(View.GONE);
//            cart_price.setText("로그인을 해주세요");
//            cart_price.setBackground(getDrawable(R.drawable.pay_border_off));
//            cart_price.setTextColor(Color.parseColor("#FF737373"));

            user_id = pref.getString("id", "");
            url = getString(R.string.ipv4) + "android/getUserInfo";
            Log.d(TAG, user_id + " : " + url);
            NetworkTask1 test = new NetworkTask1(url, user_id);
            test.execute();



        Intent intent = getIntent();
       r_name =  intent.getStringExtra("r_name");
       r_menu = intent.getStringExtra("r_menu");
       r_adress1 = intent.getStringExtra("r_adress1");
       m_number = intent.getStringExtra("m_number");
        adapter = new SingerAdapter2(this);
        StringTokenizer st_Formenu = new StringTokenizer(r_menu,",");

        while(st_Formenu.hasMoreTokens()){
            String token_test = st_Formenu.nextToken();
            Log.d(TAG,token_test);
            StringTokenizer st_Forprice = new StringTokenizer( token_test,":");
            while (st_Forprice.hasMoreTokens()){
                String token_test2 = st_Forprice.nextToken();
                String token_test3 = st_Forprice.nextToken();
                Log.d(TAG,token_test2 +" : " + token_test3);
                SingerItem2 item = new SingerItem2(token_test2, token_test3 + "원","0");
                adapter.addItem(item);
            }
        }

       RecyclerView listView1 = findViewById(R.id.menu_listView);
       listView1.setAdapter(adapter);

        adapter.setItemClick(new SingerAdapter2.ItemClick() {
            @Override
            public void onClick1(View view, int position) {
                SingerItem2 item = (SingerItem2) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "selected : " + item.getMenu_price(), Toast.LENGTH_SHORT).show();
                String sub_menu_price = item.getMenu_price();
                int j = sub_menu_price.indexOf("원");
                sub_menu_price = sub_menu_price.substring(0,j);
                Log.d(TAG, sub_menu_price);
                payment = total_price + Integer.parseInt(sub_menu_price);

                cart_price.setText(payment + "원 주문하기");
                Log.d(TAG, String.valueOf(payment));
                total_price = payment;

            }
        });
        adapter.setItemClick2(new SingerAdapter2.ItemClick2() {
            @Override
            public void onClick2(View view, int position) {
                SingerItem2 item = (SingerItem2) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "selected : " + item.getMenu_price(), Toast.LENGTH_SHORT).show();
                String sub_menu_price = item.getMenu_price();
                int j = sub_menu_price.indexOf("원");
                sub_menu_price = sub_menu_price.substring(0,j);
                Log.d(TAG, sub_menu_price);
                payment = total_price - Integer.parseInt(sub_menu_price);

                cart_price.setText(payment + "원 주문하기");
                Log.d(TAG, String.valueOf(payment));
                total_price = payment;


            }
        });



        listView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true));
        listView1.scrollToPosition(adapter.getItemCount() -1);
        listView1.setItemAnimator(new DefaultItemAnimator());


        listView1.getAdapter().notifyDataSetChanged();

       cart_price.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onClick_request();
           }
       });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SirenOrder.this, RestaurantInfoActivity.class);
        intent.putExtra("매장명",r_name);
        intent.putExtra("업소관리번",m_number);
        startActivity(intent);
        finish();
    }

    public void onClick_request() {
        menu_csv = new StringBuffer();
        String request_data = "없음";
        int sum = 0;
        for(int j=0; j<adapter.getItemCount(); j++){
            Log.d(TAG, String.valueOf(adapter.getItemCount()));
            SingerItem2 item = (SingerItem2) adapter.getItem(j);
            sum += Integer.parseInt(item.getMenu_count());
        }
        if(sum == 0){
            MyUtil.AlertShow(SirenOrder.this, "메뉴를 선택해주세요.");
        } else {
            for(int j=0; j<adapter.getItemCount(); j++){
                SingerItem2 item = (SingerItem2) adapter.getItem(j);
                if(Integer.parseInt(item.getMenu_count())>0){
                    menu_csv.append(item.getMenu_name() +  "," + item.getMenu_count() + "  ");
                    Log.d(TAG, String.valueOf(menu_csv).trim());

                }
                if(request.getText().toString().length()==0){
                    request_data = "없음";
                }else{
                    request_data = request.getText().toString();
                }
            }
            long time = System.currentTimeMillis();
            DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String r_rsvnum = format.format(time) + c_index;
            Log.d(TAG, "r_rsvnum : " +  format.format(time) + c_index);
            json =   "{\"m_number\":\"" +m_number+"\","
                    + "\"r_name\":\"" +r_name+"\","
                    + "\"c_id\":\""+user_id+"\","    //유저아이디
                    + "\"c_name\":\""+ userName.getText().toString()+"\"," //유저이름
                    + "\"c_phone\":\""+userPhone.getText().toString()+"\","
                    + "\"res_payment\":\""+payment+"\","
                    + "\"r_rsvnum\":\""+r_rsvnum+"\","
                    + "\"r_menu\":\"" +String.valueOf(menu_csv).trim()+"\","
                    + "\"request\":\"" +request_data+"\"}"
                    ;
            forBootPay();
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        ProgressDialog customCircleProgressDialog = new ProgressDialog(SirenOrder.this);
        String url;
        String result;
        public NetworkTask(String url) {
            this.url = url;
        }

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
            RequestBody body = new FormBody.Builder().add("json",json).build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
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
            Log.d(TAG, s);
            try {
                customCircleProgressDialog.dismiss();
                Intent intent = new Intent(SirenOrder.this, SirenOrderResult.class);
                intent.putExtra("menu",String.valueOf(menu_csv).trim());
                intent.putExtra("address",r_adress1);
                startActivity(intent);
                finish();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public class NetworkTask1 extends AsyncTask<Void, Void, String> {
        ProgressDialog customCircleProgressDialog = new ProgressDialog(SirenOrder.this);
        String result;
        String test;
        String test1;


        public NetworkTask1(String url, String user_id) {
            this.test = url;
            this.test1 = user_id;
        }

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
            RequestBody body = new FormBody.Builder().add("c_id", test1)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                result = response.body().string();

            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
            @Override
            protected void onPostExecute (String s){

                Log.d(TAG, "Userinfo 결과 : " + s);
                try {
                    jsonObject = new JSONObject(s);
                    jsonObject = jsonObject.getJSONObject("userInfo");
                    userName.setText(jsonObject.getString("c_name"));
                    userPhone.setText(jsonObject.getString("c_phone"));
                    c_index = jsonObject.getString("c_index");
                    customCircleProgressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }





    public void forBootPay() {
        // 결제호출
        BootUser bootUser = new BootUser().setPhone("010-5918-3963");
        BootExtra bootExtra = new BootExtra().setQuotas(new int[]{0, 2, 3});

        Bootpay.init(getFragmentManager())
                .setApplicationId("610a268e019943002dc69183") // 해당 프로젝트(안드로이드)의 application id 값
                .setPG(PG.NICEPAY) // 결제할 PG 사
                .setMethod(Method.KAKAO) // 결제수단
                .setContext(this)
                .setBootUser(bootUser)
                .setBootExtra(bootExtra)
                .setUX(UX.PG_DIALOG)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                .setName(r_name) // 결제할 상품명
                .setOrderId("1234") // 결제 고유번호expire_month
                .setPrice(payment) // 결제할 금액
                .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                    @Override
                    public void onConfirm(@Nullable String message) {

                        if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                        else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                        Log.d(TAG, "Confirm : " + message);
                    }
                })
                .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                    @Override
                    public void onDone(@Nullable String message) {

                        Log.d(TAG, "DONE : " + message);
                        url = getString(R.string.ipv4) + "android/sirenOrder";
                        NetworkTask networktask = new NetworkTask(url);
                        networktask.execute();
                    }
                })
                .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                    @Override
                    public void onReady(@Nullable String message) {
                        Log.d(TAG, "Ready : " + message);
                    }
                })
                .onCancel(new CancelListener() { // 결제 취소시 호출
                    @Override
                    public void onCancel(@Nullable String message) {

                        Log.d(TAG, "Cancel : " + message);
                    }
                })
                .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                    @Override
                    public void onError(@Nullable String message) {
                        Log.d(TAG, "Error : " + message);
                    }
                })
                .onClose(
                        new CloseListener() { //결제창이 닫힐때 실행되는 부분
                            @Override
                            public void onClose(String message) {
                                Log.d(TAG, "close");
                            }
                        })
                .request();


    }

}
