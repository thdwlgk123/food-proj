package com.study.android.a4thteamproject01.mypage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.study.android.a4thteamproject01.R;
import com.study.android.a4thteamproject01.RestaurantInfoActivity;

import java.util.ArrayList;

public class ResCancelListAdapter extends RecyclerView.Adapter<ResCancelListAdapter.ResItemViewHolder> {
    public final static String TAG = "ResCancelListAdapter";

    Context context;
    ArrayList<SirenDto> dtos = new ArrayList<>();


    public class ResItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_resCode;
        protected TextView tv_rName;
        protected TextView tv_condition1;
        protected TextView tv_condition2;
        protected TextView tv_resDay;
        protected TextView tv_resTime1;
        protected TextView tv_resTime2;
        protected TextView tv_numPerson;
        protected TextView tv_request;
        protected Button btn_store;
        protected Button btn_review;
        protected LinearLayout head;

        public ResItemViewHolder(View view) {
            super(view);
            tv_resCode = view.findViewById(R.id.res_code1);
            tv_rName = view.findViewById(R.id.tv_res_name);
            tv_condition1 = view.findViewById(R.id.tv_condition1);
            tv_condition2 = view.findViewById(R.id.tv_condition2);
            tv_resDay = view.findViewById(R.id.res_day);
            tv_resTime1 = view.findViewById(R.id.res_time1);
            tv_resTime2 = view.findViewById(R.id.res_time2);
            tv_numPerson = view.findViewById(R.id.res_per_no);
            tv_request = view.findViewById(R.id.request_con);
            btn_store = view.findViewById(R.id.btn_check);
            btn_review = view.findViewById(R.id.btn_noCheck);
            head = view.findViewById(R.id.headLinLayout);
        }

    }

    public ResCancelListAdapter(Context context) {
        this.context=context;
    }

    public void resAddItem(SirenDto item) {
        dtos.add(item);
    }

    @Override
    public ResItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reservation_item_view, viewGroup, false);

        ResItemViewHolder viewHolder=new ResItemViewHolder(view);

//        if(dtos.get(viewType).getCondition_check() != (4|5|6)) {
//            viewGroup.setVisibility(View.GONE);
//        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ResItemViewHolder viewholder, int position) {
        viewholder.tv_resCode.setText(dtos.get(position).getR_rsvnumber());
        viewholder.tv_rName.setText(dtos.get(position).getR_name());
        viewholder.tv_resDay.setText(dtos.get(position).getTdate());
        viewholder.tv_resTime1.setText(dtos.get(position).getRes_payment());
        viewholder.tv_resTime2.setText(dtos.get(position).getRequest());
        viewholder.tv_numPerson.setText(String.valueOf(dtos.get(position).getC_name()));
        viewholder.tv_request.setText(dtos.get(position).getRequest());

        if (dtos.get(position).getCondition_check().equals("1")) {
            viewholder.tv_condition1.setVisibility(View.GONE);
            viewholder.tv_condition2.setVisibility(View.VISIBLE);

//            viewholder.tv_resTime2.setText(dtos.get(position).getTime());
            viewholder.tv_resTime1.setVisibility(View.GONE);
            viewholder.tv_resTime2.setVisibility(View.VISIBLE);
//        } else if (dtos.get(position).getCondition_check() == 3) {
//            viewholder.tv_condition1.setVisibility(View.VISIBLE);
//            viewholder.tv_condition2.setVisibility(View.GONE);
//
//            viewholder.tv_resTime1.setVisibility(View.VISIBLE);
//            viewholder.tv_resTime2.setVisibility(View.GONE);
//        } else {
//            viewholder.head.setVisibility(View.GONE);
//        }

            viewholder.btn_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, RestaurantInfoActivity.class);
                    intent.putExtra("업소관리번호", dtos.get(position).getM_number());
                    intent.putExtra("매장명", dtos.get(position).getR_name());
                    Log.d(TAG, intent.toString());
                    context.startActivity(intent);
                }
            });

//        viewholder.btn_review.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Review.class);
//                intent.putExtra("r_name",dtos.get(position).getR_name());
//                intent.putExtra("m_number",dtos.get(position).getM_number());
//                intent.putExtra("nickname",dtos.get(position).getNickname());
//                context.startActivity(intent);
//            }
//        });

        }
    }

        @Override
        public int getItemCount () {
            return (null != dtos ? dtos.size() : 0);
        }

}
