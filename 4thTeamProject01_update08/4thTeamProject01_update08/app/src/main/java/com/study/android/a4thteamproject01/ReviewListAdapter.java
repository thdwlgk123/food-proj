package com.study.android.a4thteamproject01;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder>{
    private static final String TAG="lecture";

    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private ReviewListAdapter.ItemClick itemClick;

    public void setItemClick(ReviewListAdapter.ItemClick itemClick){
        this.itemClick=itemClick;
    }

    Context context;
    ArrayList<ReviewListDto> items=new ArrayList<>();

    public static class ReviewListViewHolder extends RecyclerView.ViewHolder{
        protected ImageView imageView1;
        protected ImageView imageView2;
        protected ImageView imageView3;
        protected ImageView imageView4;
        protected ImageView imageView5;
        protected ImageView imageView6;
        protected ImageView[] img;
        TextView textView1;//닉네임
        TextView textView2;//리뷰내용

        public ReviewListViewHolder(View view){
            super(view);
            imageView1=view.findViewById(R.id.imageView6);
            imageView2=view.findViewById(R.id.imageView7);
            imageView3=view.findViewById(R.id.imageView8);
            imageView4=view.findViewById(R.id.imageView9);
            imageView5=view.findViewById(R.id.imageView10);

            img=new ImageView[5];
            img[0]=imageView1;
            img[1]=imageView2;
            img[2]=imageView3;
            img[3]=imageView4;
            img[4]=imageView5;

            imageView6=view.findViewById(R.id.imageView5);//리뷰사진

            textView1=view.findViewById(R.id.nickname);
            textView2=view.findViewById(R.id.reviewcontext1);
//            imageView1=view.findViewById(R.id.imageView1);
        }
    }

    public ReviewListAdapter(Context context){
        this.context=context;
    }
    public void addItem(ReviewListDto item){
        items.add(item);
    }

    @Override
    public ReviewListAdapter.ReviewListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_view, viewGroup, false);

        ReviewListAdapter.ReviewListViewHolder viewHolder=new ReviewListAdapter.ReviewListViewHolder(view);
        context=this.context;

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.ReviewListViewHolder viewholder, int position){
//        Context context=viewholder.imageView1.getContext();
        viewholder.textView1.setText(items.get(position).getNickname());
        viewholder.textView2.setText(items.get(position).getContents());
        int star=Integer.parseInt(items.get(position).getGrade());
        int i;
        for(i=0;i<star;i++){
            viewholder.img[i].setImageResource(R.drawable.checked_review_star);
        }
        if(items.get(position).getFilename().equals("")){
           Log.d(TAG, "filename null");
            viewholder.imageView6.setVisibility(View.GONE);
        }else{
            Log.d(TAG, "filename is not null: "+items.get(position).getFilename());
            viewholder.imageView6.setVisibility(View.VISIBLE);
            Picasso.get().load(context.getString(R.string.ipv4)+"upload/"+items.get(position).getFilename()).fit().into(viewholder.imageView6);
            Log.d(TAG,items.get(position).getFilename());
//            Picasso.get().load(context.getString(R.string.ipv4)+"img/test.jpg").fit().into(viewholder.imageView6);
//            Picasso.get().load(context.getString(R.string.ipv4)+"img/food.jpg").fit().into(viewholder.imageView6);
        }
//        viewholder.imageView1.setImageResource(items.get(position).getResId());

//        viewholder.imageView1.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, RestaurantInfoActivity.class);
//                intent.putExtra("업소관리번호",items.get(position).getM_number());
//                intent.putExtra("매장명",items.get(position).getR_name());
//                context.startActivity(intent);
//            }
//        });
    }

    public Object getItem(int position){
        return items.get(position);
    }
    @Override
    public int getItemCount() {
        return (null !=items ? items.size():0);
    }
}
