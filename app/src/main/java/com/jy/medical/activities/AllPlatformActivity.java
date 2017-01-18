package com.jy.medical.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;


public class AllPlatformActivity extends BaseActivity {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private PlatformFragmentPagerAdapter adapter;
    private List<PlatformFragment> platformFragmentList = new ArrayList<>();
    private static PlatformFragment fragmentAll;
    private static PlatformFragment fragmentDoing;
    private static PlatformFragment fragmentTimeout;
    private static PlatformFragment fragmentFinished;
    private String taskType = "";
    private TextView titleTV;
    private ClaimManager claimManager = DaoUtils.getClaimInstance();
    private PopupWindow popupWindow;
    private TextView checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    private View navView;
    private int mPosition = -1;

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
        if (parms.getInt("position") != -1) {
            mPosition = parms.getInt("position");
        }
        initFragment();
    }

    @Override
    public void initView() {
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setStatusBarTint();
        navView = findViewById(R.id.title_head_third);
        titleTV = (TextView) navView.findViewById(R.id.page_third_head_text);
        setAllPlatformNavState(navView, "全部任务");
        initPopupWindow();
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabLayout_allPlatform);
        viewPager = (ViewPager) findViewById(R.id.viewPager_allPlatform);
        String[] titles = new String[]{"全部", "未超时", "已超时", "已完成"};

        adapter = new PlatformFragmentPagerAdapter(getSupportFragmentManager(),
                this, titles, platformFragmentList);

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager, titles);
        if (mPosition != -1) {
//            initFragment();
            viewPager.setCurrentItem(mPosition);
            slidingTabLayout.setCurrentTab(mPosition);
            mPosition = -1;
        }

        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
                filterData(taskType);
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
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i("ACTION_DOWN", "ViewPager");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i("ACTION_MOVE", "ViewPager");
//                        viewPager.onInterceptTouchEvent(event);
//                        break;
//                }
//                return false;
//            }
//        });
        setTitleText();
//        filterData(taskType);

    }

    private void setTitleText() {
        switch (taskType) {
            case "":
                titleTV.setText("全部任务");
                break;
            case "01":
                titleTV.setText("医疗探视");
                break;
            case "02":
                titleTV.setText("收入情况");
                break;
            case "03":
                titleTV.setText("误工信息");
                break;
            case "04":
                titleTV.setText("户籍居住");
                break;
            case "05":
                titleTV.setText("被扶养人");
                break;
            case "06":
                titleTV.setText("死亡信息");
                break;
            case "08":
                titleTV.setText("伤残鉴定");
                break;
            case "09":
                titleTV.setText("基本信息");
                break;
            case "10":
                titleTV.setText("事故现场");
                break;
        }
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
            bundle.putInt("taskFlag", i);
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

    @Override
    protected void onResume() {
        super.onResume();
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
        this.taskType = taskType;
        setTitleText();
        if (popupWindow.isShowing())
            popupWindow.dismiss();
        int position = viewPager.getCurrentItem();
        platformFragmentList.get(position).setFilterData(taskType, position);
    }

    public void filterData(int position) {
        viewPager.setCurrentItem(position);
    }

    public void setWindowBackground(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this.getWindow().setAttributes(lp);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (alpha > 0.5) {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            this.getWindow().setBackgroundDrawableResource(R.color.blackPressed);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            filterData(taskType);
        }
    }
}
