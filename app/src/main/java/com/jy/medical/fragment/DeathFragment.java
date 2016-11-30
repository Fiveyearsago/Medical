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
import com.jy.medical.activities.DeathActivity;
import com.jy.medical.activities.FollowEditActivity;
import com.jy.medical.adapter.ContactInfoAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.greendao.entities.DeathData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.DeathDataManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PhotoUtil;

import java.util.ArrayList;
import java.util.List;

public class DeathFragment extends Fragment {
    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private static Context mContext;
    private static DeathFragment followRecordFragment;
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private String taskNo;
    private DeathDataManager deathDataManager = DaoUtils.getDeathDataInstance();
    private TextView textReason, textAddress, textTime, textParticipation, textRemark, textComplete;
    private View layoutAddress, layoutTime, layoutReason, layoutParticipation, layoutPhoto, layoutRemark, layoutComplete, layoutEmpty;

    private int count = 0;
    private ImageView deathEdit;

    public static DeathFragment newInstance(Context context) {
        mContext = context;
        if (followRecordFragment == null)
            followRecordFragment = new DeathFragment();
        return followRecordFragment;
    }

    public DeathFragment() {
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

        View view = inflater.inflate(R.layout.fragment_death, container, false);
        layoutAddress = view.findViewById(R.id.layout_address);
        layoutTime = view.findViewById(R.id.layout_time);
        layoutReason = view.findViewById(R.id.layout_reason);
        layoutParticipation = view.findViewById(R.id.layout_participation);
        layoutPhoto = view.findViewById(R.id.layout_photo);
        layoutRemark = view.findViewById(R.id.layout_remark);
        layoutComplete = view.findViewById(R.id.layout_complete);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        textReason = (TextView) view.findViewById(R.id.death_reason);
        textAddress = (TextView) view.findViewById(R.id.death_address);
        textTime = (TextView) view.findViewById(R.id.death_time);
        textParticipation = (TextView) view.findViewById(R.id.death_participation);
        textRemark = (TextView) view.findViewById(R.id.remark);
        textComplete = (TextView) view.findViewById(R.id.complete_status);
        deathEdit = (ImageView) view.findViewById(R.id.death_edit);
        deathEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeathActivity.class);
                intent.putExtra("taskNo", taskNo);
                ((AppCompatActivity) mContext).startActivityForResult(intent, 0x09);
            }
        });
        pictureRecyclerView = (RecyclerView) view.findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        pictureRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        pictureList = new ArrayList<>();
        list = new ArrayList<>();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        count = 0;
        initOtherData();
        setPhotoData();

        if (count == 7) {
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
        }
    }

    private void initOtherData() {
        DeathData deathData = deathDataManager.getData(taskNo);
        if (deathData == null) {
            layoutReason.setVisibility(View.GONE);
            layoutAddress.setVisibility(View.GONE);
            layoutTime.setVisibility(View.GONE);
            layoutParticipation.setVisibility(View.GONE);
            layoutRemark.setVisibility(View.GONE);
            layoutComplete.setVisibility(View.GONE);
            count = 6;
            return;
        }
        textAddress.setText(deathData.getDeathAddress());
        textTime.setText(deathData.getDeathTime());
        textParticipation.setText(deathData.getParticipation());
        textRemark.setText(deathData.getRemark());
        if (deathData.getDeathReason().equals("0"))
            textReason.setText("损伤导致");
        else if (deathData.getDeathReason().equals("1"))
            textReason.setText("损伤与疾病共同导致");
        else if (deathData.getDeathReason().equals("2"))
            textReason.setText("疾病导致");

        if (deathData.getCompleteStatus().equals("0"))
            textComplete.setText("已完成");
        else if (deathData.getCompleteStatus().equals("1"))
            textComplete.setText("无法完成");

        if (deathData.getDeathReason().equals("")) {
            layoutReason.setVisibility(View.GONE);
            count++;
        } else {
            layoutReason.setVisibility(View.VISIBLE);
        }
        if (deathData.getDeathAddress().equals("")) {
            layoutAddress.setVisibility(View.GONE);
            count++;
        } else {
            layoutAddress.setVisibility(View.VISIBLE);
        }
        if (deathData.getDeathTime().equals("")) {
            layoutTime.setVisibility(View.GONE);
            count++;
        } else {
            layoutTime.setVisibility(View.VISIBLE);
        }
        if (deathData.getParticipation().equals("")) {
            layoutParticipation.setVisibility(View.GONE);
            count++;
        } else {
            layoutParticipation.setVisibility(View.VISIBLE);
        }
        if (deathData.getRemark().equals("")) {
            layoutRemark.setVisibility(View.GONE);
            count++;
        } else {
            layoutRemark.setVisibility(View.VISIBLE);
        }
        if (deathData.getCompleteStatus().equals("")) {
            layoutComplete.setVisibility(View.GONE);
            count++;
        } else {
            layoutComplete.setVisibility(View.VISIBLE);
        }
        if (deathData.getCommitFlag().equals("1")) {
            deathEdit.setVisibility(View.GONE);
        } else {
            deathEdit.setVisibility(View.VISIBLE);
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
        pictureAdapter = new PictureAdapter(mContext, list, taskNo, false, false);
        pictureAdapter.notifyDataSetChanged();
        pictureRecyclerView.setAdapter(pictureAdapter);
        if (pictureList.size() == 0) {
            layoutPhoto.setVisibility(View.GONE);
            count++;
        } else {
            layoutPhoto.setVisibility(View.VISIBLE);
        }
    }
}
