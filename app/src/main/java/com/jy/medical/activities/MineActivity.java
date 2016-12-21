package com.jy.medical.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.util.ToastUtil;

public class MineActivity extends BaseActivity {
    private RadioButton radioPlatform, radioMine, radioTool;
    private boolean mIsExit;
    private View clearData, updateVersion;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        MedicalApplication.getInstance().addActivity(this);
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), false, "我的", false, "");
        findViewById(R.id.layout_mine_suggest).setOnClickListener(this);
        findViewById(R.id.layout_mine_head).setOnClickListener(this);
        findViewById(R.id.layout_collect).setOnClickListener(this);
        findViewById(R.id.clear_data).setOnClickListener(this);
        findViewById(R.id.update_version).setOnClickListener(this);

        View navView = findViewById(R.id.navView);
        radioPlatform = (RadioButton) navView.findViewById(R.id.radio_btn_platform);
        radioMine = (RadioButton) navView.findViewById(R.id.radio_btn_mine);
        radioTool = (RadioButton) navView.findViewById(R.id.radio_btn_tool);
        radioTool.setOnClickListener(this);
        radioPlatform.setOnClickListener(this);
        radioMine.setChecked(true);
        radioTool.setChecked(false);
        radioPlatform.setChecked(false);
    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.radio_btn_tool:
                startActivity(ToolActivity.class);
                break;
            case R.id.radio_btn_platform:
//                startActivity(PlatformActivity.class);
                startActivity(HomeActivity.class);
                break;
            case R.id.layout_mine_suggest:
                startActivity(FeedBackActivity.class);
                break;
            case R.id.layout_mine_head:
                startActivity(PersonalActivity.class);
                break;
            case R.id.layout_collect:
                startActivity(CollectActivity.class);
                break;
            case R.id.clear_data:
                ToastUtil.showToast(MineActivity.this,"已清理缓存");
                break;
            case R.id.update_version:
                ToastUtil.showToast(MineActivity.this,"已是最新版本");
                break;
            default:
                break;
        }
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
