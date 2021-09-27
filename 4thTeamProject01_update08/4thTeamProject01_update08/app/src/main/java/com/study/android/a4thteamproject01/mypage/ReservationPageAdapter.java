package com.study.android.a4thteamproject01.mypage;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ReservationPageAdapter extends FragmentStatePagerAdapter {

    int mtabCount;

    public ReservationPageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.mtabCount = tabCount;
    }

    @Override
    public int getCount() {
        return mtabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReservationFragment1();
            case 1:
                return new ReservationFragment2();
            default:
                return null;
        }
    }
}

