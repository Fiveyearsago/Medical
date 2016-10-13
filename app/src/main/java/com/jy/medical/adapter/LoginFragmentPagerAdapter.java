package com.jy.medical.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jy.medical.fragment.LawFragment;
import com.jy.medical.fragment.LoginFragment;
import com.jy.medical.fragment.LoginPhoneFragment;

/**
 * Created by songran on 16/10/8.
 */

public class LoginFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"账号登陆", "短信验证码登陆"};
    private Context context;

    public LoginFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0) {
                return LoginFragment.newInstance(position, context);

        }else {
            return LoginPhoneFragment.newInstance(position , context);
        }
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
