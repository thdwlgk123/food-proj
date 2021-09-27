package com.study.android.a4thteamproject01;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResInfoFragment3 extends Fragment {
    private static final String TAG="lecture";

    RecyclerView mRecyclerView;
    ReviewListAdapter adapter;
    RetrofitMain retrofit;
    TextView textview;
    TextView grade;
    TextView countreview;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView[] img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"ResInfoFragment3");
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_res_info3, container, false);

        Bundle bundle=getArguments();
        String m_number=bundle.getString("m_number");
        String r_name=bundle.getString("r_name");
        Log.d(TAG, "m_number: "+m_number+", "+r_name);

        retrofit=new RetrofitMain();
        mRecyclerView=rootView.findViewById(R.id.ReviewList);
        adapter=new ReviewListAdapter(rootView.getContext());

        //평균 별점 표기를 위한 imageview 세팅
        imageView1=rootView.findViewById(R.id.imageView6);
        imageView2=rootView.findViewById(R.id.imageView7);
        imageView3=rootView.findViewById(R.id.imageView8);
        imageView4=rootView.findViewById(R.id.imageView9);
        imageView5=rootView.findViewById(R.id.imageView10);
        img=new ImageView[5];
        img[0]=imageView1;
        img[1]=imageView2;
        img[2]=imageView3;
        img[3]=imageView4;
        img[4]=imageView5;

        textview=rootView.findViewById(R.id.whennull2);
        grade=rootView.findViewById(R.id.grade);
        countreview=rootView.findViewById(R.id.countreview);

        retrofit.service1.getReviewList(r_name, m_number).enqueue(new Callback<ArrayList<ReviewListDto>>() {
            @Override
            public void onResponse(Call<ArrayList<ReviewListDto>> call, Response<ArrayList<ReviewListDto>> response) {
                if(response.isSuccessful()){
                    ArrayList<ReviewListDto> rvlist=response.body();
                    Log.d(TAG, "get response: "+rvlist);
                    if(rvlist.size()==0){
                        textview.setText("(등록된 리뷰가 없습니다.)");
                    }else{
                        textview.setVisibility(View.GONE);
                        int sum=0;
                        Log.d(TAG, "rvlist size : "+rvlist.size());
                        for(ReviewListDto data:rvlist){
                            if(data.getFilename()==null){
                                ReviewListDto dto=new ReviewListDto(data.getNickname(), data.getGrade(), "", data.getContents());
                                sum+=Integer.parseInt(data.getGrade());
                                Log.d(TAG ,"sum: "+sum);
                                adapter.addItem(dto);
                            }else{
                                sum+=Integer.parseInt(data.getGrade());
                                Log.d(TAG ,"sum: "+sum);
                                ReviewListDto dto=new ReviewListDto(data.getNickname(), data.getGrade(), data.getFilename(), data.getContents());
                                adapter.addItem(dto);
                            }
                        }
                        int avg1=sum/rvlist.size();
                        String getavg=String.format("%.1f", sum/(double)rvlist.size());
                        Double avg2=Double.parseDouble(getavg);
                        Log.d(TAG, "avg 계산 : avg2="+avg2+", avg1= "+avg1);
                        checkGrade(avg2);
                        grade.setText(getavg);
                        countreview.setText("리뷰 개수 : "+rvlist.size()+"개");

                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
                        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));
                        mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    }

                }else{
                    Log.d(TAG, "response but fail");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReviewListDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });


        return rootView;

    }

    public void checkGrade(Double avg2){
        List<Double> aList=new ArrayList<>();
        aList.add(avg2);
//        List<Double> bList=new ArrayList<>();
//        bList.add()
        if(avg2.compareTo(4.7)==1){
            for(int i=0;i<5;i++){
                img[i].setImageResource(R.drawable.checked_review_star);
            }
        }else if(avg2.compareTo(4.2)==1){
            for(int i=0;i<5;i++){
                if(i==4){
                    img[i].setImageResource(R.drawable.ic_outline_star_half_24);
                }else{
                    img[i].setImageResource(R.drawable.checked_review_star);
                }
            }
        }
        else if(avg2.compareTo(3.7)==1){
            for(int i=0;i<4;i++){
                img[i].setImageResource(R.drawable.checked_review_star);
            }
        }else if(avg2.compareTo(3.2)==1){
            for(int i=0;i<4;i++){
                if(i==3){
                    img[i].setImageResource(R.drawable.ic_outline_star_half_24);
                }else{
                    img[i].setImageResource(R.drawable.checked_review_star);
                }
            }
        }else if(avg2.compareTo(2.7)==1){
            for(int i=0;i<3;i++){
                img[i].setImageResource(R.drawable.checked_review_star);
            }
        }else if(avg2.compareTo(2.2)==1){
            for(int i=0;i<3;i++){
                if(i==2){
                    img[i].setImageResource(R.drawable.ic_outline_star_half_24);
                }else{
                    img[i].setImageResource(R.drawable.checked_review_star);
                }
            }
        }else if(avg2.compareTo(1.7)==1){
            for(int i=0;i<2;i++){
                img[i].setImageResource(R.drawable.checked_review_star);
            }
        }else if(avg2.compareTo(1.2)==1){
            for(int i=0;i<2;i++){
                if(i==1){
                    img[i].setImageResource(R.drawable.ic_outline_star_half_24);
                }else{
                    img[i].setImageResource(R.drawable.checked_review_star);
                }
            }
        }else if(avg2.compareTo(0.5)==1){
                img[0].setImageResource(R.drawable.checked_review_star);
        }

    }
}