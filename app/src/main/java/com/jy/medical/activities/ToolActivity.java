package com.jy.medical.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.LawAdapter;
import com.jy.medical.adapter.ToolAdapter;
import com.jy.medical.greendao.entities.LawData;
import com.jy.medical.greendao.entities.ToolData;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.mobile.request.QtLawsDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class ToolActivity extends BaseActivity {
    private RadioButton radioPlatform, radioMine, radioTool, radioLaw, radioCompensation, radioBudget;
    private RecyclerView toolRecycler;
    private List<LawData>list;
    private ToolAdapter adapter;
    private boolean mIsExit;
    private ProgressDialog progressDialog;

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
        adapter=new ToolAdapter(this,list);
        adapter.addHeaderView(headView);
        toolRecycler.setAdapter(adapter);
        requestData(1);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.radio_btn_mine:
                startActivity(MineActivity.class);
                break;
            case R.id.radio_btn_platform:
//                startActivity(PlatformActivity.class);
                startActivity(HomeActivity.class);
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
    public void requestData(int page){
        progressDialog = new ProgressDialog(this, R.style.Custom_Progress);
        progressDialog.setMessage("正在加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setTitle("提示");
        progressDialog.show();
        QtLawsDTO qtLawsDTO=new QtLawsDTO();
        qtLawsDTO.setPageNo(page);
        qtLawsDTO.setPageSize(10);
        Gson gson = new Gson();
        String data = gson.toJson(qtLawsDTO);
        ServerApiUtils.sendToServer(data, "002002", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
//                    if (progressDialog.isShowing()){
//                        progressDialog.dismiss();
//                    }
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    List<LawData> newDataList= JsonUtil.changeToList(data);
                    list.addAll(newDataList);
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
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
