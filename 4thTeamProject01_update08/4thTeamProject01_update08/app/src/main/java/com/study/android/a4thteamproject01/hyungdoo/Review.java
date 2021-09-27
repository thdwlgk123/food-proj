package com.study.android.a4thteamproject01.hyungdoo;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.study.android.a4thteamproject01.MainActivity;
import com.study.android.a4thteamproject01.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Review extends AppCompatActivity {
    private static final String TAG = "lecture";
    //점수
    ImageView star1, star2, star3, star4, star5;
    TextView score;
    //사진 표시하는 레이아웃
    LinearLayout getImage,selectedImage;
    //리뷰 내용
    EditText contents;
    String r_name, m_number,c_id,nickname;

    //파일 업로드
    String filePath = "";
    ImageView uploadImage;
    private static final int PICK_FROM_ALBUM = 2;

    //갤러리 허용
    private final String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static final int MULTIPLE_PERMISSIONS = 101;

    //매장명
    TextView rName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
        Intent intent = getIntent();
        r_name = intent.getStringExtra("r_name");
        m_number = intent.getStringExtra("m_number");
        nickname = intent.getStringExtra("nickname");
        Log.d(TAG, nickname);
        //회원아이디 가져오기
        c_id = pref.getString("id","");

        //툴바
        Toolbar tb = (Toolbar) findViewById(R.id.reviewToolbar);
        tb.setTitle("만족도 평가 및 리뷰");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbutton);

        // 매장명
        rName = findViewById(R.id.r_name);
        rName.setText(r_name);

        //점수
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        score = findViewById(R.id.reviewscore);

        //사진가져오기
        getImage = findViewById(R.id.getImage);
        uploadImage = findViewById(R.id.uploadImage);
        selectedImage = findViewById(R.id.seletedImage);
        contents = findViewById(R.id.contents);
        selectedImage.setVisibility(View.GONE);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.checked_review_star);
                star2.setImageResource(R.drawable.unchecked_review_star);
                star3.setImageResource(R.drawable.unchecked_review_star);
                star4.setImageResource(R.drawable.unchecked_review_star);
                star5.setImageResource(R.drawable.unchecked_review_star);
                score.setText("1");


            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.checked_review_star);
                star2.setImageResource(R.drawable.checked_review_star);
                star3.setImageResource(R.drawable.unchecked_review_star);
                star4.setImageResource(R.drawable.unchecked_review_star);
                star5.setImageResource(R.drawable.unchecked_review_star);
                score.setText("2");


            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.checked_review_star);
                star2.setImageResource(R.drawable.checked_review_star);
                star3.setImageResource(R.drawable.checked_review_star);
                star4.setImageResource(R.drawable.unchecked_review_star);
                star5.setImageResource(R.drawable.unchecked_review_star);
                score.setText("3");


            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.checked_review_star);
                star2.setImageResource(R.drawable.checked_review_star);
                star3.setImageResource(R.drawable.checked_review_star);
                star4.setImageResource(R.drawable.checked_review_star);
                star5.setImageResource(R.drawable.unchecked_review_star);
                score.setText("4");


            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.checked_review_star);
                star2.setImageResource(R.drawable.checked_review_star);
                star3.setImageResource(R.drawable.checked_review_star);
                star4.setImageResource(R.drawable.checked_review_star);
                star5.setImageResource(R.drawable.checked_review_star);
                score.setText("5");


            }
        });

        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //앨범에서 가져오기
                checkPermissions();
                getImage();
            }
        });
    }

    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
//                    permissionList.toArray(new String[permissionList.size()]),
                    permissionList.toArray(new String[0]),
                    MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reviewappbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "backButton", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Review.this, MainActivity.class);
//                intent.putExtra("latitude",latitude);
//                intent.putExtra("longitude",longitude);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void getImage() {
        //갤러리 리스트뷰 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 2) {
            if (resultCode == -1) {
                Uri selPhotoURi = intent.getData();
                showCapturedImage(selPhotoURi);
            }
        }
    }

    private void showCapturedImage(Uri imageUri) {
        // 절대경로를 획득한다!!! 중요~
        filePath = getRealPathFromURI(imageUri);
        Log.d(TAG, "path1:" + filePath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        selectedImage.setVisibility(View.VISIBLE);
        uploadImage.setImageBitmap(bitmap);
        uploadImage.invalidate();
//        Bitmap rotatedBitmap = rotate(bitmap, exifDegree);
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(rotatedBitmap, 800, 800, false);
//        bitmap.recycle();

        //경로를 통해 비트맵으로 전환

    }

    // 사진의 회전값을 처리하지 않으면 사진을 찍은 방향대로 이미지뷰에 처리되지 않는다.
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    // 사진을 정방향대로 회전하기
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    // 사진의 절대경로 구하기
    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    //인덱스 음식점이름 회원아이디 등록날짜 별점
    public void btn_submit(View v) {
        String url = getString(R.string.ipv4) + "android/review";
        NetworkTask networkTask = new NetworkTask(url);
        networkTask.execute();
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        ProgressDialog customCircleProgressDialog = new ProgressDialog(Review.this);
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
            RequestBody body;
            OkHttpClient client = new OkHttpClient();
            if(filePath.length()==0){
                        body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("m_number", m_number)
                        .addFormDataPart("r_name", r_name)
                        .addFormDataPart("c_id", c_id)
                        .addFormDataPart("nickname", nickname)
                        .addFormDataPart("contents", contents.getText().toString())
                        .addFormDataPart("grade", score.getText().toString())
                        .build();

            }else {
                        body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("m_number", m_number)
                        .addFormDataPart("r_name", r_name)
                        .addFormDataPart("c_id", c_id)
                        .addFormDataPart("nickname", nickname)
                        .addFormDataPart("contents", contents.getText().toString())
                        .addFormDataPart("grade", score.getText().toString())
                        .addFormDataPart("uploadFile", "test.jpg", RequestBody.create(MultipartBody.FORM, new File(filePath)))
                        .build();
            }
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
            customCircleProgressDialog.dismiss();
            try {



            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Review.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
