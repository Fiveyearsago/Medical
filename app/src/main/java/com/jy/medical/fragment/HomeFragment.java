package com.jy.medical.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.activities.AllPlatformActivity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private PieChartData data;
    private PieChartView chart;
    private TextView radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8, radioButton9;
    private View viewAll, viewDoing, viewComplete, viewTimeOut;
    private static HomeFragment homeFragment;

    public static HomeFragment newInstance(int page, Context context) {
        mContext = context;
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        return homeFragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        View viewGroup = view.findViewById(R.id.radio_layout);
        viewAll = view.findViewById(R.id.view_all_platform);
        viewDoing = view.findViewById(R.id.view_doing_platform);
        viewComplete = view.findViewById(R.id.view_complete_platform);
        viewTimeOut = view.findViewById(R.id.view_timeout_platform);
        viewAll = view.findViewById(R.id.view_all_platform);
        viewAll.setOnClickListener(this);
        viewDoing.setOnClickListener(this);
        viewComplete.setOnClickListener(this);
        viewTimeOut.setOnClickListener(this);
        radioButton1 = (TextView) viewGroup.findViewById(R.id.radioButton1);
        radioButton2 = (TextView) viewGroup.findViewById(R.id.radioButton2);
        radioButton3 = (TextView) viewGroup.findViewById(R.id.radioButton3);
        radioButton4 = (TextView) viewGroup.findViewById(R.id.radioButton4);
        radioButton5 = (TextView) viewGroup.findViewById(R.id.radioButton5);
        radioButton6 = (TextView) viewGroup.findViewById(R.id.radioButton6);
        radioButton7 = (TextView) viewGroup.findViewById(R.id.radioButton7);
        radioButton8 = (TextView) viewGroup.findViewById(R.id.radioButton8);
        radioButton9 = (TextView) viewGroup.findViewById(R.id.radioButton9);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
        radioButton5.setOnClickListener(this);
        radioButton6.setOnClickListener(this);
        radioButton7.setOnClickListener(this);
        radioButton8.setOnClickListener(this);
        radioButton9.setOnClickListener(this);
        chart = (PieChartView) view.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
//        chart.setOnClickListener(this);
        generateData();

        return view;
    }

    private void generateData() {
        List<SliceValue> values = new ArrayList<SliceValue>();
        SliceValue sliceValue1 = new SliceValue(85, Color.parseColor("#00FFFF"));
        SliceValue sliceValue2 = new SliceValue(23, Color.parseColor("#FF3E00"));
        SliceValue sliceValue3 = new SliceValue(67, Color.parseColor("#F2F200"));
        values.add(sliceValue1);
        values.add(sliceValue2);
        values.add(sliceValue3);

        data = new PieChartData(values);
        data.setHasCenterCircle(true);
        data.setSlicesSpacing(4);
        String textNum = "175";
        SpannableString msp = new SpannableString(textNum);
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, textNum.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
        SpannableStringBuilder style = new SpannableStringBuilder(textNum);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), 0, textNum.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        // Get font size from dimens.xml and convert it to sp(library uses sp values).
        data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                (int) getResources().getDimension(R.dimen.large_text_size)));
        chart.setPieChartData(data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_all_platform:
                jumpToAllPlatform(0);
                break;
            case R.id.view_doing_platform:
                jumpToAllPlatform(1);
                break;
            case R.id.view_complete_platform:
                jumpToAllPlatform(3);
                break;
            case R.id.view_timeout_platform:
                jumpToAllPlatform(2);
                break;
            case R.id.radioButton1:
                jumpToAllPlatform("09");
                break;
            case R.id.radioButton2:
                jumpToAllPlatform("10");
                break;
            case R.id.radioButton3:
                jumpToAllPlatform("01");
                break;
            case R.id.radioButton4:
                jumpToAllPlatform("02");
                break;
            case R.id.radioButton5:
                jumpToAllPlatform("03");
                break;
            case R.id.radioButton6:
                jumpToAllPlatform("04");
                break;
            case R.id.radioButton7:
                jumpToAllPlatform("05");
                break;
            case R.id.radioButton8:
                jumpToAllPlatform("08");
                break;
            case R.id.radioButton9:
                jumpToAllPlatform("06");
                break;
            case R.id.chart:
                jumpToAllPlatform("");
                break;

        }
    }

    public void jumpToAllPlatform(String taskType) {
        Intent intent = new Intent(mContext, AllPlatformActivity.class);
        intent.putExtra("taskType", taskType);
        startActivity(intent);
    }

    public void jumpToAllPlatform(int position) {
        Intent intent = new Intent(mContext, AllPlatformActivity.class);
        intent.putExtra("taskType", "");
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}
