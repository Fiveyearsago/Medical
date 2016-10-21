package com.jy.medical.activities;

import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class AddContactsActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_contacts;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "添加联系人", true, "保存");
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                break;
        }
    }
}
