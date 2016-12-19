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
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.activities.SupporterActivity;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.adapter.SelectedSupporterAdapter;
import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.SupporterData;
import com.jy.medical.greendao.entities.SupporterPerson;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.SupporterDataManager;
import com.jy.medical.greendao.manager.SupporterPersonManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PhotoUtil;

import java.util.ArrayList;
import java.util.List;

public class SupporterFragment extends Fragment {
    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private static Context mContext;
    private static SupporterFragment supporterFragment;
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private String taskNo;
    private RecyclerView supporterRecycler;
    private List<SupporterPerson> supporterDataList;
    private SelectedSupporterAdapter adapter;
    private SupporterPersonManager supporterManager = DaoUtils.getSupporterPersonInstance();
    private SupporterDataManager supporterDataManager = DaoUtils.getSupporterDataInstance();
    private TextView textPay, textFee, textRemark, textComplete;
    private View layoutOther,layoutPayInfo,layoutPay, layoutFee, layoutSupporter, layoutPhoto, layoutRemark, layoutComplete, layoutEmpty;

    private int countPhoto = 0;
    private int countPay = 0;
    private int countSupporter = 0;
    private int countOther = 0;

    public static SupporterFragment newInstance(Context context) {
        mContext = context;
        if (supporterFragment == null)
            supporterFragment = new SupporterFragment();
        return supporterFragment;
    }

    public SupporterFragment() {
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

        View view = inflater.inflate(R.layout.fragment_supporter1, container, false);
        layoutPayInfo = view.findViewById(R.id.layout_pay_info);
        layoutOther = view.findViewById(R.id.other_data_layout);
        layoutPay = view.findViewById(R.id.layout_pay);
        layoutFee = view.findViewById(R.id.layout_fee);
        layoutSupporter = view.findViewById(R.id.layout_supporter);
        layoutPhoto = view.findViewById(R.id.layout_photo);
        layoutRemark = view.findViewById(R.id.layout_remark);
        layoutComplete = view.findViewById(R.id.layout_complete);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        textPay = (TextView) view.findViewById(R.id.supporter_pay);
        textFee = (TextView) view.findViewById(R.id.supporter_fee);
        textRemark = (TextView) view.findViewById(R.id.remark);
        textComplete = (TextView) view.findViewById(R.id.complete_status);
        pictureRecyclerView = (RecyclerView) view.findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        pictureRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        pictureList = new ArrayList<>();
        list = new ArrayList<>();
        supporterRecycler = (RecyclerView) view.findViewById(R.id.supporter_recyclerView);
        supporterRecycler.setHasFixedSize(true);
        supporterRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        supporterDataList = new ArrayList<>();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        countPhoto = 0;
        countPay = 0;
        countSupporter = 0;
        countOther = 0;
        initOtherData();
        setPhotoData();
        setSupporterPerson();

        if (countPay==2){
            layoutPayInfo.setVisibility(View.GONE);
        }else {
            layoutPayInfo.setVisibility(View.VISIBLE);
        }
        if (countOther==2){
            layoutOther.setVisibility(View.GONE);
        }else {
            layoutOther.setVisibility(View.VISIBLE);
        }
        if (countPhoto+countPay+countSupporter+countOther == 6) {
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
        }
    }

    private void initOtherData() {
        SupporterData supporterData = supporterDataManager.getData(taskNo);
        if (supporterData == null) {
            layoutPay.setVisibility(View.GONE);
            layoutFee.setVisibility(View.GONE);
            layoutRemark.setVisibility(View.GONE);
            layoutComplete.setVisibility(View.GONE);
            countOther = 2;
            countPay=2;
            return;
        }
        textPay.setText(supporterData.getPayCoefficient());
        textFee.setText(supporterData.getMaintenanceFee());
        textRemark.setText(supporterData.getRemark());
        if (supporterData.getCompleteStatus().equals("0"))
            textComplete.setText("已完成");
        else if (supporterData.getCompleteStatus().equals("1"))
            textComplete.setText("无法完成");

        if (supporterData.getPayCoefficient().equals("")) {
            layoutPay.setVisibility(View.GONE);
            countPay++;
        } else {
            layoutPay.setVisibility(View.VISIBLE);
        }
        if (supporterData.getMaintenanceFee().equals("")) {
            layoutFee.setVisibility(View.GONE);
            countPay++;
        } else {
            layoutFee.setVisibility(View.VISIBLE);
        }
        if (supporterData.getRemark().equals("")) {
            layoutRemark.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutRemark.setVisibility(View.VISIBLE);
        }
        if (supporterData.getCompleteStatus().equals("")) {
            layoutComplete.setVisibility(View.GONE);
            countOther++;
        } else {
            layoutComplete.setVisibility(View.VISIBLE);
        }
    }

    private void setSupporterPerson() {
        supporterDataList = supporterManager.selectAllContact(taskNo);
        adapter = new SelectedSupporterAdapter(mContext, supporterDataList);
        supporterRecycler.setAdapter(adapter);
        if (supporterDataList.size() == 0) {
            layoutSupporter.setVisibility(View.GONE);
            countSupporter++;
        }else {
            layoutSupporter.setVisibility(View.VISIBLE);
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
        pictureAdapter = new PictureAdapter(mContext, list, taskNo, false,false);
        pictureAdapter.notifyDataSetChanged();
        pictureRecyclerView.setAdapter(pictureAdapter);
        if (pictureList.size() == 0) {
            layoutPhoto.setVisibility(View.GONE);
            countPhoto++;
        }else {
            layoutPhoto.setVisibility(View.VISIBLE);
        }
    }
}
