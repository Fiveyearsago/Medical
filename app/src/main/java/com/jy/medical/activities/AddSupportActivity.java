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
import com.jy.medical.adapter.SupporterAdapter;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.greendao.entities.SupporterPerson;
import com.jy.medical.greendao.manager.NursingDataManager;
import com.jy.medical.greendao.manager.SupporterPersonManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.widget.CleanableEditText;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

public class AddSupportActivity extends BaseActivity {
    private String taskNo;
    private List<SupporterPerson> supporterPersonList;
    private SupporterAdapter supporterAdapter;
    private SupporterPersonManager supporterPersonManager = DaoUtils.getSupporterPersonInstance();
    private RecyclerView recyclerView;
    private String houseKindKey = "", houseKindValue = "", relationKey = "", relationValue = "";

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_supporter;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo = parms.getString("taskNo");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "添加被扶养人", true, "保存");
        supporterPersonList = new ArrayList<>();
        findViewById(R.id.add_supporter_layout).setOnClickListener(this);
        findViewById(R.id.add_supporter_button).setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.supporter_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        supporterAdapter = new SupporterAdapter(this, supporterPersonList);
        recyclerView.setAdapter(supporterAdapter);
        supporterAdapter.addItem();
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                //保存护理人
                if (check()) {
                    supporterPersonManager.insertData(supporterPersonList);
                    finish();
                }
                break;
            case R.id.add_supporter_layout:
                //添加护理人
                supporterAdapter.addItem();
                break;
            case R.id.add_supporter_button:
                //添加护理人
                supporterAdapter.addItem();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x13) {
                LinearLayout layout = (LinearLayout) recyclerView.getChildAt(0);
                ClearEditText textView = (ClearEditText) layout.findViewById(R.id.item_address);
                textView.setText(data.getStringExtra("address"));

            } else {
                if (data.getStringExtra("requestKind").equals("01")) {
                    relationKey = data.getStringExtra("key");
                    relationValue = data.getStringExtra("value");
                    LinearLayout layout = (LinearLayout) recyclerView.getChildAt(requestCode);
                    TextView textView = (TextView) layout.findViewById(R.id.item_relation);
                    textView.setText(data.getStringExtra("value"));
//                supporterPersonList.get(requestCode).setRelationValue(data.getStringExtra("value"));
//                supporterPersonList.get(requestCode).setRelationKey(data.getStringExtra("key"));
                } else if (data.getStringExtra("requestKind").equals("02")) {
                    houseKindKey = data.getStringExtra("key");
                    houseKindValue = data.getStringExtra("value");
                    LinearLayout layout = (LinearLayout) recyclerView.getChildAt(requestCode);
                    TextView textView = (TextView) layout.findViewById(R.id.item_households_nature);
                    textView.setText(data.getStringExtra("value"));
//                supporterPersonList.get(requestCode).setHouseKindValue(data.getStringExtra("value"));
//                supporterPersonList.get(requestCode).setHouseKindKey(data.getStringExtra("key"));
                }
//            supporterAdapter.notifyDataSetChanged();
            }
        }
    }

    private boolean check() {

        //检查护理人填写
        for (int i = 0; i < supporterPersonList.size(); i++) {
            LinearLayout layout = (LinearLayout) recyclerView.getChildAt(i);
            ClearEditText nameEdit, ageEdit, yearsEdit, numEdit, addressEdit;

            TextView textTime, textHouseholds, textRelation;
            nameEdit = (ClearEditText) layout.findViewById(R.id.item_supporter_name);
            textTime = (TextView) layout.findViewById(R.id.item_born_date);
            textHouseholds = (TextView) layout.findViewById(R.id.item_households_nature);
            textRelation = (TextView) layout.findViewById(R.id.item_relation);
            ageEdit = (ClearEditText) layout.findViewById(R.id.item_age);
            yearsEdit = (ClearEditText) layout.findViewById(R.id.item_years);
            numEdit = (ClearEditText) layout.findViewById(R.id.item_num);
            addressEdit = (ClearEditText) layout.findViewById(R.id.item_address);
            String name = nameEdit.getText().toString();
            String time = textTime.getText().toString();
            String households = textHouseholds.getText().toString();
            String relation = textRelation.getText().toString();
            String age = ageEdit.getText().toString();
            String years = yearsEdit.getText().toString();
            String num = numEdit.getText().toString();
            String address = addressEdit.getText().toString();
            if (name.equals("") || time.equals("") || households.equals("") || relation.equals("") || age.equals("") || years.equals("") || num.equals("") || address.equals("")) {
                Toast.makeText(this, "请完被扶养人信息", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                supporterPersonList.get(i).setTaskNo(taskNo);
                supporterPersonList.get(i).setName(name);
                supporterPersonList.get(i).setBornTime(time);
                supporterPersonList.get(i).setHouseKindKey(houseKindKey);
                supporterPersonList.get(i).setHouseKindValue(houseKindValue);
                supporterPersonList.get(i).setRelationKey(relationKey);
                supporterPersonList.get(i).setRelationValue(relationValue);
                supporterPersonList.get(i).setAge(age);
                supporterPersonList.get(i).setYears(years);
                supporterPersonList.get(i).setNum(num);
                supporterPersonList.get(i).setAddress(address);
            }
        }
        return true;
    }

    public void saveData() {

    }
}
