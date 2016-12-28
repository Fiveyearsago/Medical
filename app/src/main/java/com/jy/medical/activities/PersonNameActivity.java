package com.jy.medical.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jy.medical.R;
import com.jy.medical.widget.CleanableEditText;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.SwipeBackLayout;

public class PersonNameActivity extends BaseActivity {

    private ClearEditText editTextName;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_name;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "姓名", true, "确定");
        setCleanableEditTextSelection((ClearEditText) findViewById(R.id.peron_edit_name));
        editTextName= (ClearEditText) findViewById(R.id.peron_edit_name);
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
