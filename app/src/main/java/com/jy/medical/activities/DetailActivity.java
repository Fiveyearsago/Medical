package com.jy.medical.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.R;
import com.jy.medical.adapter.BaseFragmentPagerAdapter;
import com.jy.medical.fragment.BaseInfoFragment;
import com.jy.medical.fragment.DeathFragment;
import com.jy.medical.fragment.EarningFragment;
import com.jy.medical.fragment.FollowDetailFragment;
import com.jy.medical.fragment.HandleFragment;
import com.jy.medical.fragment.MedicalVisitFragment;
import com.jy.medical.fragment.SupporterFragment;
import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.DeathData;
import com.jy.medical.greendao.entities.EarningData;
import com.jy.medical.greendao.entities.HandleData;
import com.jy.medical.greendao.entities.MedicalVisit;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.DeathDataManager;
import com.jy.medical.greendao.manager.EarningDataManager;
import com.jy.medical.greendao.manager.HandleDataManager;
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
public class DetailActivity extends BaseActivity implements OnItemClickListener, OnDismissListener {
    private TextView taskTimeTab;
    private int dayNum = 0;
    private CustomViewpager viewPager;
    private SlidingTabLayout segmentTabLayout;
    private BaseFragmentPagerAdapter adapter;
    private PlatformData platformData;
    private TextView textName;
    private TextView textTime;
//    private TextView textReport;
    private ImageButton imagePhone;
    private AlertView mAlertView;
    private String taskType = "";
    private String commitFlag = "";
    private MedicalVisitManager medicalVisitManager = DaoUtils.getMedicalVisitInstance();
    private EarningDataManager earningDataManager = DaoUtils.getEarningDataInstance();
    private DeathDataManager deathDataManager = DaoUtils.getDeathDataInstance();
    private BaseInfoDataManager baseInfoDataManager = DaoUtils.getBaseInfoDataInstance();
    private HandleDataManager handleDataManager = DaoUtils.getHandleDataInstance();
    private View layoutBottom;
    private Context context;
    private ImageView taskTypeImage;
    private FloatingActionButton floatingActionButton;
    private String taskNo;

