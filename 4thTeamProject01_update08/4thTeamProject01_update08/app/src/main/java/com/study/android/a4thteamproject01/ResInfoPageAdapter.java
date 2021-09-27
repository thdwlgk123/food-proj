package com.study.android.a4thteamproject01;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ResInfoPageAdapter extends FragmentStatePagerAdapter {
    private static final String TAG="lecture";
    int mNumOfTabs;
    Bundle bundle;
    ResInfoFragment1 fragment1;
    ResInfoFragment2 fragment2;
    ResInfoFragment3 fragment3;

    public ResInfoPageAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs=NumOfTabs;
    }

    public ResInfoPageAdapter(FragmentManager fm, int NumOfTabs, Bundle bundle){
        super(fm);
        this.mNumOfTabs=NumOfTabs;
        this.bundle=bundle;
    }
    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                fragment1=new ResInfoFragment1();
                fragment1.setArguments(bundle);
                return fragment1;
            case 1:
                fragment2=new ResInfoFragment2();
//                Log.d(TAG, String.valueOf(bundle));
                fragment2.setArguments(bundle);
                return fragment2;
            case 2:
                fragment3=new ResInfoFragment3();
                fragment3.setArguments(bundle);
                return fragment3;
            default:
                return null;
        }
    }
    public int getCount() {
        return mNumOfTabs;
    }
}
