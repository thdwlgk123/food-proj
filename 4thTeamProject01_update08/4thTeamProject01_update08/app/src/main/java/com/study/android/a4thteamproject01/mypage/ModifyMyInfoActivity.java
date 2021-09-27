package com.study.android.a4thteamproject01.mypage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ModifyMyInfoActivity extends AppCompatActivity {
    private final static String TAG = "Modify";

    TextView tv_name;
    TextView tv_id;
    TextView tv_error;
    EditText et_pwd;
    EditText et_checkPwd;
    EditText et_phone;
    EditText et_eMail;
    UserDto item;
    Toolbar modifyInfo;

    RetrofitMember mRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_my_info);

        tv_name = findViewById(R.id.tv_modName);
        tv_id = findViewById(R.id.tv_modId);
        tv_error = findViewById(R.id.tv_error);
        et_pwd = findViewById(R.id.et_modPwd);
        et_checkPwd = findViewById(R.id.et_modPwdChk);
        et_phone = findViewById(R.id.et_modPhone);
        et_eMail = findViewById(R.id.et_modeMail);

        mRetrofit = new RetrofitMember();
        modifyInfo = findViewById(R.id.modifyInfo);
        setSupportActionBar(modifyInfo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        String numAutoLogin = pref.getString("autoLogin", "");
        String numEnabled = pref.getString("enabled", "");

        tv_error.setVisibility(View.GONE);

        if (numEnabled.equals("b")) {
            mRetrofit.mService.getModifyProfile(id).enqueue(new Callback<JSONObjectResult2>() {
                @Override
                public void onResponse(Call<JSONObjectResult2> call, Response<JSONObjectResult2> response) {
                    if (response.isSuccessful()) {
                        JSONObjectResult2 profile = response.body();

                        for(UserDto data:profile.mod_data){
                            UserDto item=new UserDto(data.getC_name(), data.getC_id(), data.getC_phone(),
                                    data.getC_email());
                            tv_name.setText(item.getC_name());
                            tv_id.setText(item.getC_id());
                            et_phone.setText(item.getC_phone());
                            et_eMail.setText(item.getC_email());
                        }

                    }
                }

                @Override
                public void onFailure(Call<JSONObjectResult2> call, Throwable t) {

                }
            });
        }

    }

    public void onClick_success(View v) throws NoSuchAlgorithmException {
        Log.d(TAG, et_pwd.getText().toString());
        Log.d(TAG, et_checkPwd.getText().toString());
        if ((et_pwd.getText().toString().equals(et_checkPwd.getText().toString())) == false) {
            tv_error.setText("* 비밀번호가 일치하지 않습니다.");
            tv_error.setVisibility(View.VISIBLE);
            et_pwd.findFocus();
        } else {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(et_pwd.getText().toString().getBytes(UTF_8));
            byte[] bytes = md.digest();
            String password = String.format("%64x", new BigInteger(1, bytes));

            Map<String, String> map = new HashMap<>();
            map.put("c_name", tv_name.getText().toString());
            map.put("c_id", tv_id.getText().toString());
            map.put("c_pw", password);
            map.put("c_phone", et_phone.getText().toString());
            map.put("c_email", et_eMail.getText().toString());
            Log.d(TAG, map.toString());

            mRetrofit.mService.setMyProfile((HashMap<String, String>) map).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        if (response.body() == 1) {
                            Toast.makeText(getApplicationContext(), "내 정보 수정 완료", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "수정 실패", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "서버에 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void left_back(View v) {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);

        }
    }
}