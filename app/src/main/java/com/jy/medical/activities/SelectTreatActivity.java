package com.jy.medical.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.greendao.entities.SelectedDiagnose;
import com.jy.medical.greendao.manager.SelectedDiagnoseManager;
import com.jy.medical.greendao.util.DaoUtils;

public class SelectTreatActivity extends BaseActivity {
    private String taskNo;
    private String diagnoseId;
    private String diagnoseName;
    private TextView treatConservative;
    private TextView treatOperation;
    private SelectedDiagnoseManager selectedDiagnoseManager;
    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_treat;
    }

    @Override
    public void initParms(Bundle parms) {
        taskNo=parms.getString("taskNo");
        diagnoseId=parms.getString("diagnoseId");
        diagnoseName=parms.getString("diagnoseName");
        selectedDiagnoseManager= DaoUtils.getSelectedDiagnoseInstance();
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "选择治疗方式", false, "");
        treatConservative= (TextView) findViewById(R.id.treat_conservative);
        treatOperation= (TextView) findViewById(R.id.treat_operation);
        treatConservative.setOnClickListener(this);
        treatOperation.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.page_head_image:
                finish();
                break;
            case R.id.treat_conservative:
                //保存选择治疗方式
                selectedDiagnoseManager=DaoUtils.getSelectedDiagnoseInstance();
                selectedDiagnoseManager.insertSingleData(new SelectedDiagnose(taskNo,diagnoseId,diagnoseName,"0","保守治疗"));
                MedicalApplication.getInstance().finishActivity(SelectDiagnoseActivity.class);
                MedicalApplication.getInstance().finishActivity(AddDiagnoseActivity.class);
                finish();
                break;
            case R.id.treat_operation:
                selectedDiagnoseManager=DaoUtils.getSelectedDiagnoseInstance();
                selectedDiagnoseManager.insertSingleData(new SelectedDiagnose(taskNo,diagnoseId,diagnoseName,"1","手术治疗"));
                MedicalApplication.getInstance().finishActivity(SelectDiagnoseActivity.class);
                MedicalApplication.getInstance().finishActivity(AddDiagnoseActivity.class);
                finish();
                break;
            default:
                break;
        }
    }
}
