package com.jy.medical.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;
import com.jy.medical.adapter.LawFragmentPagerAdapter;

/**
 * 赔偿标准
 */
public class CompensationActivity extends BaseActivity {
   private ViewPager viewPager;
   private LawFragmentPagerAdapter adapter;
   private TabLayout tabLayout;


    @Override
    public void initData() {
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_compensation;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setNavState(findViewById(R.id.title_head_second),"赔偿标准");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new LawFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_second_image:
                finish();
                break;
            case R.id.nav_layout:
                startActivity(SelectCityActivity.class);
                break;
            default:
                break;
        }
    }
}
