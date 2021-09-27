package com.study.android.a4thteamproject01.mypage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.android.a4thteamproject01.MainActivity;
import com.study.android.a4thteamproject01.R;
import com.study.android.a4thteamproject01.RestaurantInfoActivity;
import com.study.android.a4thteamproject01.hyungdoo.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckResListAdapter extends RecyclerView.Adapter<CheckResListAdapter.ResItemViewHolder> {
    public final static String TAG = "ResListAdapter";

    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick){
        this.itemClick=itemClick;
    }

    Context context;
    ArrayList<ReservationDto> dtos = new ArrayList<>();


    public class ResItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_resCode;
        protected TextView tv_rName;
        protected TextView tv_condition2;
        protected TextView tv_resDay;
        protected TextView tv_resTime2;
        protected TextView tv_numPerson;
        protected TextView tv_request;
        protected Button btn_check;
        protected Button btn_noCheck;
        protected LinearLayout head;

        public ResItemViewHolder(View view) {
            super(view);
            tv_resCode = view.findViewById(R.id.res_code1);
            tv_rName = view.findViewById(R.id.tv_res_name);
            tv_condition2 = view.findViewById(R.id.tv_condition2);
            tv_resDay = view.findViewById(R.id.res_day);
            tv_resTime2 = view.findViewById(R.id.res_time2);
            tv_numPerson = view.findViewById(R.id.res_per_no);
            tv_request = view.findViewById(R.id.request_con);
            btn_check = view.findViewById(R.id.btn_check);
            btn_noCheck = view.findViewById(R.id.btn_noCheck);
            head = view.findViewById(R.id.headLinLayout);
        }

    }

    public CheckResListAdapter(Context context) {
        this.context=context;
    }

    public void resAddItem(ReservationDto item) {
        dtos.add(item);
    }

    @Override
    public ResItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkreservation_item_view, viewGroup, false);
        ResItemViewHolder viewHolder=new ResItemViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ResItemViewHolder viewholder, int position) {
        viewholder.tv_resCode.setText(dtos.get(position).getR_rsvnumber());
        viewholder.tv_rName.setText(dtos.get(position).getR_name());
        viewholder.tv_resDay.setText(dtos.get(position).getTdate());
        viewholder.tv_resTime2.setText(dtos.get(position).getTime());
        viewholder.tv_numPerson.setText(String.valueOf(dtos.get(position).getB_party()));
        viewholder.tv_request.setText(dtos.get(position).getRequest());
        if (dtos.get(position).getCondition_check() == 3) {

            viewholder.tv_condition2.setText("노쇼");
            viewholder.tv_condition2.setTextColor(Color.parseColor("#001eff"));
            viewholder.btn_check.setVisibility(View.GONE);
            viewholder.btn_noCheck.setVisibility(View.GONE);
        } else if(dtos.get(position).getCondition_check() == 1) {
            viewholder.tv_condition2.setText("방문");
            viewholder.btn_check.setVisibility(View.GONE);
            viewholder.btn_noCheck.setVisibility(View.GONE);
        }

        viewholder.btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetrofitMember retroservice = new RetrofitMember();
                retroservice.mService.checkReservation(dtos.get(position).getR_rsvnumber()).enqueue(new Callback<JSONObjectResult2>() {
                    @Override
                    public void onResponse(Call<JSONObjectResult2> call, Response<JSONObjectResult2> response) {
                        if (response.isSuccessful()) {
                            JSONObjectResult2 res_list = response.body();
                            if(res_list.myreservation==null){
                                Log.d(TAG, "res_list resfrag1 is null");
                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                context.startActivity(intent);
                            }else{

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObjectResult2> call, Throwable t) {
                        t.printStackTrace();
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
            }
        });

        viewholder.btn_noCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitMember retroservice = new RetrofitMember();
                retroservice.mService.noCheckReservation(dtos.get(position).getR_rsvnumber()).enqueue(new Callback<JSONObjectResult2>() {
                    @Override
                    public void onResponse(Call<JSONObjectResult2> call, Response<JSONObjectResult2> response) {
                        if (response.isSuccessful()) {
                            JSONObjectResult2 res_list = response.body();
                            if(res_list.myreservation==null){
                                Log.d(TAG, "res_list resfrag1 is null");
                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                context.startActivity(intent);

                            }else{

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObjectResult2> call, Throwable t) {
                        t.printStackTrace();
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null !=dtos ? dtos.size():0);
    }

    public Object getItem(int position) {
        return dtos.get(position);
    }
}
