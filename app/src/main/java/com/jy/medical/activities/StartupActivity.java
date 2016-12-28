package com.jy.medical.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;

public class StartupActivity extends BaseActivity {


    @Override
    public void initData() {
        MedicalApplication.getInstance().addActivity(this);
        Handler handler = new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class);
                StartupActivity.this.finish();
            }
        };
        handler.postDelayed(runnable,500);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_startup;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
//        startActivity(LoginActivity.class);
        MedicalApplication.getInstance().addActivity(this);
    }

    @Override
    public void widgetClick(View v) {

    }
}
