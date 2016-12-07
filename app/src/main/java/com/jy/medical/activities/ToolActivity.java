package com.jy.medical.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.ToolAdapter;
import com.jy.medical.greendao.entities.ToolData;

import java.util.ArrayList;
import java.util.List;

public class ToolActivity extends BaseActivity {
    private RadioButton radioPlatform, radioMine, radioTool, radioLaw, radioCompensation, radioBudget;
    private RecyclerView toolRecycler;
    private List<ToolData>list;
    private ToolAdapter adapter;
    private boolean mIsExit;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tool;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        MedicalApplication.getInstance().addActivity(this);
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), false, "工具", false, "");
        View navView = findViewById(R.id.navView);
        radioPlatform = (RadioButton) navView.findViewById(R.id.radio_btn_platform);
        radioMine = (RadioButton) navView.findViewById(R.id.radio_btn_mine);
        radioTool = (RadioButton) navView.findViewById(R.id.radio_btn_tool);
        radioMine.setOnClickListener(this);
        radioPlatform.setOnClickListener(this);

        View headView= LayoutInflater.from(this).inflate(R.layout.tool_head_layout,null);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(layoutParams);
        View toolNavView = headView.findViewById(R.id.tool_nav);
        radioLaw = (RadioButton) toolNavView.findViewById(R.id.radio_btn_law);
        radioCompensation = (RadioButton) toolNavView.findViewById(R.id.radio_btn_compensation);
        radioBudget = (RadioButton) toolNavView.findViewById(R.id.radio_btn_budget);
        radioLaw.setOnClickListener(this);
        radioCompensation.setOnClickListener(this);
        radioBudget.setOnClickListener(this);

        toolRecycler = (RecyclerView) findViewById(R.id.tool_recycler);
        toolRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        toolRecycler.setLayoutManager(layoutManager);

        list=new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add(new ToolData("北京","以前在ListView当中，我们只要修改后数据用Adapter的notifyDatasetChange一下就可以更新界面然而在RecyclerView中还有一些更高级的用法"+i,"临床鉴定","2016-10-9"));
        }
        adapter=new ToolAdapter(this,list);
        adapter.addHeaderView(headView);
        toolRecycler.setAdapter(adapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        radioTool.setChecked(true);
        radioMine.setChecked(false);
        radioPlatform.setChecked(false);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                exit();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
