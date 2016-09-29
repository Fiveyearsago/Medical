package com.jy.medical.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jy.medical.R;

public class MineActivity extends BaseActivity {

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), false, "我的", false, "");
        findViewById(R.id.layout_mine_suggest).setOnClickListener(this);
        findViewById(R.id.layout_mine_head).setOnClickListener(this);
    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.layout_mine_suggest:
                startActivity(FeedBackActivity.class);
                break;
            case R.id.layout_mine_head:
                startActivity(PersonalActivity.class);
                break;
            default:
                break;
        }
    }


}
