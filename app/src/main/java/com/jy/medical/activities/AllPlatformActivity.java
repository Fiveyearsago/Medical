package com.jy.medical.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.R;
import com.jy.medical.adapter.PlatformFragmentPagerAdapter;

public class AllPlatformActivity extends BaseActivity {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private PlatformFragmentPagerAdapter adapter;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_platform;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setThirdNavState(findViewById(R.id.title_head_third), "跟踪平台", R.drawable.nav_image_selector, R.drawable.serach_selector);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabLayout_allPlatform);
        viewPager = (ViewPager) findViewById(R.id.viewPager_allPlatform);
        adapter = new PlatformFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager, new String[]{"全部", "待办", "进行中", "超时", "未完成"});
        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                slidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_third_head_image:
                finish();
                break;
            case R.id.page_third_head_collect:
                startActivity(SearchPlatformActivity.class);
                break;
        }
    }
}
