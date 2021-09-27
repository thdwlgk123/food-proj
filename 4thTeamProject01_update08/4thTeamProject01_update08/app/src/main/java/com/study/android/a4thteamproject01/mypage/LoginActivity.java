package com.study.android.a4thteamproject01.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.study.android.a4thteamproject01.MainActivity;
import com.study.android.a4thteamproject01.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1001;
    private static final String TAG = "Login";

    EditText c_id;
    EditText c_pw;
    TextView loginFailed;
    CheckBox autoLogin;
    SharedPreferences.Editor editor;
    RetrofitMember mRetrofit;

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        autoLogin = findViewById(R.id.auto_login);

        // 자동로그인 체크
        autoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoLogin.isChecked()==true) {
                    Toast.makeText(getApplicationContext(), "자동로그인on", Toast.LENGTH_SHORT).show();
                } else if(autoLogin.isChecked()==false){
                    Toast.makeText(getApplicationContext(), "자동로그인off", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void left_back(View v) {
        super.onBackPressed();
    }

    // 로그인 버튼
    public void btn_login(View v) throws NoSuchAlgorithmException {
        c_id = findViewById(R.id.c_id);
        c_pw = findViewById(R.id.c_pw);
        loginFailed = findViewById(R.id.tv_loginfailed);
        autoLogin = findViewById(R.id.auto_login);
        mRetrofit = new RetrofitMember();

        if (autoLogin.isChecked() == true) {
            SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
            editor = pref.edit();
            String sId = c_id.getText().toString();
            String sEnabled = "b";
            String autoLogin = "b";

            editor.putString("id", sId);
            editor.putString("autoLogin", autoLogin);
            editor.putString("enabled", sEnabled);
            editor.commit();
        } else if (autoLogin.isChecked() == false) {
            SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
            editor = pref.edit();
            String sId = c_id.getText().toString();
            String sEnabled = "b";
            String autoLogin = "a";

            editor.putString("id", sId);
            editor.putString("autoLogin", autoLogin);
            editor.putString("enabled", sEnabled);
            editor.commit();

            String id = pref.getString("id", "");
            String numAutoLogin = pref.getString("autoLogin", "");
            String numEnabled = pref.getString("enabled", "");
            Log.d(TAG, "id = " + id);
            Log.d(TAG, "autoLogin = " + numAutoLogin);
            Log.d(TAG, "enabled = " + numEnabled);
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(c_pw.getText().toString().getBytes(UTF_8));
        byte[] bytes = md.digest();
        String password = String.format("%64x", new BigInteger(1, bytes));

        mRetrofit.mService.postLoginData(c_id.getText().toString(), password).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    if(response.body() == 1) {
                        Toast.makeText(getApplication(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "로그인 성공");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        loginFailed.setVisibility(View.VISIBLE);
                    }
                }else{
                    Log.d(TAG, "onResponse:실패");
                    loginFailed.setText("서버연결 실패");
                    loginFailed.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public void btnClick(View v) {
        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
        startActivity(intent);
//        startActivityForResult(intent, 1);
    }
    public void idCheck(View v) {

    }

}