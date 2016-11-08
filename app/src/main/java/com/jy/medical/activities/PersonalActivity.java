package com.jy.medical.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jy.medical.R;
import com.jy.medical.widget.pickerview.TimePickerDialog;
import com.jy.medical.widget.pickerview.data.Type;
import com.jy.medical.widget.pickerview.listener.OnDateSetListener;
import com.jy.medical.widget.pickerview.utils.PickerContants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonalActivity extends BaseActivity {
    private TimePickerDialog mDialogYearMonthDay;
    private Button logOut;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "个人资料", false, "");
        findViewById(R.id.layout_person_name).setOnClickListener(this);
        findViewById(R.id.layout_person_phone).setOnClickListener(this);
        findViewById(R.id.layout_person_psw).setOnClickListener(this);
        findViewById(R.id.layout_person_born).setOnClickListener(this);
        findViewById(R.id.log_out).setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                break;
            case R.id.layout_person_head:

                break;
            case R.id.layout_person_name:
                startActivity(PersonNameActivity.class);
                break;
            case R.id.layout_person_born:
                initTimePicker();
                break;
            case R.id.layout_person_phone:
                startActivity(ChangePhoneActivity.class);
                break;
            case R.id.layout_person_psw:
                startActivity(EditPswActivity.class);
                break;
            case R.id.log_out:
                startActivity(LoginActivity.class);
                break;
            default:
                break;
        }

    }

    public void initTimePicker() {
        PickerContants.DEFAULT_MIN_YEAR = 1950;
        PickerContants.YEAR_COUNT = 70;
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("")
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
//                        textA.setText(text);
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
