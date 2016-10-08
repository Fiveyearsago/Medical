package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class LawActivity extends BaseActivity {


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_law;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setNavState(findViewById(R.id.title_head_second),"法律法规");
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_second_image:
                finish();
                break;
            case R.id.nav_layout:
                startActivity(SelectCityActivity.class);
                break;
            default:
                break;
        }
    }
}
