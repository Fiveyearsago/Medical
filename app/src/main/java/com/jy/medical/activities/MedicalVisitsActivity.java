package com.jy.medical.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.SelectedDiagnoseAdapter;
import com.jy.medical.adapter.SelectedHospitalAdapter;
import com.jy.medical.adapter.SelectedNursingAdapter;
import com.jy.medical.controller.JsonToBean;
import com.jy.medical.greendao.entities.MedicalVisit;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.greendao.entities.SelectedDiagnose;
import com.jy.medical.greendao.entities.SelectedHospital;
import com.jy.medical.greendao.manager.MedicalVisitManager;
import com.jy.medical.greendao.manager.NursingDataManager;
import com.jy.medical.greendao.manager.SelectedDiagnoseManager;
import com.jy.medical.greendao.manager.SelectedHospitalManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.inter.OnItemClickListener;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.util.SwipeMenuUtil;
import com.jy.medical.widget.CleanableEditText;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.dto.DisabilityDescrDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.dto.NursePersonDTO;
import com.jy.mobile.request.QTInspectHospitalInfoDTO;
import com.jy.mobile.response.SpRecieveTaskDTO;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class MedicalVisitsActivity extends BaseActivity {
    private SwipeMenuRecyclerView hospitalRecyclerView;
    private SwipeMenuRecyclerView diagnoseRecyclerView;
    private SwipeMenuRecyclerView nursingRecyclerView;
    private SelectedHospitalAdapter selectedHospitalAdapter;
    private SelectedNursingAdapter selectedNursingAdapter;
    private SelectedDiagnoseAdapter selectedDiagnoseAdapter;
    private List<SelectedHospital> selectedHospitals;
    private List<NursingData> nursingDataList;
    private List<SelectedDiagnose> selectedDiagnoseList;
    private Context mContext;
    private String taskNo;
    private SelectedHospitalManager selectedHospitalManager = DaoUtils.getSelectedHospitaInstance();
    private SelectedDiagnoseManager selectedDiagnoseManager = DaoUtils.getSelectedDiagnoseInstance();
    private NursingDataManager nursingDataManager = DaoUtils.getNursingDataInstance();
    private MedicalVisitManager medicalVisitManager = DaoUtils.getMedicalVisitInstance();
    private TextView completeStatus;
    private CleanableEditText nursingFeeEdit;
    private CleanableEditText nursingRemarkEdit;
    private Button btnCommit;
    private Button btnSave;
    private MedicalVisit medicalVisit;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_medical_visits;
    }

    @Override
    public void initParms(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        mContext = this;
        setStatusBarTint();
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        completeStatus = (TextView) findViewById(R.id.complete_status_text);
        nursingFeeEdit = (CleanableEditText) findViewById(R.id.nursing_fee);
        nursingRemarkEdit = (CleanableEditText) findViewById(R.id.nursing_remark);
        btnCommit = (Button) findViewById(R.id.visit_edit_commit);
        btnSave = (Button) findViewById(R.id.visit_edit_save);
        btnCommit.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        selectedHospitals = new ArrayList<>();
        selectedDiagnoseList = new ArrayList<>();
        nursingDataList = new ArrayList<>();
        completeStatus.setOnClickListener(this);
        findViewById(R.id.add_hospital).setOnClickListener(this);
        findViewById(R.id.add_nurse).setOnClickListener(this);
        findViewById(R.id.add_diagnose).setOnClickListener(this);
        hospitalRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.hospital_recycler_view);
        diagnoseRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.diagnose_recycler_view);
        nursingRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.nursing_recycler_view);

        hospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hospitalRecyclerView.setHasFixedSize(true);
        hospitalRecyclerView.setItemAnimator(new DefaultItemAnimator());
        hospitalRecyclerView.setSwipeMenuCreator(SwipeMenuUtil.getSwipeMenuEdit(this));
        hospitalRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        selectedHospitalAdapter = new SelectedHospitalAdapter(selectedHospitals, this);
        selectedHospitalAdapter.setOnItemClickListener(onItemClickListener);
        hospitalRecyclerView.setAdapter(selectedHospitalAdapter);

        diagnoseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        diagnoseRecyclerView.setHasFixedSize(true);
        diagnoseRecyclerView.setItemAnimator(new DefaultItemAnimator());
        diagnoseRecyclerView.setSwipeMenuCreator(SwipeMenuUtil.getSwipeMenuEdit(this));
        diagnoseRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener1);
        selectedDiagnoseAdapter = new SelectedDiagnoseAdapter(selectedDiagnoseList, this);
        selectedDiagnoseAdapter.setOnItemClickListener(onItemClickListener1);
        diagnoseRecyclerView.setAdapter(selectedDiagnoseAdapter);

        nursingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        nursingRecyclerView.setHasFixedSize(true);
        nursingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        nursingRecyclerView.setSwipeMenuCreator(SwipeMenuUtil.getSwipeMenuDelete(this));
        nursingRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener2);
        selectedNursingAdapter = new SelectedNursingAdapter(nursingDataList, this);
