package com.jy.medical.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.activities.EarningActivity;
import com.jy.medical.adapter.ContactEditAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.EarningData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.EarningDataManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.LocalImageHelper;
import com.jy.medical.util.PhotoUtil;
import com.jy.medical.widget.CleanableEditText;
import com.jy.medical.widget.pickerview.TimePickerDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songran on 16/11/28.
 */

public class EarningFragment extends Fragment{
    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private TimePickerDialog mDialogYearMonthDay;
    private SwipeMenuRecyclerView contactRecycler;
    private List<ContactData> contactDataList;
    private ContactEditAdapter adapter;
    private ContactManager contactManager = DaoUtils.getContactInstance();
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private String taskNo;
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组
    DisplayImageOptions options;
    private TextView jobStatus, industryText, completeStatus, entryTime, agreementText, socialText, incomeForm, leaveTime;
    private ImageButton companyLocation;

    private CleanableEditText companyAddress, monthlyIncome, companyName, remarkEdit,restDaysEdit,moneyReduceEdit;
    private Button btnCommit;
    private Button btnSave;
    private View layout1, layout;
    private EarningDataManager earningDataManager = DaoUtils.getEarningDataInstance();
    //    private BaseInfoData baseInfoData;
    private Context context;
    private String industryKey="";
    private static Context mContext;
    private static EarningFragment earningFragment;
    private ImageView earningEdit;
    private View layoutPhoto,layoutJob;
    private int count=0;

    public static EarningFragment newInstance(Context context) {
        mContext = context;
        if (earningFragment == null)
            earningFragment = new EarningFragment();
        return earningFragment;
    }

    public EarningFragment() {
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
        final Bundle data = getArguments();//获得从activity中传递过来的值
        View view = inflater.inflate(R.layout.fragment_earning, container, false);
        earningEdit= (ImageView) view.findViewById(R.id.earning_edit);
        earningEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EarningActivity.class);
                intent.putExtra("taskNo", data.getString("taskNo"));
                startActivity(intent);
            }
        });
        layout1 = view.findViewById(R.id.layout1);
        layout = view.findViewById(R.id.layout);
        layoutPhoto = view.findViewById(R.id.layoutPhoto);
        layoutJob = view.findViewById(R.id.layoutJob);

        jobStatus = (TextView) view.findViewById(R.id.job_status);
        completeStatus = (TextView) view.findViewById(R.id.complete_status);
        industryText = (TextView) view.findViewById(R.id.industry);
        companyName = (CleanableEditText) view.findViewById(R.id.company_name);
        entryTime = (TextView) view.findViewById(R.id.entry_time);
        agreementText = (TextView) view.findViewById(R.id.agreement);
        socialText = (TextView) view.findViewById(R.id.social);
        incomeForm = (TextView) view.findViewById(R.id.income_form);
        leaveTime = (TextView) view.findViewById(R.id.leave_time);

        companyLocation = (ImageButton) view.findViewById(R.id.company_location);
        companyAddress = (CleanableEditText) view.findViewById(R.id.company_address);
        monthlyIncome = (CleanableEditText) view.findViewById(R.id.monthly_income);
        remarkEdit = (CleanableEditText) view.findViewById(R.id.remark_edit);
        pictureRecyclerView = (RecyclerView) view.findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        pictureList = new ArrayList<>();
        list = new ArrayList<>();
        pictureRecyclerView.setLayoutManager(layoutManager);
        contactRecycler = (SwipeMenuRecyclerView) view.findViewById(R.id.contact_recycler);
        contactRecycler.setHasFixedSize(true);
        contactRecycler.setLayoutManager(layoutManager1);
        contactDataList = new ArrayList<>();
        contactDataList = contactManager.selectAllContact(taskNo);
        adapter = new ContactEditAdapter(mContext, contactDataList);
        contactRecycler.setAdapter(adapter);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageForEmptyUri(R.mipmap.dangkr_no_picture_small)
                .showImageOnFail(R.mipmap.dangkr_no_picture_small)
                .showImageOnLoading(R.mipmap.dangkr_no_picture_small)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setPhotoData();
        initOtherData();
    }

    private void initOtherData() {
        EarningData earningData=earningDataManager.getData(taskNo);
        if (earningData == null) {
            layoutJob.setVisibility(View.GONE);
//            layoutRemark.setVisibility(View.GONE);
//            layoutComplete.setVisibility(View.GONE);
            count = 3;
            return;
        }
        jobStatus.setText(earningData.getJobStatusValue());
        industryText.setText(earningData.getIndustryValue());
        completeStatus.setText(earningData.getCompleteStatusValue());
        entryTime.setText(earningData.getEntryTime());
        agreementText.setText(earningData.getAgreementValue());
        socialText.setText(earningData.getSocialValue());
        incomeForm.setText(earningData.getIncomeFormValue());
        leaveTime.setText(earningData.getLeaveTime());
        companyAddress.setText(earningData.getCompanyAddress());
        monthlyIncome.setText(earningData.getMonthlyIncome());
        companyName.setText(earningData.getCompanyName());
        remarkEdit.setText(earningData.getRemark());
//        restDaysEdit.setText(earningData.getJobStatusValue());
//        moneyReduceEdit.setText(earningData.getJobStatusValue());

        if (earningData.getJobStatusValue().equals("")) {
            jobStatus.setVisibility(View.GONE);
            count++;
        } else {
            jobStatus.setVisibility(View.VISIBLE);
        }
    }

    private void setPhotoData() {
        pictureList.clear();
        list.clear();
        taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        for (int i = 0; i < pictureList.size(); i++) {
            Bitmap bmp = PhotoUtil.convertToBitmap(pictureList.get(i).getPhotoPath(), 75, 75);
            list.add(bmp);
        }
        pictureAdapter = new PictureAdapter(mContext, list, taskNo, false,false);
        pictureAdapter.notifyDataSetChanged();
        pictureRecyclerView.setAdapter(pictureAdapter);
        if (pictureList.size() == 0) {
            layoutPhoto.setVisibility(View.GONE);
            count++;
        }else {
            layoutPhoto.setVisibility(View.VISIBLE);
        }
    }
}
