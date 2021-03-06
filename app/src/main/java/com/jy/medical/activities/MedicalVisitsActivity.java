package com.jy.medical.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.adapter.SelectedDiagnoseAdapter;
import com.jy.medical.adapter.SelectedHospitalAdapter;
import com.jy.medical.adapter.SelectedNursingAdapter;
import com.jy.medical.controller.JsonToBean;
import com.jy.medical.greendao.entities.MedicalVisit;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.greendao.entities.SelectedDiagnose;
import com.jy.medical.greendao.entities.SelectedHospital;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.MedicalVisitManager;
import com.jy.medical.greendao.manager.NursingDataManager;
import com.jy.medical.greendao.manager.SelectedDiagnoseManager;
import com.jy.medical.greendao.manager.SelectedHospitalManager;
import com.jy.medical.greendao.manager.TaskManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.inter.OnItemClickListener;
import com.jy.medical.util.CommitUtil;
import com.jy.medical.util.ImageUtils;
import com.jy.medical.util.LocalImageHelper;
import com.jy.medical.util.MultiSelectUtil;
import com.jy.medical.util.PhotoUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.util.StringUtils;
import com.jy.medical.util.SwipeMenuUtil;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.FilterImageView;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.dto.DisabilityDescrDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.dto.NursePersonDTO;
import com.jy.mobile.request.QTInspectHospitalInfoDTO;
import com.jy.mobile.response.SpRecieveTaskDTO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;

