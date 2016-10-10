package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

/**
 * 法律法规详情页
 */
public class LawDetailActivity extends BaseActivity {


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_law_detail;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDetailNavState(findViewById(R.id.detail_title_head));
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_detail_head_image:
                finish();
                break;
        }
    }
}
