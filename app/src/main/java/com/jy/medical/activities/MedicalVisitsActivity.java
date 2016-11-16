package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;

public class MedicalVisitsActivity extends BaseActivity {

    private String taskNo;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_medical_visits;
    }

    @Override
    public void initParms(Bundle parms) {
        taskNo=parms.getString("taskNo");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        findViewById(R.id.add_hospital).setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                break;
            case R.id.add_hospital:
                //选择医院
                Bundle bundle=new Bundle();
                bundle.putString("taskNo",taskNo);
                startActivity(SelectHospitalActivity.class,bundle);
                break;
        }
    }
}
