package com.jy.medical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.adapter.NursingAdapter;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.greendao.manager.NursingDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.widget.CleanableEditText;

import java.util.ArrayList;
import java.util.List;

public class AddNursingActivity extends BaseActivity {
    private String taskNo;
    private List<NursingData> nursingDataList;
    private NursingAdapter nursingAdapter;
    private NursingDataManager nursingDataManager;
    private RecyclerView recyclerView;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_nursing;
    }

    @Override
    public void initParms(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "添加护理人", true, "保存");
        nursingDataList = new ArrayList<>();
        findViewById(R.id.add_nursing_layout).setOnClickListener(this);
        findViewById(R.id.add_nursing_button).setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.nursing_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        nursingAdapter = new NursingAdapter(this, nursingDataList);
        recyclerView.setAdapter(nursingAdapter);
        nursingDataManager = DaoUtils.getNursingDataInstance();
        nursingAdapter.addItem();
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                //保存护理人
                if (check()){
                    nursingDataManager.insertData(nursingDataList);
                    finish();
                }
                break;
            case R.id.add_nursing_layout:
                //添加护理人
                nursingAdapter.addItem();
                break;
            case R.id.add_nursing_button:
                //添加护理人
                nursingAdapter.addItem();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            nursingDataList.get(requestCode).setIdValue(data.getStringExtra("value"));
            nursingDataList.get(requestCode).setIdKey(data.getStringExtra("key"));
//            nursingAdapter.notifyDataSetChanged();
            LinearLayout layout = (LinearLayout) recyclerView.getChildAt(requestCode);
            TextView textView= (TextView) layout.findViewById(R.id.item_nursing_id);
            textView.setText(data.getStringExtra("value"));
        }
    }

    private boolean check() {

        //检查护理人填写
        for (int i = 0; i < nursingDataList.size(); i++) {
            LinearLayout layout = (LinearLayout) recyclerView.getChildAt(i);
            CleanableEditText name, days, fee;
            TextView textView= (TextView) layout.findViewById(R.id.item_nursing_id);
            name = (CleanableEditText) layout.findViewById(R.id.item_nursing_name);
            days = (CleanableEditText) layout.findViewById(R.id.item_nursing_days);
            fee = (CleanableEditText) layout.findViewById(R.id.item_nursing_fee);
            String nursingName=name.getText().toString();
            String nursingDays=days.getText().toString();
            String nursingFee=fee.getText().toString();
            if (nursingName.equals("") || nursingDays.equals("")|| nursingFee.equals("")||textView.getText().toString().equals("")) {
                Toast.makeText(this, "请完善联系人信息", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                nursingDataList.get(i).setTaskNo(taskNo);
                nursingDataList.get(i).setName(nursingName);
                nursingDataList.get(i).setDays(nursingDays);
                nursingDataList.get(i).setFee(nursingFee);
                nursingDataList.get(i).setIdTypeCode("D110");
            }
        }
        return true;
    }
    public void saveData(){

    }
}
