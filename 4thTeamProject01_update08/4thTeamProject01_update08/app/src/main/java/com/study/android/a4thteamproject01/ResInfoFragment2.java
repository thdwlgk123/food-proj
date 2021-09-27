package com.study.android.a4thteamproject01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResInfoFragment2 extends Fragment {
    private static final String TAG="lecture";
    private String name;
    private String number;
    private int checkstore=0;
    private int checkmenu=0;

    RecyclerView mRecyclerView;
    MenulistAdapter adapter;
    TextView textView;

//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        if(getArguments()!=null){
//            name=getArguments().getString("매장명");
//            number=getArguments().getString("업소관리번호");
//        }else{
//            Log.d(TAG, "resinfofrag2: Bundel null값");
//        }
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG,"ResInfoFragment2");
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_res_info2, container, false);

        adapter=new MenulistAdapter(rootView.getContext());
        textView=rootView.findViewById(R.id.whennull);
        checkstore=0;

        Bundle bundle=getArguments();
        String menu=bundle.getString("메뉴");
        String r_name=bundle.getString("r_name");
//        Log.d(TAG,"RESINFOFRAG2: "+menu+", "+r_name);

        if(menu.equals("MENU_IS_NULL")){
            checkmenu=1;
        }

        if(checkmenu==0){
            textView.setVisibility(View.GONE);
            //해당 string[]안의 가게들은 r_menu가 메뉴:가격 형태로 저장되어있음.
            String[] storelist=new String[]{"두르가 (종각)","두르가","바로화덕치킨","지호한방삼계탕","채선당종로점","독도참치종각점"};
            for(String store:storelist){
//                Log.d(TAG ,"RESINFOFRAG2:for문 storename: "+store);
                if(r_name.equals(store)){
//                    Log.d(TAG ,"RESINFOFRAG2:for문 if문 들옴");
                    checkstore=1;
                    break;
                }
            }

            //checkstore=0이면 메뉴 띄어쓰기 구분 checkstore=1이면 메뉴:가격,메뉴:가격 형태
            if(checkstore==1){
                Log.d(TAG, "RESINFOFRAG2:checkstore=1");
                int target_num;
                String[] menulist=menu.split(",");
                for(String data:menulist){
                    target_num=data.indexOf(":");
                    String menuname=data.substring(0,target_num);
                    String menuprice=data.substring(target_num+1);
//                    Log.d(TAG, "RESINFOFRAG2: substring =>"+menuname+":"+menuprice);
                    MenuItem item=new MenuItem(menuname, menuprice);
                    adapter.addItem(item);
                }
            }else{
                String[] menulist=menu.split(" ");
                for(String data:menulist){
//                        Log.d(TAG, data);
                    MenuItem item=new MenuItem(data);
                    adapter.addItem(item);
                }
            }

            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(rootView.getContext(),new LinearLayoutManager(rootView.getContext()).getOrientation());

            mRecyclerView=rootView.findViewById(R.id.MenuList);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.VERTICAL, true));

            //구분선 및 공백 추가
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));

            mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }else{
            textView.setText("(등록된 메뉴가 없습니다.)");
        }

        return rootView;
    }
}