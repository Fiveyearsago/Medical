package com.jy.medical.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jy.medical.R;

public class MineActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        View navView = findViewById(R.id.title_head);
        setTitleState(navView, false, "我", false, "");
    }


    @Override
    public void widgetClick(View v) {

    }


}
