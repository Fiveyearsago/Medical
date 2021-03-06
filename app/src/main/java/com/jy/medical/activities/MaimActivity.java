package com.jy.medical.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.adapter.SelectedMaimGradeAdapter;
import com.jy.medical.greendao.entities.MaimData;
import com.jy.medical.greendao.entities.MaimGradeData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.MaimDataManager;
import com.jy.medical.greendao.manager.MaimGradeDataManager;
import com.jy.medical.greendao.manager.TaskManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.CommitUtil;
import com.jy.medical.util.ImageUtils;
import com.jy.medical.util.LocalImageHelper;
import com.jy.medical.util.MultiSelectUtil;
import com.jy.medical.util.PhotoUtil;
import com.jy.medical.util.StringUtils;
import com.jy.medical.util.SwipeMenuUtil;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.FilterImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 伤残信息
 */
public class MaimActivity extends BaseActivity {

    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private SwipeMenuRecyclerView maimGradeRecycler;
    private List<MaimGradeData> maimGradeDataList=new ArrayList<>();
    private SelectedMaimGradeAdapter adapter;
    private MaimGradeDataManager maimGradeDataManager;
    private TaskPhotoManager taskPhotoManager;
    private String taskNo;
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组
    private DisplayImageOptions options;

    private TextView completeStatus, approvalDepartmentTV;
    private ClearEditText remarkEdit, approvalPersonEdit, payCoefficientEdit, describeEdit;

