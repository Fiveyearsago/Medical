package com.jy.medical.widget.pickerview;

import android.content.Context;
import android.view.View;

import com.jy.medical.R;
import com.jy.medical.widget.pickerview.adapters.ArrayWheelAdapter;
import com.jy.medical.widget.pickerview.adapters.NumericWheelAdapter;
import com.jy.medical.widget.pickerview.config.PickerConfig;
import com.jy.medical.widget.pickerview.utils.PickerContants;
import com.jy.medical.widget.pickerview.utils.Utils;
import com.jy.medical.widget.pickerview.wheel.OnWheelChangedListener;
import com.jy.medical.widget.pickerview.wheel.WheelView;

import java.util.Calendar;

/**
 * Created by jzxiang on 16/4/20.
 */
public class SelectWheel {
    Context mContext;

    WheelView year;
    NumericWheelAdapter mYearAdapter;
    ArrayWheelAdapter arrayWheelAdapter;

    IController mIController;
    PickerConfig mPickerConfig;



    public SelectWheel(IController iController, View view, PickerConfig pickerConfig) {
        mIController = iController;
        mPickerConfig = pickerConfig;
        mContext = view.getContext();
        initialize(view);
    }


    public void initialize(View view) {
        initView(view);
        initYear();
    }


    void initView(View view) {
        year = (WheelView) view.findViewById(R.id.year);

        switch (mPickerConfig.mType) {
            case ALL:
                break;
        }
    }

    void initYear() {
        int minYear = mIController.getMinYear();
        int maxYear = mIController.getMaxYear();
        String[] data=mPickerConfig.stringData;
        arrayWheelAdapter=new ArrayWheelAdapter(mContext,data);
        mYearAdapter = new NumericWheelAdapter(mContext, minYear, maxYear, PickerContants.FORMAT, mPickerConfig.mYear);
        mYearAdapter.setConfig(mPickerConfig);
        arrayWheelAdapter.setConfig(mPickerConfig);
        year.setViewAdapter(arrayWheelAdapter);
        year.setCurrentItem(mPickerConfig.index);
//        year.setViewAdapter(mYearAdapter);
//        year.setCurrentItem(mIController.getDefaultCalendar().year - minYear);
    }
    public int getCurrentIndex() {
        return year.getCurrentItem();
    }
    public String getCurrentText() {
        return mPickerConfig.stringData[getCurrentIndex()];
    }

}
