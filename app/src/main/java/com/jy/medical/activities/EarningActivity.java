package com.jy.medical.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.EarningDataManager;
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
import com.jy.medical.widget.pickerview.TimePickerDialog;
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

public class EarningActivity extends BaseActivity {

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
    private TextView jobStatus, industryText, completeStatus, entryTime, agreementText, socialText, incomeForm, leaveTime;
    private ImageButton companyLocation;

    private ClearEditText companyAddress, monthlyIncome, companyName;
    private ClearEditText  remarkEdit;
    private Button btnCommit;
    private Button btnSave;
    private View layout1, layout;
    private EarningDataManager earningDataManager = DaoUtils.getEarningDataInstance();
    //    private BaseInfoData baseInfoData;
    private Context context;
    private String industryKey = "";

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_earning;
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
        layout1 = findViewById(R.id.layout1);
        layout = findViewById(R.id.layout);
        jobStatus = (TextView) findViewById(R.id.job_status);
        completeStatus = (TextView) findViewById(R.id.complete_status);
        industryText = (TextView) findViewById(R.id.industry);
        companyName = (ClearEditText) findViewById(R.id.company_name);
        entryTime = (TextView) findViewById(R.id.entry_time);
        agreementText = (TextView) findViewById(R.id.agreement);
        socialText = (TextView) findViewById(R.id.social);
        incomeForm = (TextView) findViewById(R.id.income_form);
        leaveTime = (TextView) findViewById(R.id.leave_time);

        companyLocation = (ImageButton) findViewById(R.id.company_location);
        companyAddress = (ClearEditText) findViewById(R.id.company_address);
        monthlyIncome = (ClearEditText) findViewById(R.id.monthly_income);
//        remarkEdit = (ClearEditText) findViewById(R.id.remark_edit);
        remarkEdit = (ClearEditText) findViewById(R.id.remark_edit);

        btnCommit = (Button) findViewById(R.id.earning_commit);
        btnSave = (Button) findViewById(R.id.earning_save);
        btnCommit.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        jobStatus.setOnClickListener(this);
        completeStatus.setOnClickListener(this);
        industryText.setOnClickListener(this);
        entryTime.setOnClickListener(this);
        agreementText.setOnClickListener(this);
        socialText.setOnClickListener(this);
        incomeForm.setOnClickListener(this);
        leaveTime.setOnClickListener(this);
        companyLocation.setOnClickListener(this);
        findViewById(R.id.add_contact).setOnClickListener(this);

        pictureRecyclerView = (RecyclerView) findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        pictureList = new ArrayList<>();
        list = new ArrayList<>();

        pictureRecyclerView.setLayoutManager(layoutManager);
//        setPhotoData();

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
            case R.id.earning_save:
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
            case R.id.entry_time:
                //选择入职时间
                MultiSelectUtil.initTimePicker(context, entryTime, entryTime.getText().toString(), "选择入职时间");
                break;
            case R.id.leave_time:
                //选择离职时间
                MultiSelectUtil.initTimePicker(context, leaveTime, leaveTime.getText().toString(), "选择离职时间");
                break;
            case R.id.earning_commit:
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
            case R.id.company_location:
                startActivity(SelectAddressActivity.class);
                break;
            case R.id.industry:
                //选择行业
                selectIndustry();
                break;
            case R.id.job_status:
                //选择在职情况
                MultiSelectUtil.selectStatusWithCallBack(context, jobStatus, new String[]{"在职", "离职"}, "选择在职情况", new MultiSelectUtil.VCallBack() {
                    @Override
                    public void setLayoutVisible() {
                        if (jobStatus.getText().equals("离职")) {
                            layout1.setVisibility(View.VISIBLE);
                            layout.setVisibility(View.GONE);
                        } else {
                            layout.setVisibility(View.VISIBLE);
                            layout1.setVisibility(View.GONE);
                        }
                    }
                });

