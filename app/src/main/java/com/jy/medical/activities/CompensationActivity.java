package com.jy.medical.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.R;
import com.jy.medical.adapter.CompensationFragmentPagerAdapter;
import com.jy.medical.adapter.LawFragmentPagerAdapter;
import com.jy.medical.widget.SwipeBackLayout;

/**
 * 参考代码:http://blog.csdn.net/analyzesystem/article/details/51426473
 * 赔偿标准
 */
public class CompensationActivity extends BaseActivity {
    private ViewPager viewPager;
    private CompensationFragmentPagerAdapter adapter;
    private SegmentTabLayout segmentTabLayout;


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_compensation;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setNavState(findViewById(R.id.title_head_second), "赔偿标准");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new CompensationFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        segmentTabLayout = (SegmentTabLayout) findViewById(R.id.segmentTabLayout);
        viewPager.setAdapter(adapter);
        segmentTabLayout.setTabData(new String[]{"2016年", "2015年", "2014年"});
        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
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
