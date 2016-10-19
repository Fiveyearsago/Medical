package com.jy.medical.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.R;
import com.jy.medical.adapter.BaseFragmentPagerAdapter;
import com.jy.medical.adapter.CompensationFragmentPagerAdapter;
import com.jy.medical.fragment.FollowDetailFragment;
import com.jy.medical.fragment.FollowRecordFragment;
import com.jy.medical.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 跟踪详情
 */
public class FollowDetailActivity extends BaseActivity {
    private TextView taskTimeTab;
    private int dayNum=3;
    private ViewPager viewPager;
    private SegmentTabLayout segmentTabLayout;
    private BaseFragmentPagerAdapter adapter;

    @Override
    public void initData() {
        setTaskTimeText(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_follow_detail;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "跟踪详情", false, "");
        taskTimeTab= (TextView) findViewById(R.id.task_time_tag);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(FollowRecordFragment.newInstance());
        fragmentList.add(FollowDetailFragment.newInstance());
        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(),
                this,new String[]{"跟踪记录","详细资料"},fragmentList);
        segmentTabLayout = (SegmentTabLayout) findViewById(R.id.segmentTabLayout);
        viewPager.setAdapter(adapter);
        segmentTabLayout.setTabData(new String[]{"跟踪记录","详细资料"});
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
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("OnTouch","viewPagerOnTouch");
                v.getParent().requestDisallowInterceptTouchEvent(false);
                return true;
            }
        });
        findViewById(R.id.scrollView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("OnTouch","scrollViewOnTouch");
                return false;
            }
        });

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
        }
    }

    public void setTaskTimeText(boolean flag){
        if (flag){
            SpannableString styledText = new SpannableString("已超时"+dayNum+"天");
            styledText.setSpan(new TextAppearanceSpan(this, R.style.textTimeoutTab), 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            taskTimeTab.setText(styledText, TextView.BufferType.SPANNABLE);
        }else {
            SpannableString styledText = new SpannableString(dayNum+"天后超时");
            styledText.setSpan(new TextAppearanceSpan(this, R.style.textNormalTab), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            taskTimeTab.setText(styledText, TextView.BufferType.SPANNABLE);
        }
    }
}
