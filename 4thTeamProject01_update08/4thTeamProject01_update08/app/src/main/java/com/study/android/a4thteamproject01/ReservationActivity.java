package com.study.android.a4thteamproject01;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.study.android.a4thteamproject01.mypage.UserDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends AppCompatActivity {
    private static final String TAG="lecture";
    public static Context context_main;
    RetrofitMain retroservice;
    SharedPreferences.Editor editor;

    private String storename;
    private String storenumber;
    private String checktime="";
    private String id;
    private String c_index;
    private String r_rsvnum;
    private int count;
    private int checkpay;
    private Button[] mbtn;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textview6;
    TextView textview7;
    TextView minusbtn;
    TextView plusbtn;
    EditText editText;
    Button doReserve;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15;
    Button btn16, btn17, btn18, btn19, btn20;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        context_main=this;
        retroservice=new RetrofitMain();

        count=0;
        textView1=findViewById(R.id.setdate);
        textView2=findViewById(R.id.settime);
        textView3=findViewById(R.id.textView8);
        textView4=findViewById(R.id.personnum);
        textView5=findViewById(R.id.textView13);
        textview6=findViewById(R.id.reserver);
        textview7=findViewById(R.id.reserverphone);
        editText=findViewById(R.id.editText);
        minusbtn=findViewById(R.id.minusbtn);
        plusbtn=findViewById(R.id.plusbtn);
        doReserve=findViewById(R.id.button8);

        btn1=findViewById(R.id.time1);
        btn2=findViewById(R.id.time2);
        btn3=findViewById(R.id.time3);
        btn4=findViewById(R.id.time4);
        btn5=findViewById(R.id.time5);
        btn6=findViewById(R.id.time6);
        btn7=findViewById(R.id.time7);
        btn8=findViewById(R.id.time8);
        btn9=findViewById(R.id.time9);
        btn10=findViewById(R.id.time10);
        btn11=findViewById(R.id.time11);
        btn12=findViewById(R.id.time12);
        btn13=findViewById(R.id.time13);
        btn14=findViewById(R.id.time14);
        btn15=findViewById(R.id.time15);
        btn16=findViewById(R.id.time16);
        btn17=findViewById(R.id.time17);
        btn18=findViewById(R.id.time18);
        btn19=findViewById(R.id.time19);
        btn20=findViewById(R.id.time20);

        mbtn=new Button[20];
        mbtn[0]=btn1;
        mbtn[1]=btn2;
        mbtn[2]=btn3;
        mbtn[3]=btn4;
        mbtn[4]=btn5;
        mbtn[5]=btn6;
        mbtn[6]=btn7;
        mbtn[7]=btn8;
        mbtn[8]=btn9;
        mbtn[9]=btn10;
        mbtn[10]=btn11;
        mbtn[11]=btn12;
        mbtn[12]=btn13;
        mbtn[13]=btn14;
        mbtn[14]=btn15;
        mbtn[15]=btn16;
        mbtn[16]=btn17;
        mbtn[17]=btn18;
        mbtn[18]=btn19;
        mbtn[19]=btn20;

        //activity에서 받아온 값
        Intent intent=getIntent();
        storename=intent.getStringExtra("매장명");
        storenumber=intent.getStringExtra("업소관리번호");
        Log.d(TAG, "매장명:"+storename+"업소번호:"+storenumber);

        SharedPreferences pref=getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor=pref.edit();
        id=pref.getString("id","");
        Log.d(TAG, "SHAREDPRE ID: "+id);
        //로그인 된 아이디로 db에서 회원정보 가져옴.
        getMemberInfo();
        textView3.setText(storename+" 예약하기");
        textView4.setText(count+" ");

        BootpayAnalytics.init(context_main, "610e516d7b5ba4001f529947");

        this.onSetListener();
        //인원수 -버튼
        minusbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(count==0){
//                    showAlert("","인원은 0명 미만일 수 없습니다.");
                }else{
                    count--;
                    textView4.setText(count+" ");
                }

            }
        });
        //인원수 +버튼
        plusbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(count>8){
                    showAlert("예약 인원 제한","예약 인원은 10명 이상이 될 수 없습니다.");
                }else{
                    count++;
                    textView4.setText(count+" ");
                }

            }
        });
        //예약하기 버튼
        doReserve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG,"onclick button");
                Log.d(TAG,"testview1 : "+textView1.getText());
                if(((String)textView1.getText()).equals(null)||((String)textView1.getText()).equals("")){
                    Log.d(TAG, "예약 날짜 미선택");
                    showAlert("예약 정보 미입력","예약 날짜를 입력하세요");
                }
                if (checktime.equals(null)||checktime.equals("")){
                    Log.d(TAG, "예약 시간 미선택");
                    showAlert("예약 정보 미입력","예약 시간을 입력하세요");
                }
                if (count==0){
                    Log.d(TAG, "예약 인원 미선택");
                    showAlert("예약 정보 미입력","예약 인원을 입력하세요");
                }else{
                    Log.d(TAG, "결제를 진행합니다.");
                    //결제 진행
                    showAlert("예약 내용 확인","해당 내용으로 예약금 결제를 진행하시겠습니까?");
                }
            }
        });
    }

    public void onDateBtnClicked(View v){
        final Calendar c= Calendar.getInstance();
        int mYear=c.get(Calendar.YEAR);
        int mMonth=c.get(Calendar.MONTH);
        int mDay=c.get(Calendar.DAY_OF_MONTH);
        long now=System.currentTimeMillis()-1000;

        DatePickerDialog datePickerDialog=new DatePickerDialog(this,
        new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
//                if(year<2021){
//                    showAlert("지난 해를 선택할 수 없습니다.");
//                }else if(year==2021 && month<mMonth){
//                    showAlert("오늘 ");
//                }
                textView1.setText(year+"/ "+(month+1)+" / "+dayOfMonth);
                Log.d(TAG, "textview1 시간: "+textView1.getText());
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.getDatePicker().setMaxDate(now+(long)(1000*60*60*24*60));
        datePickerDialog.setTitle("예약 날짜 선택");
        datePickerDialog.show();
    }

//    public void onTimeBtnClicked(View v){
//        final Calendar c= Calendar.getInstance();
//        int hour=c.get(Calendar.HOUR_OF_DAY);
//        int minute=c.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog=new TimePickerDialog(context_main,
//                android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                if (view.isShown()) {
//                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                    c.set(Calendar.MINUTE, minute);
//                }
//                textView2.setText(hourOfDay+" : "+minute);
//            }
//        }, hour, minute, false);
//        timePickerDialog.setTitle("예약 시간 선택");
//        timePickerDialog.show();
//
//    }

    public void onSetListener(){
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
//                Button button=(Button) view;
//                button.setBackgroundColor(Color.parseColor("#EFD587"));
                String gettime= (String) ((Button) view).getText();
                Log.d(TAG,"gettime : "+gettime);
                if(("").equals(checktime)){
                    checktime=gettime;
                    textView2.setText(checktime);
                    view.setBackgroundColor(Color.GRAY);
                    Log.d(TAG ,"checktime : "+checktime);
                }else{
                    if(gettime.equals(checktime)){
                        checktime="";
                        textView2.setText("(시간을 선택하세요)");
                        view.setBackgroundColor(Color.parseColor("#EFD587"));
                        Log.d(TAG ,"checktime : "+checktime);
                    }else{
                        showAlert("중복 선택 오류","시간을 중복선택할 수 없습니다.");
                    }
                }
            }
        };
