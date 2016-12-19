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
import com.jy.medical.adapter.ContactEditAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.greendao.entities.HandleData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.HandleDataManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.TaskManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.CommitUtil;
import com.jy.medical.util.ImageUtils;
import com.jy.medical.util.LocalImageHelper;
import com.jy.medical.util.MultiSelectUtil;
import com.jy.medical.util.PhotoUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.util.StringUtils;
import com.jy.medical.util.SwipeMenuUtil;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.FilterImageView;
import com.jy.medical.widget.pickerview.TimePickerDialog;
import com.jy.medical.widget.pickerview.data.Type;
import com.jy.medical.widget.pickerview.listener.OnDateSetListener;
import com.jy.medical.widget.pickerview.utils.PickerContants;
import com.jy.mobile.request.QTInspectAccidentInfoDTO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HandleActivity extends BaseActivity {

    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private TextView handleTime;
    private TimePickerDialog mDialogYearMonthDay;
    private SwipeMenuRecyclerView contactRecycler;
    private List<ContactData> contactDataList;
    private ContactEditAdapter adapter;
    private ContactManager contactManager;
    private TaskPhotoManager taskPhotoManager;
    private String taskNo;
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组
    DisplayImageOptions options;
    private TextView completeStatus;
    private ClearEditText handleNameEdit;
    private ClearEditText handleResultEdit;
    private ClearEditText remarkEdit;
    private Button btnCommit;
    private Button btnSave;
    private HandleDataManager handleDataManager = DaoUtils.getHandleDataInstance();
    private Context context;
//    private HandleData handleData;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_handle;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        context=this;
        setStatusBarTint();
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        completeStatus = (TextView) findViewById(R.id.complete_status);
        handleNameEdit = (ClearEditText) findViewById(R.id.handle_name);
        handleResultEdit = (ClearEditText) findViewById(R.id.handle_result);
        remarkEdit = (ClearEditText) findViewById(R.id.remark_edit);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnCommit.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        completeStatus.setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.add_contact).setOnClickListener(this);
        handleTime = (TextView) findViewById(R.id.handle_time);
        handleTime.setOnClickListener(this);
        pictureRecyclerView = (RecyclerView) findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        pictureList = new ArrayList<>();
        list = new ArrayList<>();
        pictureRecyclerView.setLayoutManager(layoutManager);
        contactRecycler = (SwipeMenuRecyclerView) findViewById(R.id.contact_recycler);
        contactRecycler.setHasFixedSize(true);
        contactRecycler.setLayoutManager(layoutManager1);
        contactRecycler.setSwipeMenuCreator(SwipeMenuUtil.getSwipeMenuEdit44(this));
        contactRecycler.setSwipeMenuItemClickListener(menuItemClickListener);
        contactManager = DaoUtils.getContactInstance();
        contactDataList = new ArrayList<>();
        contactDataList = contactManager.selectAllContact(taskNo);
        adapter = new ContactEditAdapter(this, contactDataList);
        contactRecycler.setAdapter(adapter);

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
        pictureAdapter = new PictureAdapter(this, list, taskNo,true,true);
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
            case R.id.handle_time:
                //选择事故时间
                MultiSelectUtil.initTimePicker(context, handleTime, handleTime.getText().toString(), "选择处理时间");
                break;
            case R.id.btn_commit:
                saveData();
                CommitUtil.commitHandleInfo(context, taskNo, new CommitUtil.CommitCallBack() {
                    @Override
                    public void commitSuccess() {

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

    private void commitData() {

    }

    private void saveData() {
        String handleName = handleNameEdit.getText().toString();
        String time = handleTime.getText().toString();
        String handleResult = handleResultEdit.getText().toString();
        String remark = remarkEdit.getText().toString();
        String completeStatusText="";
        if (completeStatus.getText().toString().equals("已完成")){
            completeStatusText="0";
        }else if (completeStatus.getText().toString().equals("无法完成")){
            completeStatusText="1";
        }
        HandleData handleData = new HandleData(taskNo, handleName, time, handleResult, remark, completeStatusText, "");
        handleDataManager.insertSingleData(handleData);
        TaskManager taskManager=DaoUtils.getTaskInstance();
        taskManager.updateIsDongingFlag(taskNo,"1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContactData();
        setPhotoData();

    }

    private void setContactData() {
        contactDataList = contactManager.selectAllContact(taskNo);
        adapter = new ContactEditAdapter(this, contactDataList);
        contactRecycler.setAdapter(adapter);
    }

    private void initOtherData() {
        HandleData handleData = handleDataManager.getData(taskNo);
        if (handleData == null)
            return;
        handleNameEdit.setText(handleData.getHandleName());
        handleTime.setText(handleData.getHandleTime());
        handleResultEdit.setText(handleData.getHandleResult());
        remarkEdit.setText(handleData.getRemark());
        if (handleData.getCompleteStatus().equals("0"))
            completeStatus.setText("已完成");
        else if (handleData.getCompleteStatus().equals("1"))
            completeStatus.setText("无法完成");
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
                contactManager.deleteSingleData(contactDataList.get(adapterPosition));
                contactDataList.remove(adapterPosition);
                adapter.notifyDataSetChanged();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
            }
        }
    };

}
