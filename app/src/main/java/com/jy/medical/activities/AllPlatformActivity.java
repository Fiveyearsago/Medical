package com.jy.medical.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.R;
import com.jy.medical.adapter.PlatformFragmentPagerAdapter;
import com.jy.medical.fragment.PlatformFragment;
import com.jy.medical.greendao.manager.ClaimManager;
import com.jy.medical.greendao.util.DaoUtils;

import java.util.ArrayList;
import java.util.List;

public class AllPlatformActivity extends BaseActivity{
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private PlatformFragmentPagerAdapter adapter;
    private List<PlatformFragment> platformFragmentList = new ArrayList<>();
    private static PlatformFragment fragmentAll;
    private static PlatformFragment fragmentDoing;
    private static PlatformFragment fragmentTimeout;
    private static PlatformFragment fragmentFinished;
    private String taskType = "";
    private ClaimManager claimManager = DaoUtils.getClaimInstance();
    private PopupWindow popupWindow;
    private TextView checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    private View navView;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_platform;
    }

    @Override
    public void initParams(Bundle parms) {
        taskType = parms.getString("taskType");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        navView = findViewById(R.id.title_head_third);
        setAllPlatformNavState(navView, "全部任务");
        initPopupWindow();
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabLayout_allPlatform);
        viewPager = (ViewPager) findViewById(R.id.viewPager_allPlatform);
        String[] titles = new String[]{"全部", "未超时", "已超时", "已完成"};
        initFragment();
        adapter = new PlatformFragmentPagerAdapter(getSupportFragmentManager(),
                this, titles, platformFragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
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
                filterData(taskType);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }


    private void initFragment() {
        fragmentAll = PlatformFragment.newInstance(0, this);
        fragmentDoing = PlatformFragment.newInstance(1, this);
        fragmentTimeout = PlatformFragment.newInstance(2, this);
        fragmentFinished = PlatformFragment.newInstance(3, this);
        platformFragmentList.add(fragmentAll);
        platformFragmentList.add(fragmentDoing);
        platformFragmentList.add(fragmentTimeout);
        platformFragmentList.add(fragmentFinished);
        for (int i = 0; i < platformFragmentList.size(); i++) {
            Bundle bundle = new Bundle();
            //任务模块
            bundle.putString("taskType", taskType);
            //任务状态（超时，未超时，已完成）
            bundle.putString("taskFlag", i + "");
            platformFragmentList.get(i).setArguments(bundle);
        }
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
            case R.id.page_head_home_filter:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(navView);
                    setWindowBackground(0.5f);
                }
                break;
            case R.id.popup_check1:
                //全部
                taskType="";
                filterData("");
                break;
            case R.id.popup_check2:
                //基本信息
                filterData("09");
                break;
            case R.id.popup_check3:
                //事故处理
                filterData("10");
                break;
            case R.id.popup_check4:
                //医疗探视
                filterData("01");
                break;
            case R.id.popup_check5:
                //收入情况
                filterData("02");
                break;
            case R.id.popup_check6:
                //误工情况
                filterData("03");
                break;
            case R.id.popup_check7:
                //户籍信息
                filterData("04");
                break;
            case R.id.popup_check8:
                //被扶养人
                filterData("05");
                break;
            case R.id.popup_check9:
                //伤残
                filterData("08");
                break;
            case R.id.popup_check10:
                //死亡信息
                filterData("06");
                break;
        }
    }

    public void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popup_group, null);
        contentView.setFocusable(false); // 这个很重要
        contentView.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();

                    return true;
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowBackground(1.0f);
            }
        });
        checkBox1 = (TextView) contentView.findViewById(R.id.popup_check1);
        checkBox2 = (TextView) contentView.findViewById(R.id.popup_check2);
        checkBox3 = (TextView) contentView.findViewById(R.id.popup_check3);
        checkBox4 = (TextView) contentView.findViewById(R.id.popup_check4);
        checkBox5 = (TextView) contentView.findViewById(R.id.popup_check5);
        checkBox6 = (TextView) contentView.findViewById(R.id.popup_check6);
        checkBox7 = (TextView) contentView.findViewById(R.id.popup_check7);
        checkBox8 = (TextView) contentView.findViewById(R.id.popup_check8);
        checkBox9 = (TextView) contentView.findViewById(R.id.popup_check9);
        checkBox10 = (TextView) contentView.findViewById(R.id.popup_check10);
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);
        checkBox5.setOnClickListener(this);
        checkBox6.setOnClickListener(this);
        checkBox7.setOnClickListener(this);
        checkBox8.setOnClickListener(this);
        checkBox9.setOnClickListener(this);
        checkBox10.setOnClickListener(this);
    }



    public void filterData(String taskType) {
        this.taskType=taskType;
        if (popupWindow.isShowing())
            popupWindow.dismiss();
        int position = viewPager.getCurrentItem();
        switch (position) {
            case 0:

                break;
        }
        platformFragmentList.get(position).setFilterData(taskType,position);
    }

    public void setWindowBackground(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }
}
