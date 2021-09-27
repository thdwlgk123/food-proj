package com.study.android.a4thteamproject01.mypage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.study.android.a4thteamproject01.R;
import com.study.android.a4thteamproject01.hyungdoo.MyUtil;
import com.study.android.a4thteamproject01.hyungdoo.SirenOrder;
import com.study.android.a4thteamproject01.hyungdoo.SirenOrderResult;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static java.nio.charset.StandardCharsets.UTF_8;

public class JoinActivity extends AppCompatActivity {
    private final static String TAG = "Join";
    private final static String TAG2 = "lecture";

    RetrofitMember mRetrofit;

//    List<UserDto> userDtos;
    int checkid=1;
    int checkpwd =1;
    int checkNickName =1;
    Map<String, String> map = new HashMap<>();
    //회원가입
    EditText c_name, c_id, c_pw, checkPwd, c_phone, c_eMaill, nickname;
    //비밀번호 메세지
    TextView pw_message, pw_message2, joinMessage;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        c_name = findViewById(R.id.name);
        c_id = findViewById(R.id.c_id);
        c_pw = findViewById(R.id.c_pw);
        checkPwd = findViewById(R.id.checkPwd);
        c_phone = findViewById(R.id.c_phone);
        c_eMaill = findViewById(R.id.c_eMail);
        nickname = findViewById(R.id.nickname);

        pw_message = findViewById(R.id.pw_message);
        pw_message2 = findViewById(R.id.pw_message2);
        joinMessage = findViewById(R.id.joinErrorMessage);
        pw_message.setVisibility(View.GONE);
        pw_message2.setVisibility(View.GONE);

        pwCheckMessage();
        phoneTextWatcher();
    }
    public void phoneTextWatcher(){

        c_phone.addTextChangedListener(new TextWatcher() {
            String before_text;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                before_text = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > before_text.length())
                if(s.toString().length() == 3){
                    c_phone.setText(s.toString() + "-");
                    c_phone.setSelection(c_phone.getText().length());
                } else if(s.toString().length() == 8){
                    c_phone.setText(s.toString() + "-");
                    c_phone.setSelection(c_phone.getText().length());
                } else if(s.toString().length() == 14){
                    c_phone.setText(before_text);
                    c_phone.setSelection(c_phone.getText().length());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void pwCheckMessage(){
        c_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", c_pw.getText().toString()))
                {
                    pw_message.setVisibility(View.VISIBLE);
                    pw_message.setText("특수문자, 숫자, 대소문자를 넣어 8~20자 로 만들어주세요.");
                    checkpwd =1;
                } else {
                    pw_message.setText("");
                    checkpwd =0;
                    pw_message.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        checkPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!c_pw.getText().toString().equals(checkPwd.getText().toString())){
                    pw_message2.setVisibility(View.VISIBLE);
                    pw_message2.setText("비밀번호가 같지 않습니다.");
                    checkpwd=1;
                }else{
                    pw_message2.setText("");
                    checkpwd=0;
                    pw_message2.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void left_back(View v) {
        super.onBackPressed();
    }

    public void btn_checkId(View v) {
        checkId checkId2 = new checkId();
        checkId2.execute();

    }

    public void btn_checkNickName(View v) {
        checkNickName checkNickName2 = new checkNickName();
        checkNickName2.execute();

    }



    public void btn_write(View v) throws NoSuchAlgorithmException {
        if (checkid == 1 || checkpwd == 1 || checkNickName == 1) {
            joinMessage.setText("중복체크 또는 비밀번호를 확인해 해주세요");
        }  else{

            mRetrofit = new RetrofitMember();
            Log.d(TAG, c_id.getText().toString());

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(c_pw.getText().toString().getBytes(UTF_8));
            byte[] bytes = md.digest();
            String password = String.format("%64x", new BigInteger(1, bytes));


            map.put("member_name", c_name.getText().toString());
            map.put("member_id", c_id.getText().toString());
            map.put("member_pw", password);
            map.put("member_phone", c_phone.getText().toString());
            map.put("member_email", c_eMaill.getText().toString());
            map.put("member_nickname", nickname.getText().toString());
            SharedPreferences pref = getSharedPreferences("token", Activity.MODE_PRIVATE);
            Log.d(TAG, "token : " + pref.getString("token",""));
            map.put("token",pref.getString("token",""));

            Log.d(TAG, map.toString());

            mRetrofit.mService.postJoinData((HashMap<String, String>) map).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
//                Log.d(TAG, String.valueOf(response.body().intValue()));
                    if (response.isSuccessful()) {
                        if (response.body() == 1) {
                            Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse:실패");
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse:실패");
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }

    public class checkId extends AsyncTask<Void, Void, String> {
        ProgressDialog customCircleProgressDialog = new ProgressDialog(JoinActivity.this);
        String url = getString(R.string.ipv4)+ "android/checkId";
        String result;


        @Override
        protected void onPreExecute() {
            customCircleProgressDialog.dismiss();
            customCircleProgressDialog.setMessage("화면 준비 중....");
            customCircleProgressDialog.setCancelable(false);
            customCircleProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
            customCircleProgressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void ...voids) {
            OkHttpClient client = new OkHttpClient();
            Log.d(TAG2,c_id.getText().toString());
            RequestBody body = new FormBody.Builder().add("member_id",c_id.getText().toString()).build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (okhttp3.Response response = client.newCall(request).execute()) {
                result = response.body().string();
                Log.d(TAG2,result+"1111111");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG2, s);

            customCircleProgressDialog.dismiss();
            try {
                if(s.equals("0")){
                    checkid=0;
                    MyUtil.AlertShow(JoinActivity.this, "사용 가능한 아이디입니다.");
                }else{
                    checkid=1;
                    MyUtil.AlertShow(JoinActivity.this, "사용 중인 아이디입니다.");
                }




            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public class checkNickName extends AsyncTask<Void, Void, String> {
        ProgressDialog customCircleProgressDialog = new ProgressDialog(JoinActivity.this);
        String url = getString(R.string.ipv4)+ "android/checkNickName";
        String result;


        @Override
        protected void onPreExecute() {
            customCircleProgressDialog.dismiss();
            customCircleProgressDialog.setMessage("화면 준비 중....");
            customCircleProgressDialog.setCancelable(false);
            customCircleProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
            customCircleProgressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void ...voids) {
            OkHttpClient client = new OkHttpClient();
            Log.d(TAG2,c_id.getText().toString());
            RequestBody body = new FormBody.Builder().add("member_nickname",nickname.getText().toString()).build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (okhttp3.Response response = client.newCall(request).execute()) {
                result = response.body().string();
                Log.d(TAG2,result+"1111111");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG2, s);

            customCircleProgressDialog.dismiss();
            try {
                if(s.equals("0")){
                    checkNickName=0;
                    MyUtil.AlertShow(JoinActivity.this, "사용 가능한 닉네임입니다.");
                }else{
                    checkNickName=1;
                    MyUtil.AlertShow(JoinActivity.this, "사용 중인 닉네임입니다.");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //현재 등록 토큰 가져오기
    public String getRegistrationId() {
        final String[] token = new String[1];
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
                        token[0] = task.getResult();


                    }

                });
        Log.d(TAG, "RegID:"+ token[0]);
        return token[0];
    }

}