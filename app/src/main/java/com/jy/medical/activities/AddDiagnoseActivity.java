package com.jy.medical.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.R;
import com.jy.medical.adapter.BaseFragmentPagerAdapter;
import com.jy.medical.fragment.BodyFragment;
import com.jy.medical.fragment.DiagnoseFragment;
import com.jy.medical.fragment.FollowDetailFragment;
import com.jy.medical.fragment.FollowRecordFragment;
import com.jy.medical.widget.CustomViewpager;

import java.util.ArrayList;
import java.util.List;

public class AddDiagnoseActivity extends BaseActivity {

    private CustomViewpager viewPager;
    private SegmentTabLayout segmentTabLayout;
    private BaseFragmentPagerAdapter adapter;
    private String taskNo;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_diagnose;
    }

    @Override
    public void initParms(Bundle parms) {
        taskNo=parms.getString("taskNo");
    }

    @Override
    public void initView() {
        View headView=findViewById(R.id.title_head_tab);
        headView.findViewById(R.id.page_head_tab_image).setOnClickListener(this);
        headView.findViewById(R.id.page_head_tab_search).setOnClickListener(this);
        segmentTabLayout = (SegmentTabLayout) headView.findViewById(R.id.segmentTabLayout);
        viewPager = (CustomViewpager) findViewById(R.id.viewPager);
        List<Fragment> fragmentList=new ArrayList<>();
        Bundle bundle=new Bundle();
        bundle.putString("taskNo",taskNo);
        BodyFragment bodyFragment= BodyFragment.newInstance();
        DiagnoseFragment diagnoseFragment= DiagnoseFragment.newInstance();
        bodyFragment.setArguments(bundle);
        diagnoseFragment.setArguments(bundle);
        fragmentList.add(bodyFragment);
        fragmentList.add(diagnoseFragment);
        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(),
                this,new String[]{"人体图","诊断列表"},fragmentList);
        viewPager.setAdapter(adapter);
        segmentTabLayout.setTabData(new String[]{"人体图","诊断列表"});
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
                viewPager.resetHeight(position);
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
            case R.id.page_head_tab_image:
                finish();
                break;
            case R.id.page_head_tab_search:
                //搜索诊断
                break;
        }
    }
}
