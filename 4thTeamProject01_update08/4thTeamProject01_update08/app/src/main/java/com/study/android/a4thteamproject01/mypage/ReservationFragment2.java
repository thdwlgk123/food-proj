package com.study.android.a4thteamproject01.mypage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.android.a4thteamproject01.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationFragment2 extends Fragment {
    private static final String TAG = "ReservFragment1";

    RetrofitMember retroservice;
    ResListAdapter adapter1;
    RecyclerView recyclerView;
    int nCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.reservation_fragment2, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id = pref.getString("id", "");
        String numAutoLogin = pref.getString("autoLogin", "");
        String numEnabled = pref.getString("enabled", "");
        Log.d(TAG, "2id = " + id);
        Log.d(TAG, "2autoLogin = " + numAutoLogin);
        Log.d(TAG, "2enabled = " + numEnabled);

        adapter1 = new ResListAdapter(getContext());

        if (numEnabled.equals("b") == true) {
            retroservice = new RetrofitMember();
            retroservice.mService.getMyReservData(id).enqueue(new Callback<JSONObjectResult2>() {
                @Override
                public void onResponse(Call<JSONObjectResult2> call, Response<JSONObjectResult2> response) {
                    if (response.isSuccessful()) {
                        JSONObjectResult2 res_list = response.body();
                        if(res_list.myreservation==null){
                            Log.d(TAG, "res_list resfrag1 is null");
//                                TextView text=new TextView(rootView.getContext());

                        }else{
                            for (ReservationDto data:res_list.myreservation) {
                                ReservationDto item = new ReservationDto(data.getR_rsvnumber(), data.getR_name(), data.getM_number(), data.getNickname(),
                                        data.getB_party(), data.getCondition_check(), data.getRequest(), data.getTdate(), data.getTime());
                                if(data.getCondition_check() == 3) {
                                    adapter1.resAddItem(item);
                                }
                            }
                            recyclerView = rootView.findViewById(R.id.list_res_frg2);
                            recyclerView.setAdapter(adapter1);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
                            recyclerView.scrollToPosition(adapter1.getItemCount() - 1);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                        }
                    }
                }

                @Override
                public void onFailure(Call<JSONObjectResult2> call, Throwable t) {
                    t.printStackTrace();
                    Log.d(TAG, "onFailure: "+t.getMessage());
                }
            });

            nCount = 1;

        } else {

        }

        return rootView;
    }
}