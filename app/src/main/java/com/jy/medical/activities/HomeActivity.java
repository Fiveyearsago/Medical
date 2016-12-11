package com.jy.medical.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.HomePagerAdapter;
import com.jy.medical.fragment.HomeFragment;
import com.jy.medical.fragment.HomePlatformFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private SegmentTabLayout segmentTabLayout;
    private ImageView rightImage;
    private HomePagerAdapter homePagerAdapter;
    private ViewPager viewPager;
    private RadioButton radioPlatform, radioMine, radioTool;
    private boolean mIsExit;
    private View navView;
    private PopupWindow popupWindow;
    private TextView checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    private List<Fragment> fragmentList = new ArrayList<>();
    private HomeFragment homeFragment;
    private HomePlatformFragment homePlatformFragment;

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
        navView = findViewById(R.id.title_head_tab);
        setSegmentState(navView);
        View bottomView = findViewById(R.id.navView);
        initPopupWindow();
        homeFragment = HomeFragment.newInstance(0, this);
        homePlatformFragment = HomePlatformFragment.newInstance(1, this);
        fragmentList.add(homeFragment);
        fragmentList.add(homePlatformFragment);
        radioPlatform = (RadioButton) bottomView.findViewById(R.id.radio_btn_platform);
        radioMine = (RadioButton) bottomView.findViewById(R.id.radio_btn_mine);
        radioTool = (RadioButton) bottomView.findViewById(R.id.radio_btn_tool);
        radioMine.setOnClickListener(this);
        radioTool.setOnClickListener(this);
        rightImage = (ImageView) navView.findViewById(R.id.page_head_home_search);
        rightImage.setOnClickListener(this);
        segmentTabLayout = (SegmentTabLayout) navView.findViewById(R.id.segmentTabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        String[] titles = new String[]{"统计视图", "周任务"};
        homePagerAdapter = new HomePagerAdapter((getSupportFragmentManager()), this, titles, fragmentList);
        viewPager.setAdapter(homePagerAdapter);
        segmentTabLayout.setTabData(titles);
        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
                if (position == 0) {
                    rightImage.setVisibility(View.GONE);
                }
                if (position == 1) {
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
                if (position == 0) {
                    rightImage.setVisibility(View.GONE);
                }
                if (position == 1) {
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
        Log.i("Id", v.getId() + "");
        switch (v.getId()) {
            case R.id.radio_btn_mine:
                overridePendingTransition(0, 0);
                startActivity(MineActivity.class);
                break;
            case R.id.radio_btn_tool:
                overridePendingTransition(0, 0);
                startActivity(ToolActivity.class);
                break;
            case R.id.page_head_home_image:
                //通知
                break;
            case R.id.page_head_home_search:
                //筛选
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(navView);
                    setWindowBackground(0.5f);
                }
                break;
            case R.id.popup_check1:
                //全部
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
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        radioPlatform.setChecked(true);
        radioMine.setChecked(false);
        radioTool.setChecked(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                exit();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

    public void setWindowBackground(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }


    public void filterData(String taskType) {
        if (popupWindow.isShowing())
            popupWindow.dismiss();
//        homePlatformFragment.setData(taskType);
        homePlatformFragment.setFilterData(taskType);
    }


}
