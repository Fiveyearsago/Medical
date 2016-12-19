package com.jy.medical.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.activities.FollowEditActivity;
import com.jy.medical.activities.MedicalVisitsActivity;
import com.jy.medical.adapter.ContactEditAdapter;
import com.jy.medical.adapter.ContactInfoAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PhotoUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseInfoFragment extends Fragment {
    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private static Context mContext;
    private static BaseInfoFragment followRecordFragment;
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private String taskNo;
    private RecyclerView contactRecycler;
    private List<ContactData> contactDataList;
    private ContactInfoAdapter adapter;
    private ContactManager contactManager = DaoUtils.getContactInstance();
    private BaseInfoDataManager baseInfoDataManager = DaoUtils.getBaseInfoDataInstance();
    private TextView textAddress, textTime, textDetail, textRemark, textComplete;
    private View layoutAddress, layoutTime, layoutDetail, layoutRemark, layoutComplete;
    private View layoutAddressInfo, layoutContact, layoutPhoto, layoutOther, layoutEmpty;
    private int countAddress = 0;
    private int countContact = 0;
    private int countPhoto = 0;
    private int countOther = 0;

    public static BaseInfoFragment newInstance(Context context) {
        mContext = context;
        if (followRecordFragment == null)
            followRecordFragment = new BaseInfoFragment();
        return followRecordFragment;
    }

    public BaseInfoFragment() {
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

        View view = inflater.inflate(R.layout.fragment_baseinfo, container, false);

        layoutOther = view.findViewById(R.id.other_data_layout);
        layoutAddressInfo = view.findViewById(R.id.layout_address_info);
        layoutAddress = view.findViewById(R.id.layout_address);
        layoutTime = view.findViewById(R.id.layout_time);
        layoutContact = view.findViewById(R.id.layout_contact);
        layoutDetail = view.findViewById(R.id.layout_detail);
        layoutPhoto = view.findViewById(R.id.layout_photo);
        layoutRemark = view.findViewById(R.id.layout_remark);
        layoutComplete = view.findViewById(R.id.layout_complete);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        textAddress = (TextView) view.findViewById(R.id.accident_address);
        textTime = (TextView) view.findViewById(R.id.accident_time);
        textDetail = (TextView) view.findViewById(R.id.detail_info);
        textRemark = (TextView) view.findViewById(R.id.remark);
        textComplete = (TextView) view.findViewById(R.id.complete_status);
        pictureRecyclerView = (RecyclerView) view.findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        pictureRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        pictureList = new ArrayList<>();
        list = new ArrayList<>();
        contactRecycler = (RecyclerView) view.findViewById(R.id.contact_recyclerView);
        contactRecycler.setHasFixedSize(true);
        contactRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        contactManager = DaoUtils.getContactInstance();
        contactDataList = new ArrayList<>();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        countAddress = 0;
        countContact = 0;
        countPhoto = 0;
        countOther = 0;
        initOtherData();
        setPhotoData();
        setContactData();
        if (countAddress == 3) {
            layoutAddressInfo.setVisibility(View.GONE);
        } else {
            layoutAddressInfo.setVisibility(View.VISIBLE);
        }
        if (countOther == 2) {
            layoutOther.setVisibility(View.GONE);
        } else {
            layoutOther.setVisibility(View.VISIBLE);
        }
        if (countAddress + countContact + countPhoto + countOther == 7) {
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
        }
    }

    private void initOtherData() {
        BaseInfoData baseInfoData = baseInfoDataManager.getData(taskNo);
        if (baseInfoData == null) {
            layoutAddress.setVisibility(View.GONE);
            layoutTime.setVisibility(View.GONE);
            layoutDetail.setVisibility(View.GONE);
            layoutRemark.setVisibility(View.GONE);
            layoutComplete.setVisibility(View.GONE);
            countAddress = 3;
            countOther = 2;
            return;
        }
        textAddress.setText(baseInfoData.getAddress());
        textTime.setText(baseInfoData.getTime());
        textDetail.setText(baseInfoData.getDetailInfo());
        textRemark.setText(baseInfoData.getRemark());
        if (baseInfoData.getCompleteStatus().equals("0"))
            textComplete.setText("已完成");
        else if (baseInfoData.getCompleteStatus().equals("1"))
            textComplete.setText("无法完成");

        if (baseInfoData.getAddress().equals("")) {
            layoutAddress.setVisibility(View.GONE);
            countAddress++;
        } else {
            layoutAddress.setVisibility(View.VISIBLE);
        }
        if (baseInfoData.getTime().equals("")) {
            layoutTime.setVisibility(View.GONE);
            countAddress++;
        } else {
            layoutTime.setVisibility(View.VISIBLE);
        }
        if (baseInfoData.getDetailInfo().equals("")) {
            layoutDetail.setVisibility(View.GONE);
            countAddress++;
        } else {
            layoutDetail.setVisibility(View.VISIBLE);
        }
        if (baseInfoData.getRemark().equals("")) {
            layoutRemark.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutRemark.setVisibility(View.VISIBLE);
        }
        if (baseInfoData.getCompleteStatus().equals("")) {
            layoutComplete.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutComplete.setVisibility(View.VISIBLE);
        }
    }

    private void setContactData() {
        contactDataList = contactManager.selectAllContact(taskNo);
        adapter = new ContactInfoAdapter(mContext, contactDataList);
        contactRecycler.setAdapter(adapter);
        if (contactDataList.size() == 0) {
            layoutContact.setVisibility(View.GONE);
            countContact++;
        } else {
            layoutContact.setVisibility(View.VISIBLE);
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
