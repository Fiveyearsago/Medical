package com.jy.medical.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.widget.pickerview.OnSetListener;
import com.jy.medical.widget.pickerview.SelectPickerDialog;
import com.jy.medical.widget.pickerview.TimePickerDialog;
import com.jy.medical.widget.pickerview.data.Type;
import com.jy.medical.widget.pickerview.listener.OnDateSetListener;
import com.jy.medical.widget.pickerview.utils.PickerContants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by songran on 16/11/28.
 */

public class MultiSelectUtil {
    public static String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);
    }
    public static String getNoSecondsDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date d = new Date(time);
        return sf.format(d);
    }
    public static void initTimePickerNoSeconds(Context context, final TextView textView,String string,String title) {
        Calendar calendar = Calendar.getInstance();
        if (!string.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date=null;
            try {
                date = sdf.parse(string);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            calendar.setTime(date);
        }

        PickerContants.DEFAULT_MIN_YEAR = 1950;
        PickerContants.YEAR_COUNT = 70;
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
//                .setType(Type.YEAR_MONTH_DAY)
                .setType(Type.ALL)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId(title)
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
//                .setMinMillseconds(System.currentTimeMillis())
                .setCurrentMillseconds(string.equals("")?System.currentTimeMillis():calendar.getTimeInMillis())
                .setThemeColor(context.getResources().getColor(R.color.timepicker_dialog_bg))
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextSize(15)
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String text = getNoSecondsDateToString(millseconds);
                        textView.setText(text);
                    }
                })
                .build();
        mDialogYearMonthDay.show(((AppCompatActivity)context).getSupportFragmentManager(), "year_month_day_hour_minute");

    }

    public static void initTimePicker(Context context, final TextView textView,String string,String title) {
        Calendar calendar = Calendar.getInstance();
        if (!string.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Date date=null;
            try {
                date = sdf.parse(string);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            calendar.setTime(date);
        }

        PickerContants.DEFAULT_MIN_YEAR = 1950;
        PickerContants.YEAR_COUNT = 70;
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
//                .setType(Type.ALL)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId(title)
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
//                .setMinMillseconds(System.currentTimeMillis())
                .setCurrentMillseconds(string.equals("")?System.currentTimeMillis():calendar.getTimeInMillis())
                .setThemeColor(context.getResources().getColor(R.color.timepicker_dialog_bg))
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextSize(15)
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String text = getDateToString(millseconds);
                        textView.setText(text);
                    }
                })
                .build();
        mDialogYearMonthDay.show(((AppCompatActivity)context).getSupportFragmentManager(), "year_month_day");

    }
    public static void selectStatus(Context context, final TextView textView, String[] strings, String title) {
        int index=0;
        for (int i = 0; i < strings.length; i++) {
            if (textView.getText().equals(strings[i])){
                index=i;
                break;
            }
        }
        SelectPickerDialog pickerDialog=new SelectPickerDialog.Builder()
                .setType(Type.ALL)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId(title)
                .setData(strings)
                .setIndex(index)
                .setThemeColor(context.getResources().getColor(R.color.timepicker_dialog_bg))
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextSize(17)
                .setCallBack(new OnSetListener() {
                    @Override
                    public void onDateSet(SelectPickerDialog pickerView,String result, int index) {
                        textView.setText(result);
                    }
                }).build();
        pickerDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"ALL");
    }
    public static void selectStatusWithCallBack(Context context, final TextView textView, String[] strings, String title, final VCallBack vCallBack) {
        int index=0;
        for (int i = 0; i < strings.length; i++) {
            if (textView.getText().equals(strings[i])){
                index=i;
                break;
            }
        }
        SelectPickerDialog pickerDialog=new SelectPickerDialog.Builder()
                .setType(Type.ALL)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId(title)
                .setData(strings)
                .setIndex(index)
                .setThemeColor(context.getResources().getColor(R.color.timepicker_dialog_bg))
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.colorPrimary))
                .setWheelItemTextSize(17)
                .setCallBack(new OnSetListener() {
                    @Override
                    public void onDateSet(SelectPickerDialog pickerView,String result, int index) {
                        textView.setText(result);
                        vCallBack.setLayoutVisible();
                    }
                }).build();
        pickerDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"ALL");
    }
    public interface VCallBack{
       void setLayoutVisible();
    }
}
