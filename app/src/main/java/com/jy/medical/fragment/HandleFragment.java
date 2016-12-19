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
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.activities.FollowEditActivity;
import com.jy.medical.activities.HandleActivity;
import com.jy.medical.adapter.ContactInfoAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.greendao.entities.HandleData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.HandleDataManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PhotoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HandleFragment extends Fragment {
    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private static Context mContext;
    private static HandleFragment handleFragment;
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private String taskNo;
    private RecyclerView contactRecycler;
    private List<ContactData> contactDataList;
    private ContactInfoAdapter adapter;
    private ContactManager contactManager = DaoUtils.getContactInstance();
    private HandleDataManager handleDataManager = DaoUtils.getHandleDataInstance();
    private TextView textName, textTime, textResult, textRemark, textComplete;
    private View layoutName, layoutTime, layoutResult, layoutRemark, layoutComplete;
    private View layoutPersonInfo, layoutContact, layoutPhoto, layoutOther, layoutEmpty;
    private int countPerson = 0;
    private int countContact = 0;
    private int countPhoto = 0;
    private int countOther = 0;

    public static HandleFragment newInstance(Context context) {
        mContext = context;
        if (handleFragment == null)
            handleFragment = new HandleFragment();
        return handleFragment;
    }

    public HandleFragment() {
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

        View view = inflater.inflate(R.layout.fragment_handle, container, false);
        layoutPersonInfo = view.findViewById(R.id.layout_person_info);
        layoutOther = view.findViewById(R.id.other_data_layout);
        layoutName = view.findViewById(R.id.layout_name);
        layoutTime = view.findViewById(R.id.layout_time);
        layoutContact = view.findViewById(R.id.layout_contact);
        layoutResult = view.findViewById(R.id.layout_result);
        layoutPhoto = view.findViewById(R.id.layout_photo);
        layoutRemark = view.findViewById(R.id.layout_remark);
        layoutComplete = view.findViewById(R.id.layout_complete);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        textName = (TextView) view.findViewById(R.id.handle_name);
        textTime = (TextView) view.findViewById(R.id.handle_time);
        textResult = (TextView) view.findViewById(R.id.handle_result);
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
        countPerson = 0;
        countContact = 0;
        countPhoto = 0;
        countOther = 0;
        initOtherData();
        setPhotoData();
        setContactData();
        if (countPerson == 3) {
            layoutPersonInfo.setVisibility(View.GONE);
        } else {
            layoutPersonInfo.setVisibility(View.VISIBLE);
        }
        if (countOther == 2) {
            layoutOther.setVisibility(View.GONE);
        } else {
            layoutOther.setVisibility(View.VISIBLE);
        }
        if (countPerson+countContact+countPhoto+countOther == 7) {
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
        }
    }

    private void initOtherData() {
        HandleData handleData = handleDataManager.getData(taskNo);
        if (handleData == null) {
            layoutName.setVisibility(View.GONE);
            layoutTime.setVisibility(View.GONE);
            layoutResult.setVisibility(View.GONE);
            layoutRemark.setVisibility(View.GONE);
            layoutComplete.setVisibility(View.GONE);
            countPerson = 3;
            countOther = 2;
            return;
        }
        textName.setText(handleData.getHandleName());
        textTime.setText(handleData.getHandleTime());
        textResult.setText(handleData.getHandleResult());
        textRemark.setText(handleData.getRemark());
        if (handleData.getCompleteStatus().equals("0"))
            textComplete.setText("已完成");
        else if (handleData.getCompleteStatus().equals("1"))
            textComplete.setText("无法完成");

        if (handleData.getHandleName().equals("")) {
            layoutName.setVisibility(View.GONE);
            countPerson++;
        } else {
            layoutName.setVisibility(View.VISIBLE);
        }
        if (handleData.getHandleTime().equals("")) {
            layoutTime.setVisibility(View.GONE);
            countPerson++;
        } else {
            layoutTime.setVisibility(View.VISIBLE);
        }
        if (handleData.getHandleResult().equals("")) {
            layoutResult.setVisibility(View.GONE);
            countPerson++;
        } else {
            layoutResult.setVisibility(View.VISIBLE);
        }
        if (handleData.getRemark().equals("")) {
            layoutRemark.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutRemark.setVisibility(View.VISIBLE);
        }
        if (handleData.getCompleteStatus().equals("")) {
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
//            Bitmap bmp = PhotoUtil.convertToBitmap(pictureList.get(i).getPhotoPath(), 75, 75);
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
