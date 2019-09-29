package com.example.kamusbi.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.andropath.com.betawi.Fragment.betawiindofragment;
import app.andropath.com.betawi.Fragment.indobetawifragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int jumlahAtab;

    public ViewPagerAdapter(FragmentManager fm, int jumlahAtab) {
        super(fm);
        this.jumlahAtab = jumlahAtab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                betawiindofragment Betawiindofragment = new betawiindofragment();
                return Betawiindofragment;
            case 1:
                indobetawifragment Indobetawifragment = new indobetawifragment();
                return Indobetawifragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return jumlahAtab;
    }
}
