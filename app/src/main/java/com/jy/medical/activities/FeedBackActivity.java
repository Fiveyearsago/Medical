package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class FeedBackActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "意见反馈", true, "提交");
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.page_head_image:
                finish();
                break;
            default:
                break;
        }
    }
}
