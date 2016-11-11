package com.jy.medical.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.ContactAdapter;
import com.jy.medical.adapter.ContactEditAdapter;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.controller.JsonToBean;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.ImageUtils;
import com.jy.medical.util.LocalImageHelper;
import com.jy.medical.util.PhotoUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.widget.FilterImageView;
import com.jy.medical.widget.pickerview.TimePickerDialog;
import com.jy.medical.widget.pickerview.data.Type;
import com.jy.medical.widget.pickerview.listener.OnDateSetListener;
import com.jy.medical.widget.pickerview.utils.PickerContants;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.request.QTInspectAccidentInfoDTO;
import com.jy.mobile.request.QtRecieveTaskDTO;
import com.jy.mobile.response.SpRecieveTaskDTO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import org.xutils.common.Callback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FollowEditActivity extends BaseActivity {

    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private TextView textAccidentTime;
    private TimePickerDialog mDialogYearMonthDay;
    private RecyclerView contactRecycler;
    private List<ContactData> contactDataList;
    private ContactEditAdapter adapter;
    private ContactManager contactManager;
    private TaskPhotoManager taskPhotoManager;
    private String taskNo;
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组
    DisplayImageOptions options;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_follow_edit;
    }

    @Override
    public void initParms(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        findViewById(R.id.follow_edit_location).setOnClickListener(this);
        findViewById(R.id.follow_edit_commit).setOnClickListener(this);
        findViewById(R.id.follow_edit_add_contact_layout).setOnClickListener(this);
        textAccidentTime = (TextView) findViewById(R.id.follow_edit_accident_time);
        textAccidentTime.setOnClickListener(this);
        pictureRecyclerView = (RecyclerView) findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        pictureList=new ArrayList<>();
        list = new ArrayList<>();

        pictureRecyclerView.setLayoutManager(layoutManager);
//        setPhotoData();

        contactRecycler = (RecyclerView) findViewById(R.id.contact_recycler);
        contactRecycler.setHasFixedSize(true);
        contactRecycler.setLayoutManager(layoutManager1);
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

    }

    private void setPhotoData() {
        pictureList.clear();
        list.clear();
        taskPhotoManager=DaoUtils.getTaskPhotoInstance();
        pictureList=taskPhotoManager.selectAllPhoto(taskNo);
        for (int i = 0; i < pictureList.size(); i++) {
            Bitmap bmp = PhotoUtil.convertToBitmap(pictureList.get(i).getPhotoPath(),75,75);
            list.add(bmp);
        }
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.add_photo);
        list.add(bmp);
        pictureAdapter = new PictureAdapter(this, list,taskNo);
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
                break;
            case R.id.follow_edit_add_contact_layout:
                //添加联系人
                Bundle bundle=new Bundle();
                bundle.putString("taskNo",taskNo);
                startActivity(AddContactsActivity.class,bundle);
                break;
            case R.id.follow_edit_accident_time:
                //选择事故时间
                initTimePicker();
                break;
            case R.id.follow_edit_commit:
//                commitData();
                break;
            case R.id.follow_edit_location:
                startActivity(SelectAddressActivity.class);
//                startActivity(MapDemoActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactDataList = contactManager.selectAllContact(taskNo);
        adapter = new ContactEditAdapter(this, contactDataList);
        contactRecycler.setAdapter(adapter);
        setPhotoData();
    }

    public void commitData() {

        QTInspectAccidentInfoDTO qtInspectAccidentInfoDTO = new QTInspectAccidentInfoDTO();
        qtInspectAccidentInfoDTO.setTaskNo("402881e453ebf3540153ec38e7a40004");
        qtInspectAccidentInfoDTO.setAddress("北京海淀区上地信息路一号国际科技创业园");
        qtInspectAccidentInfoDTO.setContactPerson("宋冉");
        qtInspectAccidentInfoDTO.setContactTel("18612235095");
        qtInspectAccidentInfoDTO.setRemark("备注信息");
        qtInspectAccidentInfoDTO.setAccidentDate("2006-07-07 22:10:10");
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

    public void initTimePicker() {
        PickerContants.DEFAULT_MIN_YEAR = 1950;
        PickerContants.YEAR_COUNT = 70;
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
//                .setMinMillseconds(System.currentTimeMillis())
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextSize(12)
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String text = getDateToString(millseconds);
                        textAccidentTime.setText(text);
                    }
                })
                .build();
        mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");

    }

    public String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                if (LocalImageHelper.getInstance().isResultOk()) {
                    LocalImageHelper.getInstance().setResultOk(false);
                    //获取选中的图片
                    List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                    List<TaskPhoto> newDatas=new ArrayList<>();
                    for (int i = 0; i < files.size(); i++) {
                        String photoPath=ImageUtils.getAbsoluteImagePath(this,Uri.parse(files.get(i).getOriginalUri()));
                        newDatas.add(new TaskPhoto(taskNo,photoPath ));

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