//        btn1.setOnClickListener(Listener);
        for(Button btn:mbtn){
            btn.setOnClickListener(Listener);
        }

    }
    //부트페이 결제 진행
    public void BootPay(String phone, String reserver, int count) {
//        Log.d(TAG, "START APP22");
        // 결제호출
        BootUser bootUser = new BootUser().setPhone(phone);
        BootExtra bootExtra = new BootExtra().setQuotas(new int[] {0,2,3});

        Bootpay.init(getFragmentManager())
                .setApplicationId("610e516d7b5ba4001f529947") // 해당 프로젝트(안드로이드)의 application id 값
//                .setPG(PG.INICIS) // 결제할 PG 사
//                .setMethod(Method.PHONE) // 결제수단
                .setContext(context_main)
                .setBootUser(bootUser)
                .setBootExtra(bootExtra)
                .setUX(UX.PG_DIALOG)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                .setName(reserver) // 예약자명
                .setOrderId("1234") // 결제 고유번호expire_month
                .setPrice(1000) // 결제할 금액
                .addItem(storename, 1, storenumber, 1000) // 주문정보에 담길 상품정보, 통계를 위해 사용
//                .addItem("키보드", 1, "ITEM_CODE_KEYBOARD", 200, "패션", "여성상의", "블라우스") // 주문정보에 담길 상품정보, 통계를 위해 사용
                .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                    @Override
                    public void onConfirm(@Nullable String message) {
                        Bootpay.confirm(message);
//                        if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
//                        else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                        Log.d(TAG, "CONFIRM"+message);
                    }
                })
                .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                    @Override
                    public void onDone(@Nullable String message) {
                        checkpay=1;
                        Log.d(TAG, "DONE"+message);
                        sendResult();
                    }
                })
                .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                    @Override
                    public void onReady(@Nullable String message) {
                        Log.d(TAG, "READY"+message);
                    }
                })
                .onCancel(new CancelListener() { // 결제 취소시 호출
                    @Override
                    public void onCancel(@Nullable String message) {

                        Log.d(TAG, "CANCLE " +message);
                    }
                })
                .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                    @Override
                    public void onError(@Nullable String message) {
                        checkpay=0;
                        Log.d(TAG, "ERROR"+message);
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
    public void sendResult(){
        Log.d(TAG,"sendResult ");

        //고유 예약번호 생성
            long time= System.currentTimeMillis();
            DateFormat format1=new SimpleDateFormat("yyyyMMddHHmmss");
            r_rsvnum=format1.format(time)+c_index;
            Log.d(TAG,"set 예약번호: "+r_rsvnum);

            HashMap<String, Object> reserveinfo=new HashMap<String, Object>();
            reserveinfo.put("member_id", id);
            reserveinfo.put("r_rsvnumber", r_rsvnum);
            reserveinfo.put("m_number", storenumber);
            reserveinfo.put("r_name",storename);
            reserveinfo.put("nickname", (String)textview6.getText());
            reserveinfo.put("c_phone",(String)textview7.getText());
            reserveinfo.put("b_party", count);
            reserveinfo.put("condition_check", 2);//2는 예약대기
            reserveinfo.put("res_payment",1);
            reserveinfo.put("tdate", (String)textView1.getText());
            reserveinfo.put("time",checktime);
            if(!(editText.getText().toString()).equals("")){
                Log.d(TAG, "예약자 요청사항: "+editText.getText().toString());
                reserveinfo.put("request",editText.getText().toString());
            }

            Log.d(TAG, "hashmap setting : "+reserveinfo);

            retroservice.service1.getInsertReserveInfo(reserveinfo).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful()){
                        Integer num= response.body();
                        Log.d(TAG, "예약결과 전송"+String.valueOf(num));
                    }else{
                        Log.d(TAG,"onFailure");
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t.getMessage());
                }
            });

            showAlert("알림","예약에 성공하였습니다.");

    }
    
    public void showAlert(String title, String msg){
        Log.d(TAG, "showalert 실행");
        AlertDialog.Builder builder=new AlertDialog.Builder(context_main);
        builder.setTitle(title).setMessage(msg);
        if(msg.equals("예약에 성공하였습니다.")){
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    // 예약 완료 화면으로 예약정보보 전달
                    Intent nextintent = new Intent(context_main, DoneReserveActivity.class);
                    nextintent.putExtra("예약번호",r_rsvnum);
//                    intent.putExtra("매장명",storename);
                    context_main.startActivity(nextintent);
                }
            });
        }else if(msg.equals("해당 내용으로 예약금 결제를 진행하시겠습니까?")){
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    BootPay((String)textview7.getText(),(String)textview6.getText(),count);
                    //sendResult();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                   Log.d(TAG ,"예약 취소버튼");
                }
            });
        }else{
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
//                                Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //로그인 id값으로 db에서 회원정보 가져오기.
    public void getMemberInfo(){
        retroservice.service1.getRsvMemInfo(id).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if(response.isSuccessful()){
                    UserDto user=response.body();
                    Log.d(TAG,"user: "+user.getC_name()+", "+user.getC_phone());
                    textview6.setText(user.getC_name()); //예약자이름
                    textview7.setText(user.getC_phone()); //예약자번호
                    c_index=Integer.toString(user.getC_index());
                    Log.d(TAG, "get db c_index toString: "+c_index);
                }else{
                    Log.d(TAG,"RESPONSE FAILE");
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }


}