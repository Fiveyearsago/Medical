package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;
import com.jy.medical.widget.SwipeBackLayout;

public class PswCommitActivity extends BaseActivity {


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_psw_commit;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "修改密码", true, "确定");

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            default:
                break;
        }
    }
}
