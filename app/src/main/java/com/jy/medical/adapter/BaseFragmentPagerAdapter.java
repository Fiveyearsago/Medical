package com.jy.medical.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jy.medical.fragment.PageFragment;

import java.util.List;

/**
 * Created by songran on 16/10/8.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private Context context;
    private List<Fragment> fragmentList;
    public BaseFragmentPagerAdapter(FragmentManager fm, Context context,String[] titles,List<Fragment> fragmentList) {
        super(fm);
        this.context=context;
        this.titles=titles;
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
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
