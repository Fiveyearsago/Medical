package com.jy.medical.activities;

import android.app.ActivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.adapter.PlatformAdapter;
import com.jy.medical.adapter.ToolAdapter;
import com.jy.medical.entities.PlatformData;
import com.jy.medical.entities.ToolData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlatformActivity extends BaseActivity {
    private RadioButton radioPlatform, radioMine, radioTool;
    private boolean mIsExit;
    private RecyclerView platformRecycler;
    private List<PlatformData> list;
    private PlatformAdapter adapter;
    private TextView platformTitleText;
    private ImageView platformInfoImage;
    private TextView platformAllTextView;
    private int mYear, mMonth, mDay;
    private Map<String, String> map;

    @Override
    public void initData() {

    }

    public  void initDateData() {
        map = new HashMap<>();
        //设置日期
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR); //获取当前年份
        mMonth = c.get(Calendar.MONTH);//获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码

        for (int i = -3; i < 3; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            Log.i("Day", calendar.get(Calendar.DAY_OF_MONTH) + "");
            map.put(""+calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.YEAR)+"."+calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.DAY_OF_MONTH));
        }
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
//        setTitleState(findViewById(R.id.title_head), false, "跟踪平台", false, "");
        View view = findViewById(R.id.platform_title_head);
        platformInfoImage = (ImageView) view.findViewById(R.id.platform_page_head_image);
        platformTitleText = (TextView) findViewById(R.id.platform_page_head_text);
        platformAllTextView = (TextView) findViewById(R.id.platform_page_head_button);
        platformInfoImage.setOnClickListener(this);
        platformAllTextView.setOnClickListener(this);
        View navView = findViewById(R.id.navView);
        radioPlatform = (RadioButton) navView.findViewById(R.id.radio_btn_platform);
        radioMine = (RadioButton) navView.findViewById(R.id.radio_btn_mine);
        radioTool = (RadioButton) navView.findViewById(R.id.radio_btn_tool);
        radioMine.setOnClickListener(this);
        radioTool.setOnClickListener(this);
        radioPlatform.setChecked(true);
        radioMine.setChecked(false);
        radioTool.setChecked(false);

        platformRecycler = (RecyclerView) findViewById(R.id.platform_recycler);
        platformRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        platformRecycler.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new PlatformData("小明" + i, "2016-10-9", "完成", "C201610141120"));
        }
        adapter = new PlatformAdapter(this, list);
        platformRecycler.setAdapter(adapter);
        initDateData();

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
            case R.id.platform_page_head_image:
                break;
            case R.id.platform_page_head_button:
                startActivity(AllPlatformActivity.class);
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
