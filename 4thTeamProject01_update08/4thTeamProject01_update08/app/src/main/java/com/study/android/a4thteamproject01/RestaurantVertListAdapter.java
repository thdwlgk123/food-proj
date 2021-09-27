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

public class RestaurantVertListAdapter extends RecyclerView.Adapter<RestaurantVertListAdapter.RestaurantVertListItemViewHolder>{
    private static final String TAG="lecture";
    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private RestaurantVertListAdapter.ItemClick itemClick;

    public void setItemClick(RestaurantVertListAdapter.ItemClick itemClick){
        this.itemClick=itemClick;
    }

    Context context;
    ArrayList<SingerItem> items=new ArrayList<>();

    public class RestaurantVertListItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView textView1;
        protected TextView textView2;
        protected TextView textView3;
        protected TextView textView4;
        protected TextView textView5;
        protected ImageView imageView1;

        public RestaurantVertListItemViewHolder(View view){
            super(view);
            textView1=view.findViewById(R.id.textView26);
            textView2=view.findViewById(R.id.textView27);
            textView3=view.findViewById(R.id.textView28);
            textView4=view.findViewById(R.id.gradeavg);
            textView5=view.findViewById(R.id.countreview);
            imageView1=view.findViewById(R.id.imageView2);
        }
    }

    public RestaurantVertListAdapter(Context context){
        this.context=context;
    }
    public void addItem(SingerItem item){
        items.add(item);
    }

    @Override
    public RestaurantVertListAdapter.RestaurantVertListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singer_itemvertical_view, viewGroup, false);

        RestaurantVertListAdapter.RestaurantVertListItemViewHolder viewHolder=new RestaurantVertListAdapter.RestaurantVertListItemViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RestaurantVertListAdapter.RestaurantVertListItemViewHolder viewholder, int position){
        viewholder.textView1.setText(items.get(position).getR_name());
        viewholder.textView2.setText(items.get(position).getGu_name());
        viewholder.textView3.setText(items.get(position).getT_name());
        viewholder.textView4.setText(items.get(position).getGradeavg());
        viewholder.textView5.setText("("+String.valueOf(items.get(position).getCountreview())+")");
//        viewholder.imageView1.setImageResource(items.get(position).getResId());
        if(position%4==0){
            Picasso.get().load(context.getString(R.string.ipv4)+"img/food4.jpg").fit().into(viewholder.imageView1);
        }else if(position%4==1)
        {
            Picasso.get().load(context.getString(R.string.ipv4)+"img/food3.jpg").fit().into(viewholder.imageView1);
        }
        else if(position%4==2) {
            Picasso.get().load(context.getString(R.string.ipv4) + "img/food2.jpg").fit().into(viewholder.imageView1);
        }else if(position%4==3){
            Picasso.get().load(context.getString(R.string.ipv4) + "img/food1.jpg").fit().into(viewholder.imageView1);
        }

        final int num=position;

        viewholder.imageView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantInfoActivity.class);
                intent.putExtra("업소관리번호",items.get(position).getM_number());
                intent.putExtra("매장명",items.get(position).getR_name());
                context.startActivity(intent);
            }
        });
    }

    public Object getItem(int position){
        return items.get(position);
    }
    @Override
    public int getItemCount() {
        return (null !=items ? items.size():0);
    }
}
