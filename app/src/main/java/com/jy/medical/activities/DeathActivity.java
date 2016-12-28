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
import com.jy.medical.adapter.ContactEditAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.greendao.entities.DeathData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.DeathDataManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.TaskManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.CommitUtil;
import com.jy.medical.util.ImageUtils;
import com.jy.medical.util.LocalImageHelper;
import com.jy.medical.util.MultiSelectUtil;
import com.jy.medical.util.PhotoUtil;
import com.jy.medical.util.StringUtils;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.FilterImageView;
import com.jy.medical.widget.SwipeBackLayout;
import com.jy.medical.widget.pickerview.TimePickerDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DeathActivity extends BaseActivity implements View.OnFocusChangeListener {

    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private TaskPhotoManager taskPhotoManager;
    private String taskNo;
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组
    DisplayImageOptions options;
    private TextView completeStatusTextView, deathReasonTextView, deathTimeTextView;
    private ClearEditText addressEdit;
    private ClearEditText participationEdit;
    private ClearEditText remarkEdit;
    private Button btnCommit;
    private Button btnSave;
    private DeathDataManager deathDataManager = DaoUtils.getDeathDataInstance();
    private Context context;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_death;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        context = this;
        setStatusBarTint();
//        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        completeStatusTextView = (TextView) findViewById(R.id.complete_status);
        deathReasonTextView = (TextView) findViewById(R.id.death_reason);
        deathTimeTextView = (TextView) findViewById(R.id.death_time);
        addressEdit = (ClearEditText) findViewById(R.id.death_address);
        participationEdit = (ClearEditText) findViewById(R.id.death_participation);
        remarkEdit = (ClearEditText) findViewById(R.id.remark_edit);
        addressEdit.setOnFocusChangeListener(this);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnSave = (Button) findViewById(R.id.btn_save);

        findViewById(R.id.death_location).setOnClickListener(this);
        deathReasonTextView.setOnClickListener(this);
        deathTimeTextView.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        completeStatusTextView.setOnClickListener(this);

        pictureRecyclerView = (RecyclerView) findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        pictureRecyclerView.setLayoutManager(layoutManager);
        pictureList = new ArrayList<>();
        list = new ArrayList<>();
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
            case R.id.btn_save:
                saveData();
                ToastUtil.showToast(context, "已保存所有信息");
                finish();
                break;
            case R.id.add_contact:
                //添加联系人
                Bundle bundle = new Bundle();
                bundle.putString("taskNo", taskNo);
                startActivity(AddContactsActivity.class, bundle);
                break;
            case R.id.death_time:
                //选择死亡日期
                MultiSelectUtil.initTimePickerNoSeconds(context, deathTimeTextView, deathTimeTextView.getText().toString(), "选择死亡日期");
                break;
            case R.id.btn_commit:
                saveData();
                CommitUtil.commitBaseInfo(context, taskNo, new CommitUtil.CommitCallBack() {
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
            case R.id.death_location:
                startActivity(SelectAddressActivity.class);
                break;
            case R.id.death_reason:
                //选择死亡原因
                MultiSelectUtil.selectStatus(context, deathReasonTextView, new String[]{"损伤导致", "损伤与疾病共同导致", "疾病导致"}, "选择死亡原因");
                break;
            case R.id.complete_status:
                //选择完成情况
                MultiSelectUtil.selectStatus(context, completeStatusTextView, new String[]{"已完成", "无法完成"}, "选择完成情况");
                break;
            default:
                break;
        }
    }

    private void saveData() {

        String deathReason = "";
        if (deathReasonTextView.getText().toString().equals("损伤导致")) {
            deathReason = "0";
        } else if (completeStatusTextView.getText().toString().equals("损伤与疾病共同导致")) {
            deathReason = "1";
        } else if (completeStatusTextView.getText().toString().equals("疾病导致")) {
            deathReason = "2";
        }
        String participation = participationEdit.getText().toString();
        String time = deathTimeTextView.getText().toString();
        String address = addressEdit.getText().toString();
        String remark = remarkEdit.getText().toString();
        String completeStatusText = "";
        if (completeStatusTextView.getText().toString().equals("已完成")) {
            completeStatusText = "0";
        } else if (completeStatusTextView.getText().toString().equals("无法完成")) {
            completeStatusText = "1";
        }
        DeathData deathData = new DeathData(taskNo, deathReason, participation, address, time, remark, completeStatusText, "");
        deathDataManager.insertSingleData(deathData);
        TaskManager taskManager=DaoUtils.getTaskInstance();
        taskManager.updateIsDongingFlag(taskNo,"1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPhotoData();

    }


    private void initOtherData() {
        DeathData deathData = deathDataManager.getData(taskNo);
        if (deathData == null)
            return;
        addressEdit.setText(deathData.getDeathAddress());
        deathTimeTextView.setText(deathData.getDeathTime());
        participationEdit.setText(deathData.getParticipation());
        remarkEdit.setText(deathData.getRemark());
        if (deathData.getCompleteStatus().equals("0"))
            completeStatusTextView.setText("已完成");
        else if (deathData.getCompleteStatus().equals("1"))
            completeStatusTextView.setText("无法完成");

        if (deathData.getDeathReason().equals("0"))
            deathReasonTextView.setText("损伤导致");
        else if (deathData.getDeathReason().equals("1"))
            deathReasonTextView.setText("损伤与疾病共同导致");
        else if (deathData.getDeathReason().equals("2"))
            deathReasonTextView.setText("疾病导致");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.death_address:
                setCleanableEditTextSelection(addressEdit);
                break;
            case R.id.death_participation:
                setCleanableEditTextSelection(participationEdit);
                break;
            case R.id.remark_edit:
                setCleanableEditTextSelection(remarkEdit);
                break;
        }
    }
}
