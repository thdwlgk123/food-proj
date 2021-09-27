package com.study.android.a4thteamproject01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class IntroActivity extends AppCompatActivity {
    private static final String TAG="lecture";
    private static final int STOPSPLASH=0;
    private static final long SPLASHTIME=2000;

    private final Handler splashHandler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            Intent intent;

            switch(msg.what){
                case STOPSPLASH:
                    intent=new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String id = pref.getString("id", "");
        String numAutoLogin = pref.getString("autoLogin", "a");
        String numEnabled = pref.getString("enabled", "a");
        Log.d(TAG, "id = " + id);
        Log.d(TAG, "autoLogin = " + numAutoLogin);
        Log.d(TAG, "enabled = " + numEnabled);


        if (numAutoLogin.equals("a") == true) {
            editor.clear();
            editor.commit();
        }
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {

            for (String key : intent.getExtras().keySet()) {
                String value = intent.getExtras().getString(key);
                Log.d(TAG, "Noti - " + key + ":" + value);
            }
            Log.d(TAG, "알림 메시지가 있어요");
            // 알림을 클릭해서 앱이 실행되었을 때 메시지를 표시한다.
            // 알림 클릭 없이 앱을 바로 실행
            // -- 데이터가 표시되지 않는다.
            // -- 시스템 알림에 알림은 그대로 남아 있다.
            //String contents = intent.getExtras().getString("message");
            String contents = intent.getStringExtra("message");
            if ( contents != null ) {
                processIntent(contents);
            }
        }
        getRegistrationId();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            String contents = intent.getStringExtra("message");
            processIntent(contents);
        }
    }

    private void processIntent(String contents) {
        Log.d(TAG, "["+contents+"]");

    }





    @Override
    protected void onResume(){
        super.onResume();

        Message msg=new Message();
        msg.what=STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }
    //현재 등록 토큰 가져오기
    public void getRegistrationId() {
        Log.d(TAG, "getRegistrationId() 호출됨.");


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        SharedPreferences pref = getSharedPreferences("token", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("token",token);
                        editor.commit();
                        Log.d(TAG, "RegID:"+token);

                    }
                });
    }

}