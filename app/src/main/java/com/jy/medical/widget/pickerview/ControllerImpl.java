package com.jy.medical.widget.pickerview;

import com.jy.medical.widget.pickerview.config.PickerConfig;
import com.jy.medical.widget.pickerview.data.WheelCalendar;
import com.jy.medical.widget.pickerview.utils.PickerContants;
import com.jy.medical.widget.pickerview.utils.Utils;

import java.util.Calendar;

/**
 * Created by jzxiang on 16/5/15.
 */
public class ControllerImpl implements IController {

    PickerConfig mPickerConfig;
    WheelCalendar mCalendarMin;

    public ControllerImpl(PickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;
        mCalendarMin = pickerConfig.mMinCalendar;
    }

    @Override
    public int getMinYear() {
        if (isNoRange())
            return PickerContants.DEFAULT_MIN_YEAR;
        else
            return mCalendarMin.year;
    }

    @Override
    public int getMaxYear() {
//        if (getMinYear() + PickerContants.YEAR_COUNT>Calendar.getInstance().get(Calendar.YEAR))
//            return Calendar.getInstance().get(Calendar.YEAR);
//        else
        return getMinYear() + PickerContants.YEAR_COUNT;
    }

    @Override
    public int getMinMonth(int year) {
        if (!isNoRange() && Utils.isTimeEquals(mCalendarMin, year))
            return mCalendarMin.month;
        else
            return PickerContants.MIN_MONTH;
    }

    @Override
    public int getMaxMonth() {
        return PickerContants.MAX_MONTH;
    }

    @Override
    public int getMinDay(int year, int month) {
        if (!isNoRange() && Utils.isTimeEquals(mCalendarMin, year, month))
            return mCalendarMin.day;
        else
            return PickerContants.MIN_DAY;
    }

    @Override
    public int getMaxDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, month - 1);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getMinHour(int year, int month, int day) {
        if (!isNoRange() && Utils.isTimeEquals(mCalendarMin, year, month, day))
            return mCalendarMin.hour + 1;
        else
            return PickerContants.MIN_HOUR;
    }

    @Override
    public int getMaxHour() {
        return PickerContants.MAX_HOUR;
    }

    @Override
    public int getMinMinute() {
        return PickerContants.MIN_MINUTE;
    }

    @Override
    public int getMaxMinute() {
        return PickerContants.MAX_MINUTE;
    }

    @Override
    public boolean isMinYear(int year) {
        return Utils.isTimeEquals(mCalendarMin, year);
    }

    @Override
    public boolean isMinMonth(int year, int month) {
        return Utils.isTimeEquals(mCalendarMin, year, month);
    }

    @Override
    public boolean isMinDay(int year, int month, int day) {
        return Utils.isTimeEquals(mCalendarMin, year, month, day);
    }

    @Override
    public boolean isNoRange() {
        return mCalendarMin.isNoRange();
    }

    @Override
    public WheelCalendar getDefaultCalendar() {
        return mPickerConfig.mCurrentCalendar;
    }
}
