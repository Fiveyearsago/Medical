package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class NewPswActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_psw;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "新密码", true, "确定");
    }

    @Override
    public void widgetClick(View v) {

    }
}