package com.jy.medical.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.ContactEditAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.EarningData;
import com.jy.medical.greendao.entities.HouseholdData;
import com.jy.medical.greendao.entities.SupporterData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.EarningDataManager;
import com.jy.medical.greendao.manager.HouseholdDataManager;
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
import com.jy.mobile.request.QTInspectAccidentInfoDTO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HouseHoldsActivity extends BaseActivity {

    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private SwipeMenuRecyclerView contactRecycler;
    private List<ContactData> contactDataList;
    private ContactEditAdapter adapter;
    private ContactManager contactManager = DaoUtils.getContactInstance();
    private TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
    private String taskNo;
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组
    DisplayImageOptions options;

    private TextView householdCityTV, householdTypeTV, householdLiveTV, fatherLiveTV, matherLiveTV, startTimeTV, endTimeTV, completeStatusTV;
    private ImageButton liveLocation;
    private ClearEditText childrenNumEdit, brothersNumEdit, liveAddressEdit, liveYearsEdit, remarkEdit;
    private Button btnCommit;
    private Button btnSave;
    private HouseholdDataManager householdDataManager = DaoUtils.getHouseholdDataInstance();
    private Context context;
    private String householdCityKey = "", householdCityValue = "", householdTypeKey = "", householdTypeValue = "";

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_household;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        context = this;
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");

        householdCityTV = (TextView) findViewById(R.id.household_city);
        householdTypeTV = (TextView) findViewById(R.id.household_type);
        householdLiveTV = (TextView) findViewById(R.id.household_live);
        fatherLiveTV = (TextView) findViewById(R.id.father_live);
        matherLiveTV = (TextView) findViewById(R.id.mother_live);
        startTimeTV = (TextView) findViewById(R.id.start_time);
        endTimeTV = (TextView) findViewById(R.id.end_time);
        completeStatusTV = (TextView) findViewById(R.id.complete_status);

        liveLocation = (ImageButton) findViewById(R.id.live_location);
        childrenNumEdit = (ClearEditText) findViewById(R.id.children_num);
        brothersNumEdit = (ClearEditText) findViewById(R.id.brothers);
        liveAddressEdit = (ClearEditText) findViewById(R.id.live_address);
        liveYearsEdit = (ClearEditText) findViewById(R.id.live_years);
        remarkEdit = (ClearEditText) findViewById(R.id.remark_edit);

        btnCommit = (Button) findViewById(R.id.household_commit);
        btnSave = (Button) findViewById(R.id.household_save);
        btnCommit.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        householdCityTV.setOnClickListener(this);
        householdTypeTV.setOnClickListener(this);
        householdLiveTV.setOnClickListener(this);
        fatherLiveTV.setOnClickListener(this);
        matherLiveTV.setOnClickListener(this);
        startTimeTV.setOnClickListener(this);
        endTimeTV.setOnClickListener(this);
        completeStatusTV.setOnClickListener(this);
        liveLocation.setOnClickListener(this);
        findViewById(R.id.add_contact).setOnClickListener(this);

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
        contactRecycler.setItemAnimator(new DefaultItemAnimator());
        contactRecycler.setSwipeMenuCreator(SwipeMenuUtil.getSwipeMenuEdit44(this));
        contactRecycler.setSwipeMenuItemClickListener(menuItemClickListener);
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
            case R.id.household_save:
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
            case R.id.start_time:
                //选择入职时间
                MultiSelectUtil.initTimePicker(context, startTimeTV, startTimeTV.getText().toString(), "选择开始时间");
                break;
            case R.id.end_time:
                //选择离职时间
                MultiSelectUtil.initTimePicker(context, endTimeTV, endTimeTV.getText().toString(), "选择结束时间");
                break;
            case R.id.household_commit:
//                CommitUtil.checkEarningInfo(context, taskNo);
                saveData();
                if (!CommitUtil.checkEarningInfo(context, taskNo))
                    break;
                AlertView mAlertView = new AlertView("提示", "提交后不能进行修改，是否提交？", "否", new String[]{"是"}, null, this, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position1) {
                        if (position1 == 0) {
                            //提交信息
//                            commitData();
                        }
                    }
                }).setCancelable(true).setOnDismissListener(null);
                mAlertView.show();
                break;
            case R.id.live_location:
                startActivity(SelectAddressActivity.class);
                break;
            case R.id.household_city:
                //选择城市
                Intent intent1 = new Intent(context, SelectAreaActivity.class);
                startActivityForResult(intent1, 0x11);

                break;
            case R.id.complete_status:
                //选择完成情况
                MultiSelectUtil.selectStatus(context, completeStatusTV, new String[]{"已完成", "无法完成"}, "选择完成情况");
                break;
            case R.id.household_type:
                //选择户口性质
                Intent intent = new Intent(context, SelectCategoryActivity.class);
                intent.putExtra("kindCode", "D109");
                startActivityForResult(intent, 0x10);
                break;
            case R.id.household_live:
                //选择户籍地居住
                MultiSelectUtil.selectStatus(context, householdLiveTV, new String[]{"是", "否"}, "选择户籍地居住");
                break;
            case R.id.father_live:
                MultiSelectUtil.selectStatus(context, fatherLiveTV, new String[]{"是", "否"}, "是否健在");
                break;
            case R.id.mother_live:
                MultiSelectUtil.selectStatus(context, matherLiveTV, new String[]{"是", "否"}, "是否健在");
                break;
            default:
                break;
        }
    }


    private void saveData() {
        String householdLiveKey = "", householdLiveValue = "", fatherLiveKey = "", fatherLiveValue = "", completeStatusKey = "", completeStatusValue = "",
                motherLiveKey = "", motherLiveValue = "", childrenNum = "", brotherNum = "", address = "", years = "", remark = "", startTimeValue = "", endTimeValue = "";


        householdLiveValue = householdLiveTV.getText().toString();
        if (householdLiveValue.equals("是")) {
            householdLiveKey = "0";
        } else if (householdLiveValue.equals("否")) {
            householdLiveKey = "1";
        }
        fatherLiveValue = fatherLiveTV.getText().toString();
        if (fatherLiveValue.equals("是")) {
            fatherLiveKey = "0";
        } else if (fatherLiveValue.equals("否")) {
            fatherLiveKey = "1";
        }
        motherLiveValue = fatherLiveTV.getText().toString();
        if (motherLiveValue.equals("是")) {
            motherLiveKey = "0";
        } else if (motherLiveValue.equals("否")) {
            motherLiveKey = "1";
        }
        childrenNum = childrenNumEdit.getText().toString();
        brotherNum = brothersNumEdit.getText().toString();
        address = liveAddressEdit.getText().toString();
        startTimeValue = startTimeTV.getText().toString();
        endTimeValue = endTimeTV.getText().toString();
        years = liveYearsEdit.getText().toString();
        remark = remarkEdit.getText().toString();
        completeStatusValue = completeStatusTV.getText().toString();
        if (completeStatusValue.equals("已完成")) {
            completeStatusKey = "0";
        } else if (completeStatusValue.equals("无法完成")) {
            completeStatusKey = "1";
        }
        HouseholdData householdData = new HouseholdData(remark, taskNo, householdCityKey, householdCityValue, householdTypeKey, householdTypeValue, householdLiveKey, householdLiveValue, fatherLiveKey, fatherLiveValue, motherLiveKey, motherLiveValue, childrenNum, brotherNum, address, startTimeValue, endTimeValue, years, completeStatusKey, completeStatusValue);
        householdDataManager.insertSingleData(householdData);
        TaskManager taskManager = DaoUtils.getTaskInstance();
        taskManager.updateIsDongingFlag(taskNo, "1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initContact();
        setPhotoData();
    }

    private void initContact() {
        contactDataList = contactManager.selectAllContact(taskNo);
        adapter = new ContactEditAdapter(this, contactDataList);
        contactRecycler.setAdapter(adapter);
    }

    private void initOtherData() {
        HouseholdData householdData = householdDataManager.getData(taskNo);
        if (householdData == null)
            return;
        householdCityKey = householdData.getHouseholdCityKey();
        householdCityValue = householdData.getHouseholdCityValue();
        householdCityTV.setText(householdCityValue);
        householdTypeKey = householdData.getHouseholdTypeKey();
        householdTypeValue = householdData.getHouseholdTypeValue();
        householdTypeTV.setText(householdTypeValue);
        if (!householdData.getHouseholdLiveKey().equals(""))
            householdLiveTV.setText(householdData.getHouseholdLiveValue());
        if (!householdData.getFatherLiveKey().equals(""))
            fatherLiveTV.setText(householdData.getFatherLiveValue());
        if (!householdData.getMotherLiveKey().equals(""))
            matherLiveTV.setText(householdData.getMotherLiveValue());
        childrenNumEdit.setText(householdData.getChildrenNum());
        brothersNumEdit.setText(householdData.getBrotherNum());
        liveAddressEdit.setText(householdData.getAddress());
        childrenNumEdit.setText(householdData.getChildrenNum());
        startTimeTV.setText(householdData.getStartTime());
        endTimeTV.setText(householdData.getEndTime());
        liveYearsEdit.setText(householdData.getYears());
        if (!householdData.getCompleteKey().equals(""))
            completeStatusTV.setText(householdData.getCompleteValue());
        remarkEdit.setText(householdData.getRemark());

    }

    public void commitData() {
        saveData();
        final BaseInfoData baseInfoData = null;
        QTInspectAccidentInfoDTO qtInspectAccidentInfoDTO = new QTInspectAccidentInfoDTO();
        qtInspectAccidentInfoDTO.setTaskNo(taskNo);
        qtInspectAccidentInfoDTO.setAddress(baseInfoData.getAddress());
        qtInspectAccidentInfoDTO.setContactPerson("宋冉");
        qtInspectAccidentInfoDTO.setContactTel("18612235095");
        qtInspectAccidentInfoDTO.setRemark(baseInfoData.getRemark());
        qtInspectAccidentInfoDTO.setAccidentDate(baseInfoData.getTime());
        qtInspectAccidentInfoDTO.setUserCode("0131002498");
        Gson gson = new Gson();
        String data = gson.toJson(qtInspectAccidentInfoDTO);
        ServerApiUtils.sendToServer(data, "002005", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    baseInfoData.setCommitFlag("1");
//                    baseInfoDataManager.insertSingleData(baseInfoData);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x10:
                    householdTypeTV.setText(data.getStringExtra("value"));
                    householdTypeKey = data.getStringExtra("key");
                    householdTypeValue = data.getStringExtra("value");
                    break;
                case 0x11:
                    householdCityTV.setText(data.getStringExtra("cityName"));
                    householdCityKey = data.getStringExtra("cityKey");
                    householdCityValue = data.getStringExtra("cityName");
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
                    setPhotoData();
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