//        selectedNursingAdapter.setOnItemClickListener(onItemClickListener1);
        diagnoseRecyclerView.setAdapter(selectedNursingAdapter);


    }

    public void initHospitalData() {
        selectedHospitals = selectedHospitalManager.getDataList(taskNo);
        selectedHospitalAdapter = new SelectedHospitalAdapter(selectedHospitals, this);
        selectedHospitalAdapter.setOnItemClickListener(onItemClickListener);
        hospitalRecyclerView.setAdapter(selectedHospitalAdapter);
    }

    public void initDiagnoseData() {
        selectedDiagnoseList = selectedDiagnoseManager.getDataList(taskNo);
        selectedDiagnoseAdapter = new SelectedDiagnoseAdapter(selectedDiagnoseList, this);
        selectedDiagnoseAdapter.setOnItemClickListener(onItemClickListener1);
        diagnoseRecyclerView.setAdapter(selectedDiagnoseAdapter);
    }

    public void initNursingData() {
        nursingDataList = nursingDataManager.getDataList(taskNo);
        selectedNursingAdapter = new SelectedNursingAdapter(nursingDataList, this);
        nursingRecyclerView.setAdapter(selectedNursingAdapter);
    }

    public void initOtherData() {
        List<MedicalVisit>medicalVisitList=medicalVisitManager.getDataList(taskNo);
        if (medicalVisitList.size()<1)
            return;
        medicalVisit = medicalVisitList.get(0);
        if (medicalVisit.getCompleteStatus().equals("0")) {
            completeStatus.setText("已完成");
        } else if (medicalVisit.getCompleteStatus().equals("1")) {
            completeStatus.setText("无法完成");
        }
        nursingFeeEdit.setText(medicalVisit.getMedicalFee());
        nursingRemarkEdit.setText(medicalVisit.getRemark());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHospitalData();
        initDiagnoseData();
        initNursingData();
        initOtherData();
    }

    @Override
    public void widgetClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("taskNo", taskNo);
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                //保存编辑信息
                saveData();
                break;
            case R.id.visit_edit_commit:
                //保存编辑信息
                saveData();
                commitData();
                break;
            case R.id.visit_edit_save:
                //保存编辑信息
                saveData();
                break;
            case R.id.add_hospital:
                //选择医院
                bundle.putString("taskNo", taskNo);
                startActivity(SelectHospitalActivity.class, bundle);
                break;
            case R.id.add_nurse:
                //添加护理人
                startActivity(AddNursingActivity.class, bundle);
                break;
            case R.id.add_diagnose:
                //添加护理人
                startActivity(AddDiagnoseActivity.class, bundle);
                break;
            case R.id.complete_status_text:
                //选择完成情况
                Intent intent = new Intent(this, SelectCompleteActivity.class);
                startActivityForResult(intent, 0x11);
                break;
        }
    }

    private void commitData() {
        saveData();
        medicalVisit=medicalVisitManager.getDataList(taskNo).get(0);
        QTInspectHospitalInfoDTO qtInspectHospitalInfoDTO=new QTInspectHospitalInfoDTO();
        qtInspectHospitalInfoDTO.setTaskNo(taskNo);
        qtInspectHospitalInfoDTO.setFeePass(Double.parseDouble(medicalVisit.getMedicalFee()));
        qtInspectHospitalInfoDTO.setFinishFlag(medicalVisit.getCompleteStatus());
        qtInspectHospitalInfoDTO.setRemark(medicalVisit.getRemark());
        qtInspectHospitalInfoDTO.setUserCode("0131002498");
        HosptialDTO hosptialDTO=new HosptialDTO();
        selectedHospitals = selectedHospitalManager.getDataList(taskNo);
        selectedDiagnoseList = selectedDiagnoseManager.getDataList(taskNo);
        nursingDataList = nursingDataManager.getDataList(taskNo);
        SelectedHospital selectedHospital=selectedHospitals.get(0);
        hosptialDTO.setHospitalProperty(selectedHospital.getHospitalName());
        hosptialDTO.setHospitalDepartment(selectedHospital.getDepartmentId());
        hosptialDTO.setHospitalId(selectedHospital.getHospitalId());
        qtInspectHospitalInfoDTO.setHospital(hosptialDTO);
        List<DisabilityDescrDTO> dList=new ArrayList<>();
        for (int i = 0; i < selectedDiagnoseList.size(); i++) {
            SelectedDiagnose selectDiagnose=selectedDiagnoseList.get(i);
            DisabilityDescrDTO d=new DisabilityDescrDTO();
            d.setId(selectDiagnose.getDiagnoseId());
            d.setDisabilityCode(selectDiagnose.getDiagnoseCode());
            d.setDisabilityDescr(selectDiagnose.getDiagnoseName());
            d.setOperation(selectDiagnose.getTreatmentMode());
            dList.add(d);
        }
        qtInspectHospitalInfoDTO.setDisabList(dList);

        List<NursePersonDTO> nList=new ArrayList<>();
        for (int i = 0; i < nursingDataList.size(); i++) {
            NursePersonDTO n=new NursePersonDTO();
            NursingData nursingData=nursingDataList.get(i);
            n.setNurseDayCount(Integer.parseInt(nursingData.getDays()));
            n.setNurseName(nursingData.getName());
            n.setNurseIndustryName(nursingData.getIdValue());
            n.setNurseIndustryCode(nursingData.getIdKey());
            n.setNurseDailyReceipts(Double.parseDouble(nursingData.getFee()));
            nList.add(n);

        }
        qtInspectHospitalInfoDTO.setNurseList(nList);
        Gson gson = new Gson();
        String data = gson.toJson(qtInspectHospitalInfoDTO);
        ServerApiUtils.sendToServer(data, "002007", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    //标记已提交
                    medicalVisit.setCommitFlag("1");
                    medicalVisitManager.insertSingleData(medicalVisit);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void saveData() {
        String nursingFee = nursingFeeEdit.getText().toString();
        String nursingRemark = nursingRemarkEdit.getText().toString();
        String completeText = completeStatus.getText().toString().equals("已完成") ? "0" : "1";
        medicalVisitManager.insertSingleData(new MedicalVisit(taskNo, nursingFee, nursingRemark, completeText,""));
    }

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                selectedHospitalManager.deleteSingleData(selectedHospitals.get(adapterPosition));
                selectedHospitals.remove(adapterPosition);
                selectedHospitalAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private OnSwipeMenuItemClickListener menuItemClickListener1 = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                selectedDiagnoseManager.deleteSingleData(selectedDiagnoseList.get(adapterPosition));
                selectedDiagnoseList.remove(adapterPosition);
                selectedDiagnoseAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private OnSwipeMenuItemClickListener menuItemClickListener2 = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {

                } else {
                    nursingDataManager.deleteSingleData(nursingDataList.get(adapterPosition));
                    nursingDataList.remove(adapterPosition);
                    selectedNursingAdapter.notifyDataSetChanged();
                }
                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
        }
    };
    private OnItemClickListener onItemClickListener1 = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x11:
                    completeStatus.setText(data.getStringExtra("status"));
                    saveData();
                    break;
            }
        }
    }
}
