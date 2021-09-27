package com.study.android.a4thteamproject01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Bundle bundle;
    Fragment1 frag1;

    public PageAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs=NumOfTabs;
    }
    public PageAdapter(FragmentManager fm, int NumOfTabs, Bundle bundle){
        super(fm);
        this.mNumOfTabs=NumOfTabs;
        this.bundle=bundle;
    }

    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                frag1=new Fragment1();
                frag1.setArguments(bundle);
                return frag1;
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            case 3:
                return new Fragment4();
            case 4:
                return new Fragment5();
            default:
                return null;
        }
    }
    public int getCount() {
        return mNumOfTabs;
    }
}
