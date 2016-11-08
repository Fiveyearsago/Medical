package com.jy.medical.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.R;
import com.jy.medical.adapter.PictureAdapter;
import com.jy.medical.controller.JsonToBean;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.widget.pickerview.TimePickerDialog;
import com.jy.medical.widget.pickerview.data.Type;
import com.jy.medical.widget.pickerview.listener.OnDateSetListener;
import com.jy.medical.widget.pickerview.utils.PickerContants;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.request.QTInspectAccidentInfoDTO;
import com.jy.mobile.request.QtRecieveTaskDTO;
import com.jy.mobile.response.SpRecieveTaskDTO;

import org.xutils.common.Callback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FollowEditActivity extends BaseActivity {

    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<Bitmap> list;
    private TextView textAccidentTime;
    private TimePickerDialog mDialogYearMonthDay;

    @Override
    public void initData() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_follow_edit;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        findViewById(R.id.follow_edit_location).setOnClickListener(this);
        findViewById(R.id.follow_edit_commit).setOnClickListener(this);
        findViewById(R.id.follow_edit_add_contact_layout).setOnClickListener(this);
        textAccidentTime = (TextView) findViewById(R.id.follow_edit_accident_time);
        textAccidentTime.setOnClickListener(this);
        pictureRecyclerView = (RecyclerView) findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        list = new ArrayList<>();
        Resources res = getResources();
        for (int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.add_photo);
            list.add(bmp);
        }
        pictureAdapter = new PictureAdapter(this, list);

        pictureRecyclerView.setLayoutManager(layoutManager);
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
                startActivity(AddContactsActivity.class);
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
}
