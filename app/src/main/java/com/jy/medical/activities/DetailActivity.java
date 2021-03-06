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
import android.widget.ScrollView;
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
import com.jy.medical.fragment.DelayFragment;
import com.jy.medical.fragment.EarningFragment;
import com.jy.medical.fragment.EarningFragment1;
import com.jy.medical.fragment.FollowDetailFragment;
import com.jy.medical.fragment.HandleFragment;
import com.jy.medical.fragment.HouseholdFragment;
import com.jy.medical.fragment.MaimFragment;
import com.jy.medical.fragment.MedicalVisitFragment;
import com.jy.medical.fragment.SupporterFragment;
import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.DeathData;
import com.jy.medical.greendao.entities.EarningData;
import com.jy.medical.greendao.entities.HandleData;
import com.jy.medical.greendao.entities.MaimData;
import com.jy.medical.greendao.entities.MedicalVisit;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.greendao.entities.SupporterData;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.DeathDataManager;
import com.jy.medical.greendao.manager.EarningDataManager;
import com.jy.medical.greendao.manager.HandleDataManager;
import com.jy.medical.greendao.manager.MaimDataManager;
import com.jy.medical.greendao.manager.MedicalVisitManager;
import com.jy.medical.greendao.manager.SupporterDataManager;
import com.jy.medical.greendao.manager.TaskManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.CommitUtil;
import com.jy.medical.util.TimeUtil;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.CustomViewpager;
import com.jy.medical.widget.CustomerScrollView;
import com.jy.medical.widget.SwipeBackLayout;

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
    private SupporterDataManager supporterDataManager = DaoUtils.getSupporterDataInstance();
    private MaimDataManager maimDataManager = DaoUtils.getMaimDataInstance();
    private View layoutBottom;
    private Context context;
    private ImageView taskTypeImage;
    private FloatingActionButton floatingActionButton;
    private String taskNo;
    private TextView taskTypeTV;
    private CustomerScrollView scrollView;

    @Override
    public void initData() {
        context = this;
        textName.setText(platformData.getPeopleName());
        textTime.setText(TimeUtil.getTimeNoSecondsString(platformData.getTime()));
//        textReport.setText(platformData.getReportNum());
        dayNum = TimeUtil.getGapCount(platformData.getTime());
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
        TaskManager taskManager = DaoUtils.getTaskInstance();
        commitFlag = taskManager.getCommitFlag(taskNo);
    }

    @Override
    public void initView() {

        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "跟踪详情", false, "");
        findViewById(R.id.task_commit_btn).setOnClickListener(this);
        scrollView = (CustomerScrollView) findViewById(R.id.scrollView);
        taskTypeImage = (ImageView) findViewById(R.id.task_state_image);
        taskTypeTV = (TextView) findViewById(R.id.task_type);
        setTaskTypeText();
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
        EarningFragment1 earningFragment = EarningFragment1.newInstance(this);
        DeathFragment deathFragment = DeathFragment.newInstance(this);
        BaseInfoFragment baseInfoFragment = BaseInfoFragment.newInstance(this);
        HandleFragment handleFragment = HandleFragment.newInstance(this);
        SupporterFragment supporterFragment = SupporterFragment.newInstance(this);
        DelayFragment delayFragment = DelayFragment.newInstance(this);
        MaimFragment maimFragment = MaimFragment.newInstance(this);
        HouseholdFragment householdFragment = HouseholdFragment.newInstance(this);
        switch (taskType) {
            case "01":
                //医疗探视
                followDetailFragment.setArguments(bundle);
                medicalVisitFragment.setArguments(bundle);
                fragmentList.add(medicalVisitFragment);
                fragmentList.add(followDetailFragment);
                break;
            case "02":
                //收入情况
                followDetailFragment.setArguments(bundle);
                earningFragment.setArguments(bundle);
                fragmentList.add(earningFragment);
                fragmentList.add(followDetailFragment);
                break;
            case "03":
                //误入情况
                followDetailFragment.setArguments(bundle);
                delayFragment.setArguments(bundle);
                fragmentList.add(delayFragment);
                fragmentList.add(followDetailFragment);
                break;
            case "04":
                //误入情况
                followDetailFragment.setArguments(bundle);
                householdFragment.setArguments(bundle);
                fragmentList.add(householdFragment);
                fragmentList.add(followDetailFragment);
                break;
            case "05":
                //收入情况
                followDetailFragment.setArguments(bundle);
                supporterFragment.setArguments(bundle);
                fragmentList.add(supporterFragment);
                fragmentList.add(followDetailFragment);
                break;
            case "06":
                //死亡信息
                followDetailFragment.setArguments(bundle);
                deathFragment.setArguments(bundle);
                fragmentList.add(deathFragment);
                fragmentList.add(followDetailFragment);
                break;
            case "08":
                //伤残
                followDetailFragment.setArguments(bundle);
                maimFragment.setArguments(bundle);
                fragmentList.add(maimFragment);
                fragmentList.add(followDetailFragment);
                break;
            case "09":
                //事故基本情况
                baseInfoFragment.setArguments(bundle);
                followDetailFragment.setArguments(bundle);
                fragmentList.add(baseInfoFragment);
                fragmentList.add(followDetailFragment);
                break;
            case "10":
                //事故处理情况
                handleFragment.setArguments(bundle);
                followDetailFragment.setArguments(bundle);
                fragmentList.add(handleFragment);
                fragmentList.add(followDetailFragment);
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
                if (position == 0 && !commitFlag.equals("1")) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    floatingActionButton.setVisibility(View.GONE);
                }
                viewPager.resetHeight(position);
                segmentTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);

    }

    private void setTaskTypeText() {
        switch (taskType) {
            case "01":
                taskTypeTV.setText("医");
                break;
            case "02":
                taskTypeTV.setText("薪");
                break;
            case "03":
                taskTypeTV.setText("误");
                break;
            case "04":
                taskTypeTV.setText("籍");
                break;
            case "05":
                taskTypeTV.setText("扶");
                break;
            case "06":
                taskTypeTV.setText("死");
                break;
            case "07":
                taskTypeTV.setText("医");
                break;
            case "08":
                taskTypeTV.setText("残");
                break;
            case "09":
                taskTypeTV.setText("基");
                break;
            case "10":
                taskTypeTV.setText("处");
                break;
        }
//        scrollView.fullScroll(ScrollView.FOCUS_UP);
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
        Intent intent = new Intent();
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
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;
            case "02":
                //收入情况
                CommitUtil.commitEarningInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;
            case "03":
                //误工情况
                CommitUtil.commitDelayInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;
            case "04":
                //户籍信息
                CommitUtil.commitHouseHoldsInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;
            case "05":
                //被扶养人信息
                CommitUtil.commitSupporterInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;
            case "06":
                //死亡信息
                CommitUtil.commitDeathInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;
            case "07":
                CommitUtil.commitDelayInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;
            case "08":
                //伤残信息
                CommitUtil.commitMaimInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;
            case "09":
                CommitUtil.commitBaseInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;
            case "10":
                //事故处理信息
                CommitUtil.commitHandleInfo(this, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        ToastUtil.showToast(context, "已成功提交");
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
                        ToastUtil.showToast(context, "提交失败");
                    }
                });
                break;

        }
    }

    public void setTaskTimeText(int dayNum) {
        if (dayNum < 0) {
            SpannableString styledText = new SpannableString("已超时" + (-dayNum) + "天");
            int length = 3 + String.valueOf(-dayNum).length();
            styledText.setSpan(new TextAppearanceSpan(this, R.style.textTimeoutTab), 3, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            taskTimeTab.setText(styledText, TextView.BufferType.SPANNABLE);
        } else {
            int length = String.valueOf(dayNum + 1).length();
            SpannableString styledText = new SpannableString((dayNum + 1) + "天后超时");
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

        if (commitFlag.equals("1")) {
            layoutBottom.setVisibility(View.GONE);
            taskTypeImage.setBackground(context.getResources().getDrawable(R.mipmap.detail_finished1));
            floatingActionButton.setVisibility(View.GONE);
        } else {
            if (dayNum < 0) {
                taskTypeImage.setBackground(context.getResources().getDrawable(R.mipmap.detail_timeout1));
            } else {
                taskTypeImage.setBackground(context.getResources().getDrawable(R.mipmap.detail_unfinish1));

            }
            layoutBottom.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
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
