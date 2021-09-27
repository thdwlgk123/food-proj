package com.study.android.a4thteamproject01.mypage;

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
import com.study.android.a4thteamproject01.R;
import com.study.android.a4thteamproject01.ReviewListAdapter;
import com.study.android.a4thteamproject01.ReviewListDto;

import java.util.ArrayList;

public class MyReviewListAdapter extends RecyclerView.Adapter<MyReviewListAdapter.ReviewListViewHolder>{
    private static final String TAG="lecture";

    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private MyReviewListAdapter.ItemClick itemClick;

    public void setItemClick(MyReviewListAdapter.ItemClick itemClick){
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
        TextView textView1;     //매장명
        TextView textView2;     //리뷰내용
        TextView textView3;     //작성일

        public ReviewListViewHolder(View view){
            super(view);
            imageView1=view.findViewById(R.id.imageView11);
            imageView2=view.findViewById(R.id.imageView12);
            imageView3=view.findViewById(R.id.imageView13);
            imageView4=view.findViewById(R.id.imageView14);
            imageView5=view.findViewById(R.id.imageView15);

            img=new ImageView[5];
            img[0]=imageView1;
            img[1]=imageView2;
            img[2]=imageView3;
            img[3]=imageView4;
            img[4]=imageView5;

            imageView6=view.findViewById(R.id.imageView16);//리뷰사진

            textView1=view.findViewById(R.id.r_name11);
            textView2=view.findViewById(R.id.reviewcontext2);
            textView3=view.findViewById(R.id.date);
//            imageView1=view.findViewById(R.id.imageView1);
        }
    }

    public MyReviewListAdapter(Context context){
        this.context=context;
    }
    public void addItem(ReviewListDto item){
        items.add(item);
    }

    @Override
    public MyReviewListAdapter.ReviewListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myreview_item, viewGroup, false);

        MyReviewListAdapter.ReviewListViewHolder viewHolder=new MyReviewListAdapter.ReviewListViewHolder(view);
        context=this.context;

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyReviewListAdapter.ReviewListViewHolder viewholder, int position){
//        Context context=viewholder.imageView1.getContext();
        viewholder.textView1.setText(items.get(position).getR_name());
        viewholder.textView2.setText(items.get(position).getContents());
        viewholder.textView3.setText(items.get(position).getTdate());
        int star=Integer.parseInt(items.get(position).getGrade());
        int i;
        for(i=0;i<star;i++){
            viewholder.img[i].setImageResource(R.drawable.checked_review_star);
        }
        if(items.get(position).getFilename().equals("")){
            viewholder.imageView6.setVisibility(View.GONE);
        }
//        viewholder.imageView1.setImageResource(items.get(position).getResId());

        Picasso.get().load("http://172.30.1.43:8081/upload/" + items.get(position).getFilename()).fit().into(viewholder.imageView6);

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