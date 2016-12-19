package com.jy.medical.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.activities.DeathActivity;
import com.jy.medical.adapter.ContactInfoAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.DeathData;
import com.jy.medical.greendao.entities.DelayData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.DeathDataManager;
import com.jy.medical.greendao.manager.DelayDataManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PhotoUtil;

import java.util.ArrayList;
import java.util.List;

public class DelayFragment extends Fragment {
    private RecyclerView pictureRecyclerView;
    private RecyclerView contactRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<ContactData> contactList;
    private ContactInfoAdapter contactAdapter;
    private List<Bitmap> list;
    private static Context mContext;
    private static DelayFragment delayFragment;
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private ContactManager contactManager = DaoUtils.getContactInstance();
    private DelayDataManager delayDataManager = DaoUtils.getDelayDataInstance();
    private String taskNo;
    private TextView textJobStatus, textMonthlyIncome, textSocial, textAgreement, textIncomeForm, textIndustry, textCompanyName, textCompanyAddress, textEntryTime,textLeaveTime, textRestDays, textMoneyReduce, textRemark, textComplete;
    private View jobStatusLayout, monthlyIncomeLayout, socialLayout, agreementLayout, incomeFormLayout, industryLayout, companyNameLayout, companyAddressLayout, entryTimeLayout,leaveTimeLayout, restDaysLayout, moneyReduceLayout, layoutRemark, layoutComplete;
    private View delayLayout, otherLayout, photoLayout, contactLayout,contactLayoutTab, jobLayout, layoutEmpty;
    private LinearLayout linearLayout;
    private int countDelayInfo = 0;//误工信息
    private int countOther = 0;
    private int countMovie = 0;
    private int countContact = 0;
    private int countJob = 0;//就业信息
    private int countJobLeave = 0;//就业信息

    public static DelayFragment newInstance(Context context) {
        mContext = context;
        if (delayFragment == null)
            delayFragment = new DelayFragment();
        return delayFragment;
    }

    public DelayFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle data = getArguments();//获得从activity中传递过来的值
        taskNo = data.getString("taskNo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delay, container, false);
        delayLayout = view.findViewById(R.id.layout_delay_info);
        otherLayout = view.findViewById(R.id.other_data_layout);
        photoLayout = view.findViewById(R.id.layout_photo);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        contactLayout = view.findViewById(R.id.layout_contact);
        contactLayoutTab = view.findViewById(R.id.layout_contact_tab);
        jobLayout = view.findViewById(R.id.layout_job);
        linearLayout= (LinearLayout) view.findViewById(R.id.layout_contact);
        // ,,,,,,,,,,, ;

        jobStatusLayout = view.findViewById(R.id.layout_job_status);
        monthlyIncomeLayout = view.findViewById(R.id.layout_monthly_income);
        socialLayout = view.findViewById(R.id.layout_social);
        agreementLayout = view.findViewById(R.id.layout_agreement);
        incomeFormLayout = view.findViewById(R.id.layout_income_form);
        industryLayout = view.findViewById(R.id.layout_industry);

        companyNameLayout = view.findViewById(R.id.layout_company_name);
        companyAddressLayout = view.findViewById(R.id.layout_company_address);
        entryTimeLayout = view.findViewById(R.id.layout_entry_time);
        leaveTimeLayout = view.findViewById(R.id.layout_leave_time);

        restDaysLayout = view.findViewById(R.id.layout_rest_days);
        moneyReduceLayout = view.findViewById(R.id.layout_money_reduce);

        layoutRemark = view.findViewById(R.id.layout_remark);
        layoutComplete = view.findViewById(R.id.layout_complete);


        textJobStatus = (TextView) view.findViewById(R.id.text_job_status);
        textMonthlyIncome = (TextView) view.findViewById(R.id.text_monthly_income);
        textSocial = (TextView) view.findViewById(R.id.text_social);
        textAgreement = (TextView) view.findViewById(R.id.text_agreement);
        textIncomeForm = (TextView) view.findViewById(R.id.text_income_form);
        textIndustry = (TextView) view.findViewById(R.id.text_industry);
        textCompanyName = (TextView) view.findViewById(R.id.text_company_name);
        textCompanyAddress = (TextView) view.findViewById(R.id.text_company_address);
        textEntryTime = (TextView) view.findViewById(R.id.text_entry_time);
        textLeaveTime = (TextView) view.findViewById(R.id.text_leave_time);
        textRestDays = (TextView) view.findViewById(R.id.text_rest_days);
        textMoneyReduce = (TextView) view.findViewById(R.id.text_money_reduce);
        textRemark = (TextView) view.findViewById(R.id.remark);
        textComplete = (TextView) view.findViewById(R.id.complete_status);


        pictureRecyclerView = (RecyclerView) view.findViewById(R.id.picture_recyclerView);
        contactRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        contactRecyclerView.setHasFixedSize(true);
        pictureRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        pictureList = new ArrayList<>();
        contactList = new ArrayList<>();
        list = new ArrayList<>();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        countDelayInfo = 0;
        countOther = 0;
        countMovie = 0;
        countContact=0;
        countJob=0;
        countJobLeave=0;
        bindingData();
        if ( countJob== 9||countJobLeave== 2) {
            jobLayout.setVisibility(View.GONE);
        } else {
            jobLayout.setVisibility(View.VISIBLE);
        }

        if (countDelayInfo == 2) {
            delayLayout.setVisibility(View.GONE);
        } else {
            delayLayout.setVisibility(View.VISIBLE);
        }
//        if (countContact == 1) {
//            contactLayout.setVisibility(View.GONE);
//            contactLayoutTab.setVisibility(View.GONE);
//        } else {
//            contactLayout.setVisibility(View.VISIBLE);
//            contactLayoutTab.setVisibility(View.VISIBLE);
//        }
        if (countMovie == 1) {
            photoLayout.setVisibility(View.GONE);
        } else {
            photoLayout.setVisibility(View.VISIBLE);
        }
        if (countOther == 2) {
            otherLayout.setVisibility(View.GONE);
        } else {
            otherLayout.setVisibility(View.VISIBLE);
        }
        //6+(2/9)
        if (countDelayInfo + countOther + countMovie+countContact+countJob == 15 ||countDelayInfo + countOther + countMovie+countJobLeave==7) {
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
        }
    }

