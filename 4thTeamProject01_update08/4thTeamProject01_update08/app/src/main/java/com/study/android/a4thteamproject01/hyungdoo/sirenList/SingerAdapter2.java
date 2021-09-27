package com.study.android.a4thteamproject01.hyungdoo.sirenList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.android.a4thteamproject01.R;

import java.util.ArrayList;

public class SingerAdapter2 extends RecyclerView.Adapter<SingerAdapter2.SingerItemViewHolder>

{
    private static final String TAG = "lecture";
    SharedPreferences.Editor editor;
    //아이템 클릭시 실행 함수
    public interface ItemClick{
        public void onClick1(View view, int position);

    }
    public interface ItemClick2{
        public void onClick2(View view, int position);

    }
    private ItemClick itemClick;
    private ItemClick2 itemClick2;

    //아이템 클릭시 실행 함수 등록
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }
    public void setItemClick2(ItemClick2 itemClick2){
        this.itemClick2 = itemClick2;
    }

    Context context;
    ArrayList<SingerItem2> items = new ArrayList<SingerItem2>();

    public class SingerItemViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView textView1;
        protected TextView textView2;
        protected TextView minusButton;
        protected TextView plusButton;
        protected TextView countButton;
        protected  TextView totalPrice;


        public SingerItemViewHolder(View view){
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            minusButton = view.findViewById(R.id.minus);
            plusButton = view.findViewById(R.id.plus);
            countButton = view.findViewById(R.id.count);



        }

    }

    public SingerAdapter2(Context context){
        this.context = context;
    }

    public void addItem(SingerItem2 item){
        items.add(item);
    }
    @Override
    public SingerItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.singer_item_view_hyung, viewGroup, false);
        SingerItemViewHolder viewHolder = new SingerItemViewHolder(view);





        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull SingerItemViewHolder viewholder, int position) {

        viewholder.textView1.setText(items.get(position).getMenu_name());
        viewholder.textView2.setText(items.get(position).getMenu_price());

        viewholder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt((String)viewholder.countButton.getText());

                if(count>0)
                {
                    count--;
                    items.get(position).setMenu_count(String.valueOf(count));
                    viewholder.countButton.setText(String.valueOf(count));
                    String sub_menu_price = (String) viewholder.textView2.getText();
                    int j = sub_menu_price.indexOf("원");
                    sub_menu_price = sub_menu_price.substring(0,j);

                    String getToTalPrice = viewholder.totalPrice.getText().toString();
                    j = getToTalPrice.indexOf("원");
                    getToTalPrice = getToTalPrice.substring(0,j);

                    int result = Integer.parseInt(getToTalPrice) + Integer.parseInt(sub_menu_price);

                    viewholder.totalPrice.setText(result + "원 결제하기");
                } else {

                }


            }
        });


        final int num = position;

        viewholder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(itemClick != null){

                    int count = Integer.parseInt((String)viewholder.countButton.getText());
                    count++;
                    items.get(position).setMenu_count(String.valueOf(count));
                    viewholder.countButton.setText(String.valueOf(count));
                    Log.d(TAG,String.valueOf(count));

                    itemClick.onClick1(v, num);


                }

            }
        });
        viewholder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(itemClick != null){

                    int count = Integer.parseInt((String)viewholder.countButton.getText());

                    if(count>0)
                    {
                        count--;
                        items.get(position).setMenu_count(String.valueOf(count));
                        viewholder.countButton.setText(String.valueOf(count));
                        itemClick2.onClick2(v, num);

                    } else {

                    }

                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public Object getItem(int position){
        return items.get(position);
    }




}
