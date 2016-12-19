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
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.HandleData;
import com.jy.medical.greendao.entities.HouseholdData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.HandleDataManager;
import com.jy.medical.greendao.manager.HouseholdDataManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PhotoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseholdFragment extends Fragment {
    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private static Context mContext;
    private static HouseholdFragment handleFragment;
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private String taskNo;
    private RecyclerView contactRecycler;
    private List<ContactData> contactDataList;
    private ContactInfoAdapter adapter;
    private ContactManager contactManager = DaoUtils.getContactInstance();
    private HouseholdDataManager householdDataManager = DaoUtils.getHouseholdDataInstance();
    private TextView textCity, textHouseholdType, textFather,textMother,textChildren,textBrothers, textLiveType,textAddress,textYeas,textRemark, textComplete;
    private View layoutCity, layoutHouseholdType, layoutFather,layoutMother,layoutChildren,layoutBrothers, layoutLiveType,layoutAddress,layoutYears,layoutRemark, layoutComplete;
    private View layoutHouseholdInfo,layoutFamilyInfo,layoutLiveInfo, layoutContact, layoutPhoto, layoutOther, layoutEmpty;
    private int countHouse = 0;
    private int countFamily = 0;
    private int countLive = 0;
    private int countContact = 0;
    private int countPhoto = 0;
    private int countOther = 0;

    public static HouseholdFragment newInstance(Context context) {
        mContext = context;
        if (handleFragment == null)
            handleFragment = new HouseholdFragment();
        return handleFragment;
    }

    public HouseholdFragment() {
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

        View view = inflater.inflate(R.layout.fragment_house, container, false);
//        private View layoutCity, layoutHouseholdType, layoutFather,layoutMother,layoutChildren,layoutBrothers, layoutLiveType,layoutAddress,layoutYears,layoutRemark, layoutComplete;
//        private View layoutHouseholdInfo,layoutFamilyInfo,layoutLiveInfo, layoutContact, layoutPhoto, layoutOther, layoutEmpty;

        layoutHouseholdInfo = view.findViewById(R.id.layout_household_info);
        layoutFamilyInfo = view.findViewById(R.id.layout_family_info);
        layoutLiveInfo = view.findViewById(R.id.layout_live_info);
        layoutContact = view.findViewById(R.id.layout_contact);
        layoutPhoto = view.findViewById(R.id.layout_photo);
        layoutOther = view.findViewById(R.id.other_data_layout);
        layoutEmpty = view.findViewById(R.id.layout_empty);

        layoutCity = view.findViewById(R.id.layout_household_city);
        layoutHouseholdType = view.findViewById(R.id.layout_household_type);
        layoutFather = view.findViewById(R.id.layout_father);
        layoutMother = view.findViewById(R.id.layout_mother);
        layoutChildren = view.findViewById(R.id.layout_children);
        layoutBrothers = view.findViewById(R.id.layout_brother);
        layoutLiveType = view.findViewById(R.id.layout_live_type);
        layoutAddress = view.findViewById(R.id.layout_address);
        layoutYears = view.findViewById(R.id.layout_live_years);
        layoutRemark = view.findViewById(R.id.layout_remark);
        layoutComplete = view.findViewById(R.id.layout_complete);


        textCity = (TextView) view.findViewById(R.id.household_city);
        textHouseholdType = (TextView) view.findViewById(R.id.household_type);
        textFather = (TextView) view.findViewById(R.id.father_live);
        textMother = (TextView) view.findViewById(R.id.mother_live);
        textChildren = (TextView) view.findViewById(R.id.children_num);
        textBrothers = (TextView) view.findViewById(R.id.brothers_num);
        textLiveType = (TextView) view.findViewById(R.id.live_type);
        textAddress = (TextView) view.findViewById(R.id.address);
        textYeas = (TextView) view.findViewById(R.id.live_years);
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
        countHouse = 0;
        countFamily = 0;
        countLive = 0;
        countContact = 0;
        countPhoto = 0;
        countOther = 0;
        initOtherData();
        setPhotoData();
        setContactData();
        if (countHouse == 2) {
            layoutHouseholdInfo.setVisibility(View.GONE);
        } else {
            layoutHouseholdInfo.setVisibility(View.VISIBLE);
        }
        if (countFamily == 4) {
            layoutFamilyInfo.setVisibility(View.GONE);
        } else {
            layoutFamilyInfo.setVisibility(View.VISIBLE);
        }
        if (countLive == 3) {
            layoutLiveInfo.setVisibility(View.GONE);
        } else {
            layoutLiveInfo.setVisibility(View.VISIBLE);
        }
        if (countOther == 2) {
            layoutOther.setVisibility(View.GONE);
        } else {
            layoutOther.setVisibility(View.VISIBLE);
        }
        if (countHouse+countFamily+countLive+countContact+countPhoto+countOther == 13) {
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
        }
    }

    private void initOtherData() {
        HouseholdData householdData = householdDataManager.getData(taskNo);
        if (householdData == null) {
            layoutCity.setVisibility(View.GONE);
            layoutHouseholdType.setVisibility(View.GONE);
            layoutFather.setVisibility(View.GONE);
            layoutRemark.setVisibility(View.GONE);
            layoutComplete.setVisibility(View.GONE);
            countHouse = 2;
            countFamily=4;
            countLive=3;
            countOther = 2;
            return;
        }
        textCity.setText(householdData.getHouseholdCityValue());
        textHouseholdType.setText(householdData.getHouseholdTypeValue());
        textFather.setText(householdData.getFatherLiveValue());
        textMother.setText(householdData.getMotherLiveValue());
        textChildren.setText(householdData.getChildrenNum());
        textBrothers.setText(householdData.getBrotherNum());
        textAddress.setText(householdData.getAddress());
        textLiveType.setText(householdData.getHouseholdLiveValue());
        textYeas.setText(householdData.getYears());
        textRemark.setText(householdData.getRemark());
        if (householdData.getCompleteKey().equals("0"))
            textComplete.setText("已完成");
        else if (householdData.getCompleteKey().equals("1"))
            textComplete.setText("无法完成");

        if (householdData.getHouseholdCityKey().equals("")) {
            layoutCity.setVisibility(View.GONE);
            countHouse++;
        } else {
            layoutCity.setVisibility(View.VISIBLE);
        }
        if (householdData.getHouseholdTypeKey().equals("")) {
            layoutHouseholdType.setVisibility(View.GONE);
            countHouse++;
        } else {
            layoutHouseholdType.setVisibility(View.VISIBLE);
        }
        if (householdData.getFatherLiveKey().equals("")) {
            layoutFather.setVisibility(View.GONE);
            countFamily++;
        } else {
            layoutFather.setVisibility(View.VISIBLE);
        }
        if (householdData.getMotherLiveKey().equals("")) {
            layoutMother.setVisibility(View.GONE);
            countFamily++;
        } else {
            layoutMother.setVisibility(View.VISIBLE);
        }
        if (householdData.getChildrenNum().equals("")) {
            layoutChildren.setVisibility(View.GONE);
            countFamily++;
        } else {
            layoutChildren.setVisibility(View.VISIBLE);
        }
        if (householdData.getBrotherNum().equals("")) {
            layoutBrothers.setVisibility(View.GONE);
            countFamily++;
        } else {
            layoutBrothers.setVisibility(View.VISIBLE);
        }
        if (householdData.getHouseholdLiveKey().equals("")) {
            layoutLiveType.setVisibility(View.GONE);
            countLive++;
        } else {
            layoutLiveType.setVisibility(View.VISIBLE);
        }
        if (householdData.getAddress().equals("")) {
            layoutAddress.setVisibility(View.GONE);
            countLive++;
        } else {
            layoutAddress.setVisibility(View.VISIBLE);
        }
        if (householdData.getYears().equals("")) {
            layoutYears.setVisibility(View.GONE);
            countLive++;
        } else {
            layoutYears.setVisibility(View.VISIBLE);
        }

        if (householdData.getRemark().equals("")) {
            layoutRemark.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutRemark.setVisibility(View.VISIBLE);
        }
        if (householdData.getCompleteKey().equals("")) {
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