    private void bindingData() {
        setPhotoData();
        setContactData();
        initOtherData();
    }

    private void setContactData() {
        contactList = contactManager.selectAllContact(taskNo);
        contactAdapter = new ContactInfoAdapter(mContext, contactList);
        contactRecyclerView.setAdapter(contactAdapter);
        if (contactList.size() == 0) {
            contactLayout.setVisibility(View.GONE);
            countContact++;
        } else {
            contactLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initOtherData() {
        DelayData delayData = delayDataManager.getData(taskNo);
//  delayLayout,otherLayout,photoLayout,contactLayout,jobLayout,layoutEmpty;

        if (delayData == null) {
            jobStatusLayout.setVisibility(View.GONE);
            monthlyIncomeLayout.setVisibility(View.GONE);
            socialLayout.setVisibility(View.GONE);
            agreementLayout.setVisibility(View.GONE);
            incomeFormLayout.setVisibility(View.GONE);
            industryLayout.setVisibility(View.GONE);
            companyNameLayout.setVisibility(View.GONE);
            companyAddressLayout.setVisibility(View.GONE);
            entryTimeLayout.setVisibility(View.GONE);
            leaveTimeLayout.setVisibility(View.GONE);

            restDaysLayout.setVisibility(View.GONE);
            moneyReduceLayout.setVisibility(View.GONE);
            
            layoutRemark.setVisibility(View.GONE);
            layoutComplete.setVisibility(View.GONE);
            
            countDelayInfo = 2;
            countJob=9;
            countOther = 2;
            return;
        }



        textJobStatus.setText(delayData.getJobStatusValue());
        textMonthlyIncome.setText("￥"+delayData.getMonthlyIncome());
        textSocial.setText(delayData.getSocialValue());
        textAgreement.setText(delayData.getAgreementValue());
        textIncomeForm.setText(delayData.getIncomeFormValue());
        textIndustry.setText(delayData.getIndustryValue());
        textCompanyName.setText(delayData.getCompanyName());
        textCompanyAddress.setText(delayData.getCompanyAddress());
        textEntryTime.setText(delayData.getEntryTime());
        textLeaveTime.setText(delayData.getLeaveTime());
        textRestDays.setText(delayData.getRestDays()+"天");
        textMoneyReduce.setText("￥"+delayData.getMoneyReduce());
        textRemark.setText(delayData.getRemark());
        textComplete.setText(delayData.getCompleteStatusValue());
// ,,,,,,,,entryTimeLayout,,,layoutRemark, layoutComplete;
        if (delayData.getJobStatusValue().equals("")) {
            jobStatusLayout.setVisibility(View.GONE);
            countJob++;
            countJobLeave++;
        } else {
            jobStatusLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getMonthlyIncome().equals("")) {
            monthlyIncomeLayout.setVisibility(View.GONE);
            countJob++;
        } else {
            monthlyIncomeLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getSocialValue().equals("")) {
            socialLayout.setVisibility(View.GONE);
            countJob++;
        } else {
            socialLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getAgreementValue().equals("")) {
            agreementLayout.setVisibility(View.GONE);
            countJob++;
        } else {
            agreementLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getIncomeFormValue().equals("")) {
            incomeFormLayout.setVisibility(View.GONE);
            countJob++;
        } else {
            incomeFormLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getIndustryValue().equals("")) {
            industryLayout.setVisibility(View.GONE);
            countJob++;
        } else {
            industryLayout.setVisibility(View.VISIBLE);
        }

        if (delayData.getCompanyName().equals("")) {
            companyNameLayout.setVisibility(View.GONE);
            countJob++;
        } else {
            companyNameLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getCompanyAddress().equals("")) {
            companyAddressLayout.setVisibility(View.GONE);
            countJob++;
        } else {
            companyAddressLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getEntryTime().equals("")) {
            entryTimeLayout.setVisibility(View.GONE);
            countJob++;
        } else {
            entryTimeLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getLeaveTime().equals("")) {
            leaveTimeLayout.setVisibility(View.GONE);
            countJobLeave++;
        } else {
            leaveTimeLayout.setVisibility(View.VISIBLE);
        }

        if (delayData.getJobStatusKey().equals("1")){
            monthlyIncomeLayout.setVisibility(View.GONE);
            socialLayout.setVisibility(View.GONE);
            agreementLayout.setVisibility(View.GONE);
            incomeFormLayout.setVisibility(View.GONE);
            industryLayout.setVisibility(View.GONE);
            companyNameLayout.setVisibility(View.GONE);
            companyAddressLayout.setVisibility(View.GONE);
            entryTimeLayout.setVisibility(View.GONE);
            if (delayData.getLeaveTime().equals("")) {
                leaveTimeLayout.setVisibility(View.GONE);
                countJobLeave++;
            } else {
                leaveTimeLayout.setVisibility(View.VISIBLE);
            }
            contactLayout.setVisibility(View.GONE);

        }
        else {
//            monthlyIncomeLayout.setVisibility(View.VISIBLE);
//            socialLayout.setVisibility(View.VISIBLE);
//            agreementLayout.setVisibility(View.VISIBLE);
//            incomeFormLayout.setVisibility(View.VISIBLE);
//            industryLayout.setVisibility(View.VISIBLE);
//            companyNameLayout.setVisibility(View.VISIBLE);
//            companyAddressLayout.setVisibility(View.VISIBLE);
//            entryTimeLayout.setVisibility(View.VISIBLE);
            leaveTimeLayout.setVisibility(View.GONE);
        }
        if (delayData.getRestDays().equals("")) {
            restDaysLayout.setVisibility(View.GONE);
            countDelayInfo++;
        } else {
            restDaysLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getMoneyReduce().equals("")) {
            moneyReduceLayout.setVisibility(View.GONE);
            countDelayInfo++;
        } else {
            moneyReduceLayout.setVisibility(View.VISIBLE);
        }
        if (delayData.getRemark().equals("")) {
            layoutRemark.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutRemark.setVisibility(View.VISIBLE);
        }
        if (delayData.getCompleteStatusValue().equals("")) {
            layoutComplete.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutComplete.setVisibility(View.VISIBLE);
        }

    }


    private void setPhotoData() {
        pictureList.clear();
        list.clear();
        taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        for (int i = 0; i < pictureList.size(); i++) {
//            Bitmap bmp = PhotoUtil.convertToBitmap(pictureList.get(i).getPhotoPath(), 75, 75);
            Bitmap bmp = PhotoUtil.getNativeImage(pictureList.get(i).getPhotoPath());

            list.add(bmp);
        }
        pictureAdapter = new PictureAdapter(mContext, list, taskNo, false, false);
        pictureAdapter.notifyDataSetChanged();
        pictureRecyclerView.setAdapter(pictureAdapter);
        if (pictureList.size() == 0) {
            photoLayout.setVisibility(View.GONE);
            countMovie++;
        } else {
            photoLayout.setVisibility(View.VISIBLE);
        }
    }
}
