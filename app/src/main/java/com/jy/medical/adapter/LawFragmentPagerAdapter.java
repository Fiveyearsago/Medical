package com.jy.medical.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jy.medical.fragment.LawFragment;
import com.jy.medical.fragment.PageFragment;

/**
 * Created by songran on 16/10/8.
 */

public class LawFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"临床鉴定","精神损害","道路交通","民事诉讼","临床鉴定","精神损害"};
    private Context context;
    public LawFragmentPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        return LawFragment.newInstance(position + 1,context);
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
