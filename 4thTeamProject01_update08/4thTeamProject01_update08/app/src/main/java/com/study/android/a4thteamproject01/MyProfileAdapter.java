package com.study.android.a4thteamproject01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.android.a4thteamproject01.mypage.ChangeProfile_imgActivity;
import com.study.android.a4thteamproject01.mypage.LoginActivity;
import com.study.android.a4thteamproject01.mypage.UserDto;

import java.util.ArrayList;

public class MyProfileAdapter extends RecyclerView.Adapter<MyProfileAdapter.UserDtoItemViewHolder> {

    private static final String TAG="lecture";

    String fileName;


    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick){
        this.itemClick=itemClick;
    }

    Context context;
    ArrayList<UserDto> items=new ArrayList<>();

    public static class UserDtoItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView beforeLogin;
        protected TextView afterLogin;
        protected ImageView profileimg;

        public UserDtoItemViewHolder(View view){
            super(view);
            beforeLogin=view.findViewById(R.id.beforeLogin);
            afterLogin=view.findViewById(R.id.afterLogin);
            profileimg=view.findViewById(R.id.profileimg);
        }
    }

    public MyProfileAdapter(Context context){
        this.context=context;
    }
    public void addItem(UserDto item){
        items.add(item);
    }

    @Override
    public UserDtoItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myprofile_item_view, viewGroup, false);

        UserDtoItemViewHolder viewHolder=new UserDtoItemViewHolder(view);
        context=this.context;

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull UserDtoItemViewHolder viewholder, int position){
//        Context context=viewholder.imageView1.getContext();
//        viewholder.beforeLogin.setText(items.get(position));
        viewholder.afterLogin.setText(items.get(position).getNickname() + "님\n환영합니다!");
//        viewholder.imageView1.setImageResource(items.get(position).getResId());
//        viewholder.imageView1.setImageResource(R.drawable.snoopy);

        if(items.get(position).getNickname() != "") {
            viewholder.beforeLogin.setVisibility(View.GONE);
            viewholder.afterLogin.setVisibility(View.VISIBLE);
        } else {
            viewholder.beforeLogin.setVisibility(View.VISIBLE);
            viewholder.afterLogin.setVisibility(View.GONE);
        }

//        Picasso.get().load(context.getString(R.string.ipv4)+"img/food.jpg").fit().into(viewholder.profileimg);

        viewholder.profileimg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, ChangeProfile_imgActivity.class);
                context.startActivity(intent1);
            }
        });

        viewholder.beforeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(context, LoginActivity.class);
                context.startActivity(intent2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public Object getItem(int position) {
        return items.get(position);
    }

}
