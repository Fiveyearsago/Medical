package com.jy.medical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jy.medical.activities.BaseActivity;
import com.jy.medical.util.DensityUtil;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PgyCrashManager.register(this);
        Log.i("96dp = ",DensityUtil.dip2px(this,96f)+"px");
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {

    }


    @Override
    public void widgetClick(View v) {

    }


}