    @Override
    public void initData() {
        context = this;
        textName.setText(platformData.getPeopleName());
        textTime.setText(platformData.getTime());
//        textReport.setText(platformData.getReportNum());

        try {
            dayNum = TimeUtil.getGapCount(platformData.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setTaskTimeText(dayNum);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initParams(Bundle parms) {
        platformData = (PlatformData) parms.getSerializable("info");
        taskType = platformData.getTaskType();
        taskNo = platformData.getTaskNo();
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "跟踪详情", false, "");
        findViewById(R.id.task_commit_btn).setOnClickListener(this);
        taskTypeImage = (ImageView) findViewById(R.id.task_state_image);
        layoutBottom = findViewById(R.id.bottom_layout);
        textName = (TextView) findViewById(R.id.follow_detail_name);
        textTime = (TextView) findViewById(R.id.follow_detail_time_text);
//        textReport = (TextView) findViewById(R.id.follow_detail_report);
        taskTimeTab = (TextView) findViewById(R.id.task_time_tag);
        imagePhone = (ImageButton) findViewById(R.id.follow_detail_phone);
        imagePhone.setOnClickListener(this);
        viewPager = (CustomViewpager) findViewById(R.id.xViewPager);
        mAlertView = new AlertView(platformData.getPeopleName(), platformData.getPhoneNum().trim(), "取消", new String[]{"呼叫"}, null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_button);
        floatingActionButton.setOnClickListener(this);
        List<Fragment> fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("taskNo", taskNo);
        FollowDetailFragment followDetailFragment = FollowDetailFragment.newInstance();
        MedicalVisitFragment medicalVisitFragment = MedicalVisitFragment.newInstance(this);
        EarningFragment earningFragment = EarningFragment.newInstance(this);
        DeathFragment deathFragment = DeathFragment.newInstance(this);
        BaseInfoFragment baseInfoFragment = BaseInfoFragment.newInstance(this);
        HandleFragment handleFragment = HandleFragment.newInstance(this);
        SupporterFragment supporterFragment = SupporterFragment.newInstance(this);
        switch (taskType) {
            case "01":
                //医疗探视
                followDetailFragment.setArguments(bundle);
                medicalVisitFragment.setArguments(bundle);
                fragmentList.add(medicalVisitFragment);
                fragmentList.add(followDetailFragment);
                MedicalVisit medicalVisit = medicalVisitManager.getData(taskNo);
                commitFlag = (medicalVisit == null) ? "" : medicalVisit.getCommitFlag();
                break;
            case "02":
                //收入情况
                followDetailFragment.setArguments(bundle);
                earningFragment.setArguments(bundle);
                fragmentList.add(earningFragment);
                fragmentList.add(followDetailFragment);
                EarningData earningData = earningDataManager.getData(taskNo);
                commitFlag = (earningData == null) ? "" : earningData.getCommitFlag();
                break;
            case "05":
                //收入情况
                followDetailFragment.setArguments(bundle);
                supporterFragment.setArguments(bundle);
                fragmentList.add(supporterFragment);
                fragmentList.add(followDetailFragment);
                EarningData earningData1 = earningDataManager.getData(taskNo);
                commitFlag = (earningData1 == null) ? "" : earningData1.getCommitFlag();
                break;
            case "06":
                //抚养人情况
                followDetailFragment.setArguments(bundle);
                deathFragment.setArguments(bundle);
                fragmentList.add(deathFragment);
                fragmentList.add(followDetailFragment);
                DeathData deathData = deathDataManager.getData(taskNo);
                commitFlag = (deathData == null) ? "" : deathData.getCommitFlag();
                break;
            case "08":
                //抚养人情况
                followDetailFragment.setArguments(bundle);
                deathFragment.setArguments(bundle);
                fragmentList.add(deathFragment);
                fragmentList.add(followDetailFragment);
                DeathData deathData1 = deathDataManager.getData(taskNo);
                commitFlag = (deathData1 == null) ? "" : deathData1.getCommitFlag();
                break;
            case "09":
                //事故基本情况
                baseInfoFragment.setArguments(bundle);
                followDetailFragment.setArguments(bundle);
                fragmentList.add(baseInfoFragment);
                fragmentList.add(followDetailFragment);
                BaseInfoData baseInfoData = baseInfoDataManager.getData(taskNo);
                commitFlag = (baseInfoData == null) ? "" : baseInfoData.getCommitFlag();
                break;
            case "10":
                //事故处理情况
                handleFragment.setArguments(bundle);
                followDetailFragment.setArguments(bundle);
                fragmentList.add(handleFragment);
                fragmentList.add(followDetailFragment);
                HandleData handleData = handleDataManager.getData(taskNo);
                commitFlag = (handleData == null) ? "" : handleData.getCommitFlag();
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
        segmentTabLayout = (SlidingTabLayout) findViewById(R.id.segmentTabLayout);
        viewPager.setAdapter(adapter);
        segmentTabLayout.setViewPager(viewPager, new String[]{"跟踪记录", "详细资料"});
//        segmentTabLayout.setTabData(new String[]{"跟踪记录", "详细资料"});
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
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.follow_detail_phone:
                //拨打电话
                mAlertView.show();
                break;
            case R.id.task_commit_btn:
                //提交
                commitData();

                break;
            case R.id.floating_button:
                jumpToActivity();
                break;
        }
    }

    private void jumpToActivity() {
        Intent intent=new Intent();
        switch (taskType) {
            case "01":
                intent = new Intent(context, MedicalVisitsActivity.class);
                break;
            case "02":
                intent = new Intent(context, EarningActivity.class);
                break;
            case "03":
                intent = new Intent(context, DelayActivity.class);
                break;
            case "04":
                intent = new Intent(context, HouseHoldsActivity.class);
                break;
            case "05":
                intent = new Intent(context, SupporterActivity.class);
                break;
            case "06":
                intent = new Intent(context, DeathActivity.class);
                break;
            case "08":
                intent = new Intent(context, MaimActivity.class);
                break;
            case "09":
                intent = new Intent(context, FollowEditActivity.class);
                break;
            case "10":
                intent = new Intent(context, HandleActivity.class);
                break;
        }
        intent.putExtra("taskNo", taskNo);
        startActivityForResult(intent, 0x09);
    }

    private void commitData() {
        switch (taskType) {
            case "01":
                CommitUtil.commitMedical(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        Toast toast = Toast.makeText(context, "已成功提交", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setResult(RESULT_OK);
                                finish();
                            }
                        }, 1000);
                    }

                    @Override
                    public void commitFailed() {
                        Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case "02":
                //收入情况
                break;
            case "03":
                //误工情况
                break;
            case "04":
                //户籍信息
                break;
            case "05":
                //被扶养人信息
                break;
            case "06":
                //死亡信息
                break;
            case "07":
                break;
            case "08":
                //伤残信息
                break;
            case "09":
                CommitUtil.commitBaseInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        Toast toast = Toast.makeText(context, "已成功提交", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setResult(RESULT_OK);
                                finish();
                            }
                        }, 1000);
                    }

                    @Override
                    public void commitFailed() {
                        Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case "10":
                //事故处理信息
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
            DetailActivity.this.startActivity(intent);
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
        if (commitFlag.equals("1")) {
            layoutBottom.setVisibility(View.GONE);
            taskTypeImage.setBackground(context.getResources().getDrawable(R.mipmap.detail_finished1));
        } else {
            layoutBottom.setVisibility(View.VISIBLE);
            taskTypeImage.setBackground(context.getResources().getDrawable(R.mipmap.detail_unfinish1));
        }
//        setTaskTypeImage();

    }

    private void setTaskTypeImage() {
        taskTypeImage.setBackground(context.getResources().getDrawable(R.mipmap.detail_finished));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x09:
                    if (data.getStringExtra("commitFlag").equals("1"))
                        commitFlag = "1";
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
