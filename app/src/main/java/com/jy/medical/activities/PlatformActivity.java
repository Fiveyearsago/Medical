package com.jy.medical.activities;

import android.app.ActivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jy.medical.R;

public class PlatformActivity extends BaseActivity {
    private RadioButton radioPlatform, radioMine, radioTool;
    private boolean mIsExit;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_platform;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), false, "跟踪平台", false, "");
        View navView = findViewById(R.id.navView);
        radioPlatform = (RadioButton) navView.findViewById(R.id.radio_btn_platform);
        radioMine = (RadioButton) navView.findViewById(R.id.radio_btn_mine);
        radioTool = (RadioButton) navView.findViewById(R.id.radio_btn_tool);
        radioMine.setOnClickListener(this);
        radioTool.setOnClickListener(this);
        radioPlatform.setChecked(true);
        radioMine.setChecked(false);
        radioTool.setChecked(false);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.radio_btn_mine:
                overridePendingTransition(0, 0);
                startActivity(MineActivity.class);
                break;
            case R.id.radio_btn_tool:
                overridePendingTransition(0, 0);
                startActivity(ToolActivity.class);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
//                this.finish();
                ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                manager.killBackgroundProcesses(getPackageName());

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
