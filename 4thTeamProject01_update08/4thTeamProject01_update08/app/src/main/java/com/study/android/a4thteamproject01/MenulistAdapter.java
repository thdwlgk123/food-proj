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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenulistAdapter extends RecyclerView.Adapter<MenulistAdapter.MenulistViewHolder>{
    private static final String TAG="lecture";
    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private MenulistAdapter.ItemClick itemClick;

    public void setItemClick(MenulistAdapter.ItemClick itemClick){
        this.itemClick=itemClick;
    }

    Context context;
    ArrayList<MenuItem> items=new ArrayList<>();

    public static class MenulistViewHolder extends RecyclerView.ViewHolder{
        protected TextView textView1;
        protected TextView textView2;
//        protected ImageView imageView1;

        public MenulistViewHolder(View view){
            super(view);
            textView1=view.findViewById(R.id.menuname);
            textView2=view.findViewById(R.id.menuprice);
//            imageView1=view.findViewById(R.id.imageView1);
        }
    }

    public MenulistAdapter(Context context){
        this.context=context;
    }
    public void addItem(MenuItem item){
        items.add(item);
    }

    @Override
    public MenulistAdapter.MenulistViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menulist_layout, viewGroup, false);

        MenulistAdapter.MenulistViewHolder viewHolder=new MenulistAdapter.MenulistViewHolder(view);
        context=this.context;

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MenulistAdapter.MenulistViewHolder viewholder, int position){
//        Context context=viewholder.imageView1.getContext();
          viewholder.textView1.setText(items.get(position).getR_menu());
          viewholder.textView2.setText(items.get(position).getMenu_price());
//        viewholder.imageView1.setImageResource(items.get(position).getResId());


    }

    public Object getItem(int position){
        return items.get(position);
    }
    @Override
    public int getItemCount() {
        return (null !=items ? items.size():0);
    }
}
