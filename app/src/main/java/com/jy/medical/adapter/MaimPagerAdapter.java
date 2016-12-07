package com.jy.medical.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.jy.medical.fragment.MaimGradeFragment;
import com.jy.medical.fragment.PlatformFragment;

/**
 * Created by songran on 16/10/8.
 */

public class MaimPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private Context context;
    private String taskNo;
    public MaimPagerAdapter(FragmentManager fm, Context context,String[] titles,String taskNo) {
        super(fm);
        this.context=context;
        this.titles=titles;
        this.taskNo=taskNo;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("position",position+"");
        MaimGradeFragment maimGradeFragment=MaimGradeFragment.newInstance(position + 1,context);
        Bundle bundle=new Bundle();
        bundle.putString("taskNo",taskNo);
        int num=position + 1;
        String gradeCode="";
        if (num != 10)
            gradeCode = "0" + num;
        else gradeCode = num + "";
        bundle.putString("gradeCode",gradeCode);
        maimGradeFragment.setArguments(bundle);
        return maimGradeFragment;
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
