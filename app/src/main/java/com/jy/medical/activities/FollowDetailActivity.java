package com.jy.medical.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.R;
import com.jy.medical.adapter.BaseFragmentPagerAdapter;
import com.jy.medical.fragment.BaseInfoFragment;
import com.jy.medical.fragment.EarningFragment;
import com.jy.medical.fragment.FollowDetailFragment;
import com.jy.medical.fragment.FollowRecordFragment;
import com.jy.medical.fragment.MedicalVisitFragment;
import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.MedicalVisit;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.MedicalVisitManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.CommitUtil;
import com.jy.medical.util.TimeUtil;
import com.jy.medical.widget.CustomViewpager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 跟踪详情
 */
public class FollowDetailActivity extends BaseActivity implements OnItemClickListener, OnDismissListener {
    private TextView taskTimeTab;
    private int dayNum = 0;
    private CustomViewpager viewPager;
    private SegmentTabLayout segmentTabLayout;
    private BaseFragmentPagerAdapter adapter;
    private PlatformData platformData;
    private TextView textName;
    private TextView textTime;
    private TextView textReport;
    private ImageButton imagePhone;
    private AlertView mAlertView;
    private String taskType="";
    private String commitFlag="";
    private MedicalVisitManager medicalVisitManager = DaoUtils.getMedicalVisitInstance();
    private BaseInfoDataManager baseInfoDataManager = DaoUtils.getBaseInfoDataInstance();
    private View layoutBottom;
    private Context context;
    private ImageView taskTypeImage;
    @Override
    public void initData() {
        context=this;
        textName.setText(platformData.getPeopleName());
        textTime.setText(platformData.getTime());
        textReport.setText(platformData.getReportNum());

        try {
            dayNum = TimeUtil.getGapCount(platformData.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setTaskTimeText(dayNum);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_follow_detail;
    }

    @Override
    public void initParms(Bundle parms) {
        platformData = (PlatformData) parms.getSerializable("info");
        taskType = platformData.getTaskType();
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "跟踪详情", false, "");
        findViewById(R.id.task_time_btn).setOnClickListener(this);
        taskTypeImage= (ImageView) findViewById(R.id.task_state_image);
        layoutBottom = findViewById(R.id.bottom_layout);
        textName = (TextView) findViewById(R.id.follow_detail_name);
        textTime = (TextView) findViewById(R.id.follow_detail_time_text);
        textReport = (TextView) findViewById(R.id.follow_detail_report);
        taskTimeTab = (TextView) findViewById(R.id.task_time_tag);
        imagePhone = (ImageButton) findViewById(R.id.follow_detail_phone);
        imagePhone.setOnClickListener(this);
        viewPager = (CustomViewpager) findViewById(R.id.xViewPager);
        mAlertView = new AlertView(platformData.getPeopleName(), platformData.getPhoneNum().trim(), "取消", new String[]{"呼叫"}, null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);

        List<Fragment> fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("taskNo", platformData.getTaskNo());
        FollowDetailFragment followDetailFragment = FollowDetailFragment.newInstance();
        MedicalVisitFragment medicalVisitFragment = MedicalVisitFragment.newInstance(this);
        EarningFragment earningFragment = EarningFragment.newInstance(this);
        BaseInfoFragment baseInfoFragment = BaseInfoFragment.newInstance(this);
        switch (taskType) {
            case "01":
                followDetailFragment.setArguments(bundle);
                medicalVisitFragment.setArguments(bundle);
                fragmentList.add(medicalVisitFragment);
                fragmentList.add(followDetailFragment);
                MedicalVisit medicalVisit = medicalVisitManager.getData(platformData.getTaskNo());
                commitFlag = (medicalVisit == null) ? "" : medicalVisit.getCommitFlag();
                break;
            case "02":
                followDetailFragment.setArguments(bundle);
                earningFragment.setArguments(bundle);
                fragmentList.add(earningFragment);
                fragmentList.add(followDetailFragment);
                MedicalVisit medicalVisit1 = medicalVisitManager.getData(platformData.getTaskNo());
                commitFlag = (medicalVisit1 == null) ? "" : medicalVisit1.getCommitFlag();
                break;
            case "09":
                baseInfoFragment.setArguments(bundle);
                followDetailFragment.setArguments(bundle);
                fragmentList.add(baseInfoFragment);
                fragmentList.add(followDetailFragment);
                BaseInfoData baseInfoData = baseInfoDataManager.getData(platformData.getTaskNo());
                commitFlag = (baseInfoData == null) ? "" : baseInfoData.getCommitFlag();
                break;
            default:
                followDetailFragment.setArguments(bundle);
                medicalVisitFragment.setArguments(bundle);
                fragmentList.add(medicalVisitFragment);
                fragmentList.add(followDetailFragment);
                break;
        }
        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(),
                this, new String[]{"跟踪记录", "详细资料"}, fragmentList);
        segmentTabLayout = (SegmentTabLayout) findViewById(R.id.segmentTabLayout);
        viewPager.setAdapter(adapter);
        segmentTabLayout.setTabData(new String[]{"跟踪记录", "详细资料"});
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
            case R.id.page_head_image:
                finish();
                break;
            case R.id.follow_detail_phone:
                //拨打电话
                mAlertView.show();
                break;
            case R.id.task_time_btn:
                //拨打电话
                commitData();

                break;
        }
    }

    private void commitData() {
        switch (taskType) {
            case "01":
                CommitUtil.commitMedical(this, platformData.getTaskNo(), new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        Toast toast= Toast.makeText(context, "已成功提交", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },1000);
                    }

                    @Override
                    public void commitFailed() {
                        Toast.makeText(context,"提交失败",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case "02":
                break;
            case "03":
                break;
            case "04":
                break;
            case "05":
                break;
            case "06":
                break;
            case "07":
                break;
            case "08":
                break;
            case "09":
                CommitUtil.commitBaseInfo(this, platformData.getTaskNo(), new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        Toast toast= Toast.makeText(context, "已成功提交", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },1000);
                    }

                    @Override
                    public void commitFailed() {
                        Toast.makeText(context,"提交失败",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case "10":
                break;

        }
    }

    public void setTaskTimeText(int dayNum) {
        if (dayNum > 0) {
            SpannableString styledText = new SpannableString("已超时" + dayNum + "天");
            int length = 3 + String.valueOf(dayNum).length();
            styledText.setSpan(new TextAppearanceSpan(this, R.style.textTimeoutTab), 3, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            taskTimeTab.setText(styledText, TextView.BufferType.SPANNABLE);
        } else {
            int length = String.valueOf(-dayNum).length();
            SpannableString styledText = new SpannableString(-dayNum + "天后超时");
            styledText.setSpan(new TextAppearanceSpan(this, R.style.textNormalTab), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            taskTimeTab.setText(styledText, TextView.BufferType.SPANNABLE);
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
//        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
        if (position == 0) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + platformData.getPhoneNum().trim()));
            FollowDetailActivity.this.startActivity(intent);
        }
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (taskType.equals("1")) {
//            layoutBottom.setVisibility(View.GONE);
//        } else {
//            layoutBottom.setVisibility(View.VISIBLE);
//        }
        if (commitFlag.equals("1")){
            layoutBottom.setVisibility(View.GONE);
        }else {
            layoutBottom.setVisibility(View.VISIBLE);
        }
        setTaskTypeImage();
    }

    private void setTaskTypeImage() {
        taskTypeImage.setBackground(context.getResources().getDrawable(R.mipmap.detail_timeout));
    }

}
