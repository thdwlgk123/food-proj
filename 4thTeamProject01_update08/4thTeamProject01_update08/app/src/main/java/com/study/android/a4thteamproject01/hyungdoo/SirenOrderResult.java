package com.study.android.a4thteamproject01.hyungdoo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.a4thteamproject01.R;

public class SirenOrderResult extends Activity {
    private int stuck = 10;

    String menu, address;
    TextView orderedMenu, resAddress, copyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sirenorder2);

        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        address = address.replace("null","");
        menu = intent.getStringExtra("menu");
        menu = menu.replace("  ","\n");

        orderedMenu = findViewById(R.id.orderedMenu);
        resAddress = findViewById(R.id.restaurantAddress);
        copyButton = findViewById(R.id.copyButton);


        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클립보드 사용 코드
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("address",address); //클립보드에 ID라는 이름표로 id 값을 복사하여 저장
                clipboardManager.setPrimaryClip(clipData);

                //복사가 되었다면 토스트메시지 노출
                Toast.makeText(getApplicationContext(),"주소가 복사되었습니다.",Toast.LENGTH_SHORT).show();

            }
        });
        orderedMenu.setText(menu);
        resAddress.setText(address);


    }

    public void btnToMain(View v){
        Intent intent = new Intent(SirenOrderResult.this, com.study.android.a4thteamproject01.MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SirenOrderResult.this,  com.study.android.a4thteamproject01.MainActivity.class);
        startActivity(intent);
        finish();
    }

}