package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class SearchPlatformActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_platform;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setSearchTitle(findViewById(R.id.title_head),"取消");

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_search_button:
                finish();
                break;
        }
    }
}
