package com.jy.medical.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by songran on 16/11/8.
 */

public class TimeUtil {

    /**
     * 获取与当前日期之间的间隔天数
     *
     * @return
     */
    public static int getGapCount(String startTime) {
        // 小于0超时
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = new Date(System.currentTimeMillis());
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        int num=(int) ((fromCalendar.getTime().getTime()-toCalendar.getTime().getTime())  / (1000 * 60 * 60 * 24));
//        int num = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));

//        if (endDate.compareTo(startDate) >= 0) {
//            Log.i("num", num + "");
//            return num;
//        } else {
//            Log.i("num1", num + "");
//            return -num;
//        }
        return num;
    }

    public static String getTimeString(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startDate = null;
        try {
            startDate = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("M-d H:m");
        String str = sdf.format(startDate);
        return str;
    }
    public static String getTimeNoSecondsString(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startDate = null;
        try {
            startDate = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String str = sdf.format(startDate);
        return str;
    }

    public static String getTimeWithSecondsString(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date startDate = null;
        try {
            startDate = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = sdf.format(startDate);
        return str;
    }
}
