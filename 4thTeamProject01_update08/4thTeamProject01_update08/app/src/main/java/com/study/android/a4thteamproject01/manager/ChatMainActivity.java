package com.study.android.a4thteamproject01.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.study.android.a4thteamproject01.MainActivity;
import com.study.android.a4thteamproject01.R;

public class ChatMainActivity extends AppCompatActivity {
    private static final String TAG="lecture";


    private TextView user_edit;
    SharedPreferences.Editor editor;


    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference();

    String[] items={"환불문의", "예약취소", "불편신고", "기타문의"};
    private String user_chat;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        SharedPreferences pref=getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor=pref.edit();
        user_id=pref.getString("id","");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,items);

        Spinner spinner=findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_chat=items[position];
                Log.d(TAG, "user_chat: "+user_chat);
                Toast.makeText(getApplicationContext(),"user_chat"+user_chat,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        user_edit=findViewById(R.id.user_edit);
        user_edit.setText(user_id);

        showChatList();
    }

    public void onBtnClicked(View v){

        if(user_edit.getText().toString().equals("")||user_chat.equals(""))
            return;

        Intent intent=new Intent(ChatMainActivity.this, ChatActivity.class);
        intent.putExtra("chatName", user_chat);
        intent.putExtra("userName", user_edit.getText().toString());
        startActivity(intent);
    }

    private void showChatList(){
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
//        chat_list.setAdapter(adapter);

        databaseReference.child("4thteam").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("LOG", "dataSnapshot.getKey() : "+dataSnapshot.getKey());
                adapter.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}