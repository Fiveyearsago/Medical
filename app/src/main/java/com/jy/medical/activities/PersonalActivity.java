package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class PersonalActivity extends BaseActivity {


    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "个人资料", false, "");
        findViewById(R.id.layout_person_name).setOnClickListener(this);
        findViewById(R.id.layout_person_phone).setOnClickListener(this);
        findViewById(R.id.layout_person_psw).setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                break;
            case R.id.layout_person_head:

                break;
            case R.id.layout_person_name:
                startActivity(PersonNameActivity.class);
                break;
            case R.id.layout_person_born:
                break;
            case R.id.layout_person_phone:
                startActivity(ChangePhoneActivity.class);
                break;
            case R.id.layout_person_psw:
                startActivity(EditPswActivity.class);
                break;
            default:
                break;
        }
    }
}
