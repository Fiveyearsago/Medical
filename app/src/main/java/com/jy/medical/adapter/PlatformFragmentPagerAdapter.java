package com.jy.medical.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jy.medical.fragment.LawFragment;
import com.jy.medical.fragment.PlatformFragment;

/**
 * Created by songran on 16/10/8.
 */

public class PlatformFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"全部","待办","进行中","超时","未完成"};
    private Context context;
    public PlatformFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        return PlatformFragment.newInstance(position + 1,context);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
