package com.jy.medical.activities;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.PlatformAdapter;
import com.jy.medical.controller.JsonToBean;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.greendao.manager.ClaimManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.request.QtRecieveTaskDTO;
import com.jy.mobile.response.SpRecieveTaskDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Calendar;
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
    private List<RadioButton> radioList;
    private RadioGroup radioGroup;
    private ClaimManager claimManager = DaoUtils.getClaimInstance();
    @Override
    public void initData() {

    }

    public void initDateData() {
        radioList = new ArrayList<>();
        View viewLayout = findViewById(R.id.date_layout);
        View view = viewLayout.findViewById(R.id.platform_radioGroup);
        radioGroup = (RadioGroup) viewLayout.findViewById(R.id.platform_radioGroup);

        radioList.add((RadioButton) view.findViewById(R.id.radio1));
        radioList.add((RadioButton) view.findViewById(R.id.radio2));
        radioList.add((RadioButton) view.findViewById(R.id.radio3));
        radioList.add((RadioButton) view.findViewById(R.id.radio4));
        radioList.add((RadioButton) view.findViewById(R.id.radio5));
        radioList.add((RadioButton) view.findViewById(R.id.radio6));
        radioList.add((RadioButton) view.findViewById(R.id.radio7));
        radioGroup.check(radioList.get(3).getId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("checkedId", checkedId + "");
            }
        });

        map = new HashMap<>();
        //设置日期
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR); //获取当前年份
        mMonth = c.get(Calendar.MONTH);//获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码

        for (int i = -3; i <= 3; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            Log.i("Day", calendar.get(Calendar.DAY_OF_MONTH) + "");
            map.put("" + calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.DAY_OF_MONTH));
            radioList.get(i + 3).setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
        }
        radioList.get(3).setText("今");
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
        MedicalApplication.getInstance().addActivity(this);
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
        platformRecycler = (RecyclerView) findViewById(R.id.platform_recycler);
        platformRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        platformRecycler.setLayoutManager(layoutManager);
        list = new ArrayList<>();

        list = claimManager.selectAllData();
        adapter = new PlatformAdapter(this, list);
        platformRecycler.setAdapter(adapter);
        initDateData();
        requestData();
    }

    private void requestData() {
        QtRecieveTaskDTO qtRecieveTaskDTO = new QtRecieveTaskDTO();
//        qtRecieveTaskDTO.setUserId("000111");
        qtRecieveTaskDTO.setUserId("0131002498");
        Gson gson = new Gson();
        String data = gson.toJson(qtRecieveTaskDTO);
        ServerApiUtils.sendToServer(data, "002001", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    SpRecieveTaskDTO spRecieveTaskDTO = responseGson.fromJson(data, SpRecieveTaskDTO.class);
                    Log.i("msUserDTO", spRecieveTaskDTO.toString());
                    List<ClaimDTO> claimDTOList = spRecieveTaskDTO.getClaimList();
                    JsonToBean.ClaimDTOToBean(claimDTOList);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.radio_btn_mine:
                overridePendingTransition(0, 0);
                this.finish();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        radioPlatform.setChecked(true);
        radioMine.setChecked(false);
        radioTool.setChecked(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case 0x11:
                    list = claimManager.selectAllData();
                    adapter=new PlatformAdapter(PlatformActivity.this,list);
                    adapter.notifyDataSetChanged();
                    platformRecycler.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    }
}
