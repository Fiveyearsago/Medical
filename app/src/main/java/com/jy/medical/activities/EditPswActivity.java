package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;

public class EditPswActivity extends BaseActivity {


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_psw;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "修改密码", true, "下一步");
        findViewById(R.id.person_psw_next).setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.page_head_image:
                finish();
                break;
            case R.id.person_psw_next:
                startActivity(PswCommitActivity.class);
                break;
            default:
                break;
        }
    }
}
