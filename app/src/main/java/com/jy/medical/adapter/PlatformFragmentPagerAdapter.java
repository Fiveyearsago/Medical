package com.jy.medical.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.jy.medical.fragment.LawFragment;
import com.jy.medical.fragment.PlatformFragment;

import java.util.List;

/**
 * Created by songran on 16/10/8.
 */

public class PlatformFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles;
    private Context context;
    private List<PlatformFragment>platformFragmentList;
    public PlatformFragmentPagerAdapter(FragmentManager fm, Context context,String[] titles, List<PlatformFragment>platformFragmentList) {
        super(fm);
        this.context=context;
        this.titles=titles;
        this.platformFragmentList=platformFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return platformFragmentList.get(position);
    }
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
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
