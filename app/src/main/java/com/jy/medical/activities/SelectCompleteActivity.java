package com.jy.medical.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;

public class SelectCompleteActivity extends BaseActivity {


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_complete;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "选择完成情况", false, "");
        findViewById(R.id.complete_yes).setOnClickListener(this);
        findViewById(R.id.complete_no).setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.complete_yes:
                Intent intent=new Intent();
                intent.putExtra("status","已完成");
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.complete_no:
                Intent intent1=new Intent();
                intent1.putExtra("status","无法完成");
                setResult(RESULT_OK,intent1);
                finish();
                break;
        }
    }
}
