package com.jy.medical.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.MaimPagerAdapter;
import com.jy.medical.adapter.PlatformFragmentPagerAdapter;
import com.jy.medical.widget.SwipeBackLayout;

public class MaimGradeActivity extends BaseActivity {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private MaimPagerAdapter adapter;
    private String taskNo;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_maim_grade;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        MedicalApplication.getInstance().addActivity(this);
        Log.i("onCreate","true");
        setLocationNavState(findViewById(R.id.title_head),  true,"确定");
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabLayout_maim);
        viewPager = (ViewPager) findViewById(R.id.viewPager_maim);
        String[] titles=new String[]{"一级", "二级", "三级", "四级", "五级","六级", "七级", "八级", "九级", "十级"};
        adapter = new MaimPagerAdapter(getSupportFragmentManager(),
                this,titles,taskNo);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        slidingTabLayout.setViewPager(viewPager, titles);
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
