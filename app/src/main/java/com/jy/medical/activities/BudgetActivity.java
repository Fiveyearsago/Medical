package com.jy.medical.activities;

import android.os.Bundle;
import android.view.View;

import com.jy.medical.R;
import com.jy.medical.widget.SwipeBackLayout;

public class BudgetActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_budget;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "人伤赔偿预算", false, "");

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
        }
    }
}
