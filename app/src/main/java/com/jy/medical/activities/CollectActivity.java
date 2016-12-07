package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class CollectActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "收藏", false, "");

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_third_head_image:
                finish();
                break;
        }
    }
}
