package com.jy.medical.fragment;


import android.content.Context;
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
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.adapter.ContactInfoAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.adapter.SelectedMaimGradeAdapter;
import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.MaimData;
import com.jy.medical.greendao.entities.MaimGradeData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.MaimDataManager;
import com.jy.medical.greendao.manager.MaimGradeDataManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PhotoUtil;

import java.util.ArrayList;
import java.util.List;

public class MaimFragment extends Fragment {
    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private static Context mContext;
    private static MaimFragment maimFragment;
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private String taskNo;
    private RecyclerView miamRecycler;
    private List<MaimGradeData> maimGradeDataList=new ArrayList<>();
    private SelectedMaimGradeAdapter adapter;
    private MaimGradeDataManager maimGradeDataManager=DaoUtils.getMaimGradeDataInstance();
    private MaimDataManager maimDataManager = DaoUtils.getMaimDataInstance();
    private TextView textApproval, textPerson, textCoefficient,textDescribe, textRemark, textComplete;
    private View layoutApproval, layoutPerson, layoutCoefficient,layoutDescribe, layoutRemark, layoutComplete;
    private View layoutApprovalInfo, layoutMaim, layoutPhoto, layoutOther, layoutEmpty;
    private int countApproval = 0;
    private int countMaim = 0;
    private int countPhoto = 0;
    private int countOther = 0;

    public static MaimFragment newInstance(Context context) {
        mContext = context;
        if (maimFragment == null)
            maimFragment = new MaimFragment();
        return maimFragment;
    }

    public MaimFragment() {
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

        View view = inflater.inflate(R.layout.fragment_maim, container, false);

        layoutOther = view.findViewById(R.id.other_data_layout);
        layoutApprovalInfo = view.findViewById(R.id.layout_approval_info);
        layoutApproval = view.findViewById(R.id.layout_approval);
        layoutPerson = view.findViewById(R.id.layout_person);
        layoutMaim = view.findViewById(R.id.layout_maim);
        layoutCoefficient = view.findViewById(R.id.layout_coefficient);
        layoutDescribe = view.findViewById(R.id.layout_describe);
        layoutPhoto = view.findViewById(R.id.layout_photo);
        layoutRemark = view.findViewById(R.id.layout_remark);
        layoutComplete = view.findViewById(R.id.layout_complete);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        textApproval = (TextView) view.findViewById(R.id.approval_department);
        textPerson = (TextView) view.findViewById(R.id.approval_person);
        textCoefficient = (TextView) view.findViewById(R.id.pay_coefficient);
        textDescribe = (TextView) view.findViewById(R.id.describe);
        textRemark = (TextView) view.findViewById(R.id.remark);
        textComplete = (TextView) view.findViewById(R.id.complete_status);
        pictureRecyclerView = (RecyclerView) view.findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        pictureRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        pictureList = new ArrayList<>();
        list = new ArrayList<>();
        miamRecycler = (RecyclerView) view.findViewById(R.id.maim_recyclerView);
        miamRecycler.setHasFixedSize(true);
        miamRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        countApproval = 0;
        countMaim = 0;
        countPhoto = 0;
        countOther = 0;
        initOtherData();
        setPhotoData();
        setMaimData();
        if (countApproval == 4) {
            layoutApprovalInfo.setVisibility(View.GONE);
        } else {
            layoutApprovalInfo.setVisibility(View.VISIBLE);
        }
        if (countOther == 2) {
            layoutOther.setVisibility(View.GONE);
        } else {
            layoutOther.setVisibility(View.VISIBLE);
        }
        if (countApproval + countMaim + countPhoto + countOther == 8) {
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
        }
    }

    private void initOtherData() {
        MaimData maimData = maimDataManager.getData(taskNo);
        if (maimData == null) {
            layoutApproval.setVisibility(View.GONE);
            layoutPerson.setVisibility(View.GONE);
            layoutCoefficient.setVisibility(View.GONE);
            layoutDescribe.setVisibility(View.GONE);
            layoutRemark.setVisibility(View.GONE);
            layoutComplete.setVisibility(View.GONE);
            countApproval = 4;
            countOther = 2;
            return;
        }
        textApproval.setText(maimData.getApprovalDepartmentValue());
        textPerson.setText(maimData.getApprovalPerson());
        textCoefficient.setText(maimData.getPayCoefficient()+"%");
        textDescribe.setText(maimData.getDescribe());
        textRemark.setText(maimData.getRemark());
        if (maimData.getCompleteStatus().equals("0"))
            textComplete.setText("已完成");
        else if (maimData.getCompleteStatus().equals("1"))
            textComplete.setText("无法完成");

        if (maimData.getApprovalDepartmentValue().equals("")) {
            layoutApproval.setVisibility(View.GONE);
            countApproval++;
        } else {
            layoutApproval.setVisibility(View.VISIBLE);
        }
        if (maimData.getApprovalPerson().equals("")) {
            layoutPerson.setVisibility(View.GONE);
            countApproval++;
        } else {
            layoutPerson.setVisibility(View.VISIBLE);
        }
        if (maimData.getPayCoefficient().equals("")) {
            layoutCoefficient.setVisibility(View.GONE);
            countApproval++;
        } else {
            layoutCoefficient.setVisibility(View.VISIBLE);
        }
        if (maimData.getDescribe().equals("")) {
            layoutDescribe.setVisibility(View.GONE);
            countApproval++;
        } else {
            layoutDescribe.setVisibility(View.VISIBLE);
        }
        if (maimData.getRemark().equals("")) {
            layoutRemark.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutRemark.setVisibility(View.VISIBLE);
        }
        if (maimData.getCompleteStatus().equals("")) {
            layoutComplete.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutComplete.setVisibility(View.VISIBLE);
        }
    }

    private void setMaimData() {
        maimGradeDataList = maimGradeDataManager.getDataList(taskNo);
        adapter = new SelectedMaimGradeAdapter( maimGradeDataList,mContext);
        miamRecycler.setAdapter(adapter);
        if (maimGradeDataList.size() == 0) {
            layoutMaim.setVisibility(View.GONE);
            countMaim++;
        } else {
            layoutMaim.setVisibility(View.VISIBLE);
        }
    }

    private void setPhotoData() {
        pictureList.clear();
        list.clear();
        taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        for (int i = 0; i < pictureList.size(); i++) {
//            Bitmap bmp = PhotoUtil.convertToBitmap(pictureList.get(i).getPhotoPath(), 150, 150);
            Bitmap bmp = PhotoUtil.getNativeImage(pictureList.get(i).getPhotoPath());
            list.add(bmp);
        }
        pictureAdapter = new PictureAdapter(mContext, list, taskNo, false, false);
        pictureAdapter.notifyDataSetChanged();
        pictureRecyclerView.setAdapter(pictureAdapter);
        if (pictureList.size() == 0) {
            layoutPhoto.setVisibility(View.GONE);
            countPhoto++;
        } else {
            layoutPhoto.setVisibility(View.VISIBLE);
        }
    }
}
