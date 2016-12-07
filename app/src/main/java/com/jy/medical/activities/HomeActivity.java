package com.jy.medical.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.HomePagerAdapter;

public class HomeActivity extends BaseActivity {
    private SegmentTabLayout segmentTabLayout;
    private ImageView rightImage;
    private HomePagerAdapter homePagerAdapter;
    private ViewPager viewPager;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setHomeStatusBarTint();
        View navView=findViewById(R.id.title_head_tab);
        setSegmentState(navView,false);
        rightImage= (ImageView) navView.findViewById(R.id.page_head_tab_search);
        segmentTabLayout = (SegmentTabLayout) navView.findViewById(R.id.segmentTabLayout);
        viewPager=(ViewPager) findViewById(R.id.viewPager);
        String[] titles=new String[]{"统计视图", "周任务"};
        homePagerAdapter=new HomePagerAdapter((getSupportFragmentManager()),this,titles);
        viewPager.setAdapter(homePagerAdapter);
        segmentTabLayout.setTabData(titles);
        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
                if (position==0){
                    rightImage.setVisibility(View.GONE);
                }
                if (position==1){
                    rightImage.setVisibility(View.VISIBLE);
                }
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
                if (position==0){
                    rightImage.setVisibility(View.GONE);
                }
                if (position==1){
                    rightImage.setVisibility(View.VISIBLE);
                }
                segmentTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    @Override
    public void widgetClick(View v) {

    }
}