    private Button btnCommit;
    private Button btnSave;
    private MaimDataManager maimDataManager = DaoUtils.getMaimDataInstance();
    private Context context;
    private String approvalDepartmentKey;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_maim;
    }

    @Override
    public void initParams(Bundle parms) {
        if (parms!=null)
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        context = this;
        setStatusBarTint();
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        completeStatus = (TextView) findViewById(R.id.complete_status);
        approvalDepartmentTV = (TextView) findViewById(R.id.approval_department);
        approvalPersonEdit = (ClearEditText) findViewById(R.id.approval_person);
        payCoefficientEdit = (ClearEditText) findViewById(R.id.pay_coefficient);
        describeEdit = (ClearEditText) findViewById(R.id.describe);
        remarkEdit = (ClearEditText) findViewById(R.id.remark_edit);
        completeStatus.setOnClickListener(this);
        approvalDepartmentTV.setOnClickListener(this);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnCommit.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        findViewById(R.id.add_maim).setOnClickListener(this);

        pictureRecyclerView = (RecyclerView) findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        pictureList = new ArrayList<>();
        list = new ArrayList<>();

        pictureRecyclerView.setLayoutManager(layoutManager);
        maimGradeRecycler = (SwipeMenuRecyclerView) findViewById(R.id.miam_grade_recycler);
        maimGradeRecycler.setHasFixedSize(true);
        maimGradeRecycler.setLayoutManager(layoutManager1);
        maimGradeRecycler.setSwipeMenuCreator(SwipeMenuUtil.getSwipeMenuEdit(this));
        maimGradeRecycler.setSwipeMenuItemClickListener(menuItemClickListener);

        maimGradeDataManager = DaoUtils.getMaimGradeDataInstance();
        maimGradeDataList = new ArrayList<>();
        maimGradeDataList = maimGradeDataManager.getDataList(taskNo);
        adapter = new SelectedMaimGradeAdapter(maimGradeDataList,this);
        maimGradeRecycler.setAdapter(adapter);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageForEmptyUri(R.mipmap.dangkr_no_picture_small)
                .showImageOnFail(R.mipmap.dangkr_no_picture_small)
                .showImageOnLoading(R.mipmap.dangkr_no_picture_small)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        initOtherData();
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
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.add_photo);
        list.add(bmp);
        pictureAdapter = new PictureAdapter(this, list, taskNo, true, true);
        pictureAdapter.notifyDataSetChanged();
        pictureRecyclerView.setAdapter(pictureAdapter);
    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                saveData();
                ToastUtil.showToast(context, "已保存所有信息");
                finish();
                break;
            case R.id.approval_department:
                Intent intent = new Intent(this, SelectHospitalActivity.class);
                intent.putExtra("taskNo", taskNo);
                intent.putExtra("flag", "");
                intent.putExtra("dealLocalCode", "1");
                startActivityForResult(intent, 0x11);
                break;
            case R.id.btn_save:
                saveData();
                ToastUtil.showToast(context, "已保存所有信息");
                finish();
                break;
            case R.id.add_maim:
                //添加伤残等级
                Bundle bundle = new Bundle();
                bundle.putString("taskNo", taskNo);
                startActivity(MaimGradeActivity.class, bundle);
                break;
            case R.id.btn_commit:
                saveData();
                CommitUtil.commitMaimInfo(context, taskNo, new CommitUtil.CommitCallBack() {
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
            case R.id.complete_status:
                //选择完成情况
                MultiSelectUtil.selectStatus(context, completeStatus, new String[]{"已完成", "无法完成"}, "选择完成情况");
                break;
            default:
                break;
        }
    }

    private void saveData() {
        String approvalDepartmentValue = approvalDepartmentTV.getText().toString();
        String approvalPerson = approvalPersonEdit.getText().toString();
        String payCoefficient = payCoefficientEdit.getText().toString();
        String describe = describeEdit.getText().toString();
        String remark = remarkEdit.getText().toString();
        String completeStatusText = "";
        if (completeStatus.getText().toString().equals("已完成")) {
            completeStatusText = "0";
        } else if (completeStatus.getText().toString().equals("无法完成")) {
            completeStatusText = "1";
        }
        MaimData maimData = new MaimData(taskNo, approvalDepartmentKey, approvalDepartmentValue, approvalPerson, payCoefficient, describe, remark, completeStatusText, "");
        maimDataManager.insertSingleData(maimData);
        TaskManager taskManager=DaoUtils.getTaskInstance();
        taskManager.updateIsDongingFlag(taskNo,"1");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setContactData();
        if (maimDataManager.getData(taskNo) != null)
            approvalDepartmentTV.setText(maimDataManager.getData(taskNo).getApprovalDepartmentValue());
        setMaimGradeData();
        setPhotoData();

    }

    private void setMaimGradeData() {
        maimGradeDataList = maimGradeDataManager.getDataList(taskNo);
        adapter.setDta(maimGradeDataList);
        adapter.notifyDataSetChanged();
//        adapter = new SelectedMaimGradeAdapter( maimGradeDataList,this);
//        maimGradeRecycler.setAdapter(adapter);
    }

    private void initOtherData() {
        MaimData maimData = maimDataManager.getData(taskNo);
        if (maimData == null)
            return;
        approvalDepartmentTV.setText(maimData.getApprovalDepartmentValue());
        approvalPersonEdit.setText(maimData.getApprovalPerson());
        payCoefficientEdit.setText(maimData.getPayCoefficient());
        describeEdit.setText(maimData.getDescribe());
        remarkEdit.setText(maimData.getRemark());
        if (maimData.getCompleteStatus().equals("0"))
            completeStatus.setText("已完成");
        else if (maimData.getCompleteStatus().equals("1"))
            completeStatus.setText("无法完成");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x11:

                    approvalDepartmentTV.setText(data.getStringExtra("hospitalName"));
                    approvalDepartmentKey = data.getStringExtra("hospitalId");
                    break;
                case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                    String cameraPath = LocalImageHelper.getInstance().getCameraImgPath();
                    if (StringUtils.isEmpty(cameraPath)) {
                        Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File file = new File(cameraPath);

                    if (file.exists()) {
                        taskPhotoManager.insertSingleData(new TaskPhoto(taskNo, cameraPath));
                    } else {
                        Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                    if (LocalImageHelper.getInstance().isResultOk()) {
                        LocalImageHelper.getInstance().setResultOk(false);
                        //获取选中的图片
                        List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                        List<TaskPhoto> newDatas = new ArrayList<>();
                        for (int i = 0; i < files.size(); i++) {
                            String photoPath = ImageUtils.getAbsoluteImagePath(this, Uri.parse(files.get(i).getOriginalUri()));
                            newDatas.add(new TaskPhoto(taskNo, photoPath));

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(75, 75);
//                        params.rightMargin = padding;
                            FilterImageView imageView = new FilterImageView(this);
                            imageView.setLayoutParams(params);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            ImageLoader.getInstance().displayImage(files.get(i).getThumbnailUri(), new ImageViewAware(imageView), options,
                                    null, null, files.get(i).getOrientation());
                            imageView.setOnClickListener(this);
                            pictures.add(files.get(i));
                            LocalImageHelper.getInstance().setCurrentSize(pictures.size());
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
                default:
                    break;
            }
        }
    }
        /**
         * 菜单点击监听。
         */
        private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
                closeable.smoothCloseMenu();// 关闭被点击的菜单。

                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                    maimGradeDataManager.deleteSingleData(maimGradeDataList.get(adapterPosition));
                    maimGradeDataList.remove(adapterPosition);
                    adapter.notifyDataSetChanged();
                } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                }
            }
        };

}
