package com.jy.medical.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.activities.DelayActivity;
import com.jy.medical.activities.MedicalVisitsActivity;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.adapter.SelectedDiagnoseAdapter;
import com.jy.medical.adapter.SelectedHospitalAdapter;
import com.jy.medical.adapter.SelectedNursingAdapter;
import com.jy.medical.greendao.entities.MedicalVisit;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.greendao.entities.SelectedDiagnose;
import com.jy.medical.greendao.entities.SelectedHospital;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.MedicalVisitManager;
import com.jy.medical.greendao.manager.NursingDataManager;
import com.jy.medical.greendao.manager.SelectedDiagnoseManager;
import com.jy.medical.greendao.manager.SelectedHospitalManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PhotoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalVisitFragment extends Fragment {
    private static MedicalVisitFragment followRecordFragment;
    private RecyclerView pictureRecyclerView, hospitalRecyclerView, diagnoseRecyclerView, nursingRecyclerView;
    private SelectedHospitalAdapter selectedHospitalAdapter;
    private SelectedNursingAdapter selectedNursingAdapter;
    private SelectedDiagnoseAdapter selectedDiagnoseAdapter;
    private List<SelectedHospital> selectedHospitals;
    private List<NursingData> nursingDataList;
    private List<SelectedDiagnose> selectedDiagnoseList;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private MedicalVisitManager medicalVisitManager=DaoUtils.getMedicalVisitInstance();
    private SelectedHospitalManager selectedHospitalManager = DaoUtils.getSelectedHospitaInstance();
    private SelectedDiagnoseManager selectedDiagnoseManager = DaoUtils.getSelectedDiagnoseInstance();
    private NursingDataManager nursingDataManager = DaoUtils.getNursingDataInstance();
    private String taskNo;
    private static Context mContext;
    private View layoutHospital, layoutDiagnose, layoutNursing, layoutFee, layoutPhoto, layoutRemark, layoutComplete, layoutOther,layoutEmpty;
    private TextView textFee, textRemark, textComplete;
    private int count = 0;
    private int countOther = 0;
    public static MedicalVisitFragment newInstance(Context context) {
        mContext = context;
        if (followRecordFragment == null)
            followRecordFragment = new MedicalVisitFragment();
        return followRecordFragment;
    }

    public MedicalVisitFragment() {
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
        View view = inflater.inflate(R.layout.fragment_medical_visit, container, false);
        layoutOther = view.findViewById(R.id.other_data_layout);
        layoutHospital = view.findViewById(R.id.layout_hospital);
        layoutDiagnose = view.findViewById(R.id.layout_diagnose);
        layoutNursing = view.findViewById(R.id.layout_nursing);
        layoutFee = view.findViewById(R.id.layout_fee);
        layoutPhoto = view.findViewById(R.id.layout_photo);
        layoutRemark = view.findViewById(R.id.layout_remark);
        layoutComplete = view.findViewById(R.id.layout_complete);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        textFee = (TextView) view.findViewById(R.id.text_fee);
        textRemark = (TextView) view.findViewById(R.id.remark);
        textComplete = (TextView) view.findViewById(R.id.complete_status);
        pictureRecyclerView = (RecyclerView) view.findViewById(R.id.picture_recyclerView);
        hospitalRecyclerView = (RecyclerView) view.findViewById(R.id.hospital_recycler);
        diagnoseRecyclerView = (RecyclerView) view.findViewById(R.id.diagnose_recycler);
        nursingRecyclerView = (RecyclerView) view.findViewById(R.id.nursing_recycler);
        pictureRecyclerView.setHasFixedSize(true);
        hospitalRecyclerView.setHasFixedSize(true);
        diagnoseRecyclerView.setHasFixedSize(true);
        nursingRecyclerView.setHasFixedSize(true);
        hospitalRecyclerView.setItemAnimator(new DefaultItemAnimator());
        pictureRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        hospitalRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        diagnoseRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        nursingRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        pictureList = new ArrayList<>();
        list = new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        count=0;
        countOther=0;
        initOtherData();
        setPhotoData();
        initHospitalData();
        initDiagnoseData();
        initNursingData();
        if (countOther == 2) {
            layoutOther.setVisibility(View.GONE);
        } else {
            layoutOther.setVisibility(View.VISIBLE);
        }
        if (count == 7) {
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
        }
    }
    public void initHospitalData() {
        selectedHospitals = selectedHospitalManager.getDataList(taskNo);
        selectedHospitalAdapter = new SelectedHospitalAdapter(selectedHospitals, mContext);
        hospitalRecyclerView.setAdapter(selectedHospitalAdapter);
        if (selectedHospitals.size()==0){
            count++;
            layoutHospital.setVisibility(View.GONE);
        }else {
            layoutHospital.setVisibility(View.VISIBLE);
        }
    }

    public void initDiagnoseData() {
        selectedDiagnoseList = selectedDiagnoseManager.getDataList(taskNo);
        selectedDiagnoseAdapter = new SelectedDiagnoseAdapter(selectedDiagnoseList, mContext);
        diagnoseRecyclerView.setAdapter(selectedDiagnoseAdapter);
        if (selectedDiagnoseList.size()==0){
            count++;
            layoutDiagnose.setVisibility(View.GONE);
        }else {
            layoutDiagnose.setVisibility(View.VISIBLE);
        }
    }

    public void initNursingData() {
        nursingDataList = nursingDataManager.getDataList(taskNo);
        selectedNursingAdapter = new SelectedNursingAdapter(nursingDataList, mContext);
        nursingRecyclerView.setAdapter(selectedNursingAdapter);
        if (nursingDataList.size()==0){
            count++;
            layoutNursing.setVisibility(View.GONE);
        }else {
            layoutNursing.setVisibility(View.VISIBLE);
        }
    }


    private void initOtherData() {
        MedicalVisit medicalVisit = medicalVisitManager.getData(taskNo);
        if (medicalVisit == null) {
            layoutFee.setVisibility(View.GONE);
            layoutRemark.setVisibility(View.GONE);
            layoutComplete.setVisibility(View.GONE);
            count = 3;
            countOther=2;
            return;
        }
        textFee.setText("￥"+medicalVisit.getMedicalFee());
        textRemark.setText(medicalVisit.getRemark());
        if (medicalVisit.getCompleteStatus().equals("0"))
            textComplete.setText("已完成");
        else if (medicalVisit.getCompleteStatus().equals("1"))
            textComplete.setText("无法完成");

        if (medicalVisit.getMedicalFee().equals("")) {
            layoutFee.setVisibility(View.GONE);
            count++;
        } else {
            layoutFee.setVisibility(View.VISIBLE);
        }
        if (medicalVisit.getRemark().equals("")) {
            layoutRemark.setVisibility(View.GONE);
            count++;
        } else {
            layoutRemark.setVisibility(View.VISIBLE);
        }
        if (medicalVisit.getCompleteStatus().equals("")) {
            layoutComplete.setVisibility(View.GONE);
            count++;
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
