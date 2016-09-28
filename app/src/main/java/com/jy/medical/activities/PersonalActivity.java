package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class PersonalActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        View navView = findViewById(R.id.title_head);
        setTitleState(navView, true, "个人资料", false, "");
    }

    @Override
    public void widgetClick(View v) {

    }
}