                break;
            case R.id.complete_status:
                //选择完成情况
                MultiSelectUtil.selectStatus(context, completeStatus, new String[]{"已完成", "无法完成"}, "选择完成情况");
                break;
            case R.id.agreement:
                //选择劳务合同
                MultiSelectUtil.selectStatus(context, agreementText, new String[]{"已签订", "未签订"}, "选择劳务合同");
                break;
            case R.id.social:
                //选择社保
                MultiSelectUtil.selectStatus(context, socialText, new String[]{"已交", "未交"}, "选择社保");
                break;
            case R.id.income_form:
                //选择收入发放形式
                MultiSelectUtil.selectStatus(context, incomeForm, new String[]{"现金", "转账"}, "选择收入发放形式");
                break;
            default:
                break;
        }
    }

    private void selectIndustry() {
        //选择行业
        Intent intent = new Intent(context, SelectCategoryActivity.class);
        this.startActivityForResult(intent, 0x12);
    }


    private void saveData() {
        String jobStatusValue = "", jobStatusKey = "", remark = "", completeStatusKey = "", completeStatusValue = "",
                industryKey1 = industryKey, industryValue = "", companyNameValue = "", companyAddressValue = "", entryTimeValue = "", leaveTimeValue = "",
                agreementKey = "", agreementValue = "", socialKey = "", socialValue = "", incomeFormKey = "", incomeFormValue = "", monthlyIncomeValue = "", commitFlagValue = "";
        jobStatusValue = jobStatus.getText().toString();
        if (jobStatusValue.equals("在职")) {
            jobStatusKey = "0";
        } else if (jobStatusValue.equals("离职")) {
            jobStatusKey = "1";
        }
        remark = remarkEdit.getText().toString();
        completeStatusValue = completeStatus.getText().toString();
        if (completeStatusValue.equals("已完成")) {
            completeStatusKey = "0";
        } else if (completeStatusValue.equals("无法完成")) {
            completeStatusKey = "1";
        }
        industryValue = industryText.getText().toString();
        companyNameValue = companyName.getText().toString();
        companyAddressValue = companyAddress.getText().toString();
        entryTimeValue = entryTime.getText().toString();
        leaveTimeValue = leaveTime.getText().toString();
        agreementValue = agreementText.getText().toString();
        if (agreementValue.equals("已签订")) {
            agreementKey = "0";
        } else if (agreementValue.equals("未签订")) {
            agreementKey = "1";
        }
        socialValue = socialText.getText().toString();
        if (socialValue.equals("已交")) {
            socialKey = "0";
        } else if (socialValue.equals("未交")) {
            socialKey = "1";
        }
        incomeFormValue = incomeForm.getText().toString();

        if (incomeFormValue.equals("现金")) {
            incomeFormKey = "0";
        } else if (incomeFormValue.equals("转账")) {
            incomeFormKey = "1";
        }
        monthlyIncomeValue = monthlyIncome.getText().toString();
        EarningData earningData = new EarningData(taskNo, jobStatusKey, jobStatusValue, remark, completeStatusKey, completeStatusValue, industryKey1, industryValue, companyNameValue, companyAddressValue, entryTimeValue, leaveTimeValue, agreementKey, agreementValue, socialKey, socialValue, incomeFormKey, incomeFormValue, monthlyIncomeValue, "");
        earningDataManager.insertSingleData(earningData);

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
        EarningData earningData = earningDataManager.getData(taskNo);
        if (earningData == null)
            return;
        if (!earningData.getJobStatusValue().equals(""))
            jobStatus.setText(earningData.getJobStatusValue());

        if (jobStatus.getText().equals("离职")) {
            layout1.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.GONE);
        }
        industryText.setText(earningData.getIndustryValue());
        completeStatus.setText(earningData.getCompleteStatusValue());
        entryTime.setText(earningData.getEntryTime());
        agreementText.setText(earningData.getAgreementValue());
        socialText.setText(earningData.getSocialValue());
        incomeForm.setText(earningData.getIncomeFormValue());
        leaveTime.setText(earningData.getLeaveTime());
        companyAddress.setText(earningData.getCompanyAddress());
         monthlyIncome.setText(earningData.getMonthlyIncome());
         companyName.setText(earningData.getCompanyName());
         remarkEdit.setText(earningData.getRemark());

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
                case 0x12:
                    industryText.setText(data.getStringExtra("value"));
                    industryKey = data.getStringExtra("value");
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
