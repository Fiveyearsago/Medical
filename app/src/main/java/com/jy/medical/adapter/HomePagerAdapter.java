package com.jy.medical.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.jy.medical.activities.HomeActivity;
import com.jy.medical.fragment.HomeFragment;
import com.jy.medical.fragment.HomePlatformFragment;
import com.jy.medical.fragment.LoginFragment;
import com.jy.medical.fragment.LoginPhoneFragment;
import com.jy.medical.fragment.MaimGradeFragment;

import java.util.List;

/**
 * Created by songran on 16/10/8.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private Context context;
    private List<Fragment>fragmentList;
    public HomePagerAdapter(FragmentManager fm, Context context, String[] titles,List<Fragment>fragmentList) {
        super(fm);
        this.context=context;
        this.titles=titles;
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
//        if (position==0) {
//            return HomeFragment.newInstance(position, context);
//
//        }else {
//            return HomePlatformFragment.newInstance(position , context);
//        }
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
