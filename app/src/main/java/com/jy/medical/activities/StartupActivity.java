package com.jy.medical.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class StartupActivity extends BaseActivity {


    @Override
    public void initData() {
        Handler handler = new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class);
            }
        };
        handler.postDelayed(runnable,500);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_startup;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
//        startActivity(LoginActivity.class);
    }

    @Override
    public void widgetClick(View v) {

    }
}