import java.io.File;
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
    private ClearEditText nursingFeeEdit;
    private ClearEditText nursingRemarkEdit;
    private Button btnCommit;
    private Button btnSave;
    private MedicalVisit medicalVisit;

    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private TaskPhotoManager taskPhotoManager;
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组
    DisplayImageOptions options;


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_medical_visits;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        mContext = this;
        setStatusBarTint();
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        completeStatus = (TextView) findViewById(R.id.complete_status_text);
        nursingFeeEdit = (ClearEditText) findViewById(R.id.nursing_fee);
        nursingRemarkEdit = (ClearEditText) findViewById(R.id.nursing_remark);
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
        hospitalRecyclerView.setAdapter(selectedHospitalAdapter);

        diagnoseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        diagnoseRecyclerView.setHasFixedSize(true);
        diagnoseRecyclerView.setItemAnimator(new DefaultItemAnimator());
        diagnoseRecyclerView.setSwipeMenuCreator(SwipeMenuUtil.getSwipeMenuEdit(this));
        diagnoseRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener1);
        selectedDiagnoseAdapter = new SelectedDiagnoseAdapter(selectedDiagnoseList, this);
        diagnoseRecyclerView.setAdapter(selectedDiagnoseAdapter);

        nursingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        nursingRecyclerView.setHasFixedSize(true);
        nursingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        nursingRecyclerView.setSwipeMenuCreator(SwipeMenuUtil.getSwipeMenuDelete(this));
        nursingRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener2);
        selectedNursingAdapter = new SelectedNursingAdapter(nursingDataList, this);
        diagnoseRecyclerView.setAdapter(selectedNursingAdapter);

        pictureRecyclerView = (RecyclerView) findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        pictureList=new ArrayList<>();
        list = new ArrayList<>();
        pictureRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageForEmptyUri(R.mipmap.dangkr_no_picture_small)
                .showImageOnFail(R.mipmap.dangkr_no_picture_small)
                .showImageOnLoading(R.mipmap.dangkr_no_picture_small)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();

    }
    private void setPhotoData() {
        pictureList.clear();
        list.clear();
        taskPhotoManager=DaoUtils.getTaskPhotoInstance();
        pictureList=taskPhotoManager.selectAllPhoto(taskNo);
        for (int i = 0; i < pictureList.size(); i++) {
            if (pictureList.get(i).getPhotoPath()==null||pictureList.get(i).getPhotoPath().equals(""))
                break;
//            Bitmap bmp = PhotoUtil.convertToBitmap(pictureList.get(i).getPhotoPath(),75,75);
            Bitmap bmp = PhotoUtil.getNativeImage(pictureList.get(i).getPhotoPath());

            list.add(bmp);
        }
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.add_photo);
        list.add(bmp);
        pictureAdapter = new PictureAdapter(this, list,taskNo,true,true);
        pictureAdapter.notifyDataSetChanged();
        pictureRecyclerView.setAdapter(pictureAdapter);
    }
    public void initHospitalData() {
        selectedHospitals = selectedHospitalManager.getDataList(taskNo);
        selectedHospitalAdapter = new SelectedHospitalAdapter(selectedHospitals, this);
//        selectedHospitalAdapter.setOnItemClickListener(onItemClickListener);
        hospitalRecyclerView.setAdapter(selectedHospitalAdapter);
    }

    public void initDiagnoseData() {
        selectedDiagnoseList = selectedDiagnoseManager.getDataList(taskNo);
        selectedDiagnoseAdapter = new SelectedDiagnoseAdapter(selectedDiagnoseList, this);
//        selectedDiagnoseAdapter.setOnItemClickListener(onItemClickListener1);
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
        setPhotoData();
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
                Toast.makeText(this,"已保存所有信息",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.visit_edit_commit:
                //保存编辑信息
                saveData();
                CommitUtil.commitMedical(mContext, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {
                        Intent intent = new Intent();
                        intent.putExtra("commitFlag", "1");
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void commitFailed() {

                    }
                });

                break;
            case R.id.visit_edit_save:
                //保存编辑信息
                saveData();
                finish();
                break;
            case R.id.add_hospital:
                //选择医院 flag为1
                bundle.putString("taskNo", taskNo);
                bundle.putString("flag", "1");
                bundle.putString("dealLocalCode", "1");
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
                MultiSelectUtil.selectStatus(mContext, completeStatus, new String[]{"已完成", "无法完成"}, "选择完成情况");

                break;
        }
    }

    private void commitMedicalData() {
        saveData();
        CommitUtil.commitMedical(this, taskNo, new CommitUtil.CommitCallBack() {
            @Override
            public void commitSuccess() {
                Toast toast= Toast.makeText(MedicalVisitsActivity.this, "已成功提交", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1000);
            }

            @Override
            public void commitFailed() {
                Toast.makeText(MedicalVisitsActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void saveData() {
        String nursingFee = nursingFeeEdit.getText().toString();
        String nursingRemark = nursingRemarkEdit.getText().toString();
        String completeText = completeStatus.getText().toString().equals("已完成") ? "0" : "1";
        medicalVisitManager.insertSingleData(new MedicalVisit(taskNo, nursingFee, nursingRemark, completeText,""));
        TaskManager taskManager=DaoUtils.getTaskInstance();
        taskManager.updateIsDongingFlag(taskNo,"1");
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
            }
        }
    };
    private OnSwipeMenuItemClickListener menuItemClickListener2 = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {
                    //编辑
                    Bundle bundle = new Bundle();
                    bundle.putString("taskNo", taskNo);
                    bundle.putString("nameString", nursingDataList.get(adapterPosition).getName());
                    bundle.putString("daysString", nursingDataList.get(adapterPosition).getDays());
                    bundle.putString("feeString", nursingDataList.get(adapterPosition).getFee());
                    bundle.putString("idKey", nursingDataList.get(adapterPosition).getIdKey());
                    bundle.putString("idValue", nursingDataList.get(adapterPosition).getIdValue());
                    bundle.putString("idTypeCode", nursingDataList.get(adapterPosition).getIdTypeCode());
                    bundle.putLong("Id", nursingDataList.get(adapterPosition).getId());
                    startActivity(AddNursingActivity.class, bundle);
                } else {
                    nursingDataManager.deleteSingleData(nursingDataList.get(adapterPosition));
                    nursingDataList.remove(adapterPosition);
                    selectedNursingAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                    String cameraPath = LocalImageHelper.getInstance().getCameraImgPath();
                    if (StringUtils.isEmpty(cameraPath)) {
                        Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File file = new File(cameraPath);

                    if (file.exists()) {
                        taskPhotoManager.insertSingleData(new TaskPhoto(taskNo,cameraPath));
                    } else {
                        Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                    if (LocalImageHelper.getInstance().isResultOk()) {
                        LocalImageHelper.getInstance().setResultOk(false);
                        //获取选中的图片
                        List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                        List<TaskPhoto> newDatas=new ArrayList<>();
                        for (int i = 0; i < files.size(); i++) {
                            String photoPath=ImageUtils.getAbsoluteImagePath(this,Uri.parse(files.get(i).getOriginalUri()));
                            newDatas.add(new TaskPhoto(taskNo,photoPath ));
//                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(75, 75);
////                        params.rightMargin = padding;
//                            FilterImageView imageView = new FilterImageView(this);
//                            imageView.setLayoutParams(params);
//                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                            ImageLoader.getInstance().displayImage(files.get(i).getThumbnailUri(), new ImageViewAware(imageView), options,
//                                    null, null, files.get(i).getOrientation());
//                            imageView.setOnClickListener(this);
//                            pictures.add(files.get(i));
//                            LocalImageHelper.getInstance().setCurrentSize(pictures.size());
                        }
                        taskPhotoManager.insertData(newDatas);
//                    setPhotoData();
                        //清空选中的图片;
                        files.clear();
                        //设置当前选中的图片数量
                        LocalImageHelper.getInstance().setCurrentSize(0);
                        LocalImageHelper.getInstance().getCheckedItems().clear();
                    }
                    //清空选中的图片
                    LocalImageHelper.getInstance().getCheckedItems().clear();
                    break;
            }
        }
    }
}
