package com.jy.medical.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.jy.medical.R;

public class ToolActivity extends BaseActivity {
    private RadioButton radioPlatform, radioMine, radioTool,radioLaw,radioCompensation,radioBudget;
    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tool;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), false, "工具", false, "");
        View navView = findViewById(R.id.navView);
        radioPlatform = (RadioButton) navView.findViewById(R.id.radio_btn_platform);
        radioMine = (RadioButton) navView.findViewById(R.id.radio_btn_mine);
        radioTool = (RadioButton) navView.findViewById(R.id.radio_btn_tool);
        radioMine.setOnClickListener(this);
        radioPlatform.setOnClickListener(this);
        radioTool.setChecked(true);
        View toolNavView = findViewById(R.id.tool_nav);
        radioLaw = (RadioButton) toolNavView.findViewById(R.id.radio_btn_law);
        radioCompensation = (RadioButton) toolNavView.findViewById(R.id.radio_btn_compensation);
        radioBudget = (RadioButton) toolNavView.findViewById(R.id.radio_btn_budget);
        radioLaw.setOnClickListener(this);
        radioCompensation.setOnClickListener(this);
        radioBudget.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.radio_btn_mine:
                startActivity(MineActivity.class);
                break;
            case R.id.radio_btn_platform:
                startActivity(PlatformActivity.class);
                break;
            case R.id.radio_btn_law:
                startActivity(LawActivity.class);
                break;
            case R.id.radio_btn_compensation:
                startActivity(CompensationActivity.class);
                break;
            case R.id.radio_btn_budget:
//                startActivity(MineActivity.class);
                break;

            default:
                break;
        }
    }
}
