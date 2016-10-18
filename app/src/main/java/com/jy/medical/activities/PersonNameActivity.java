package com.jy.medical.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jy.medical.R;
import com.jy.medical.widget.CleanableEditText;

public class PersonNameActivity extends BaseActivity {

    private CleanableEditText editTextName;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_name;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "姓名", true, "确定");
        setCleanableEditTextSelection((CleanableEditText) findViewById(R.id.peron_edit_name));
        editTextName= (CleanableEditText) findViewById(R.id.peron_edit_name);
        editTextName.setOnClickListener(this);
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
