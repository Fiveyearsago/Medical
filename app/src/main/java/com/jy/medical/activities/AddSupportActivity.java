package com.jy.medical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
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
import com.jy.medical.util.MultiSelectUtil;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.CleanableEditText;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

public class AddSupportActivity extends BaseActivity {
    private String taskNo;
    private SupporterPersonManager supporterPersonManager = DaoUtils.getSupporterPersonInstance();
    private String houseKindKey = "", houseKindValue = "", relationKey = "", relationValue = "";
    private SupporterPerson supporterPerson = new SupporterPerson();
    private SupporterPerson supporterPersonTem = null;
    private ClearEditText nameEdit, ageEdit, yearsEdit, numEdit, addressEdit;
    private TextView textTime, textHouseholds, textRelation;
    private ImageButton location;

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
        supporterPersonTem = parms.getParcelable("supporterPerson") == null ? null : (SupporterPerson) parms.getParcelable("supporterPerson");
        if (supporterPersonTem != null) {
            houseKindKey = supporterPersonTem.getHouseKindKey();
            houseKindValue = supporterPersonTem.getHouseKindValue();
            relationKey = supporterPersonTem.getRelationKey();
            relationValue = supporterPersonTem.getRelationValue();
        }
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "添加被扶养人", true, "保存");
        nameEdit = (ClearEditText) findViewById(R.id.item_supporter_name);
        textTime = (TextView) findViewById(R.id.item_born_date);
        textHouseholds = (TextView) findViewById(R.id.item_households_nature);
        textRelation = (TextView) findViewById(R.id.item_relation);
        ageEdit = (ClearEditText) findViewById(R.id.item_age);
        yearsEdit = (ClearEditText) findViewById(R.id.item_years);
        numEdit = (ClearEditText) findViewById(R.id.item_num);
        addressEdit = (ClearEditText) findViewById(R.id.item_address);
        location = (ImageButton) findViewById(R.id.item_location);
        textTime.setOnClickListener(this);
        textRelation.setOnClickListener(this);
        textHouseholds.setOnClickListener(this);
        location.setOnClickListener(this);
        initViewData();
    }

    private void initViewData() {
        if (supporterPersonTem != null) {
            nameEdit.setText(supporterPersonTem.getName());
            textTime.setText(supporterPersonTem.getBornTime());
            textHouseholds.setText(supporterPersonTem.getHouseKindValue());
            houseKindKey = supporterPersonTem.getHouseKindKey();
            textRelation.setText(supporterPersonTem.getRelationValue());
            relationKey = supporterPersonTem.getRelationKey();
            ageEdit.setText(supporterPersonTem.getAge());
            yearsEdit.setText(supporterPersonTem.getYears());
            numEdit.setText(supporterPersonTem.getNum());
            addressEdit.setText(supporterPersonTem.getAddress());
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.item_relation:
                Intent intent2 = new Intent(this, SelectCategoryActivity.class);
                intent2.putExtra("kindCode", "D116");
                intent2.putExtra("requestKind", "01");
                intent2.putExtra("title", "与受害人关系");
                startActivityForResult(intent2, 0x11);
                break;
            case R.id.item_born_date:
                MultiSelectUtil.initTimePicker(this, textTime, textTime.getText().toString(), "选择出生日期");
                break;
            case R.id.item_households_nature:
                Intent intent = new Intent(this, SelectCategoryActivity.class);
                intent.putExtra("kindCode", "D109");
                intent.putExtra("requestKind", "02");
                intent.putExtra("title", "户口性质");
                startActivityForResult(intent, 0x10);
                break;
            case R.id.item_location:
                Intent intent1 = new Intent(this, SelectAddressActivity.class);
                startActivityForResult(intent1, 0x13);
                break;
            case R.id.page_head_button:
                //保存护理人
                if (check()) {
                    if (supporterPersonTem == null) {
                        if (!supporterPersonManager.isExist(supporterPerson))
                            supporterPersonManager.insertSingleData(supporterPerson);
                        else {
                            ToastUtil.showToast(this, "已存在此被扶养人");
                            return;
                        }
                    } else {
                        supporterPerson.setId(supporterPersonTem.getId());
                        supporterPersonManager.updateData(supporterPerson);
                    }
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x13:
                    addressEdit.setText(data.getStringExtra("address"));
                    break;
                case 0x10:
                    houseKindKey = data.getStringExtra("key");
                    houseKindValue = data.getStringExtra("value");
                    textHouseholds.setText(data.getStringExtra("value"));
                    break;
                case 0x11:
                    relationKey = data.getStringExtra("key");
                    relationValue = data.getStringExtra("value");
                    textRelation.setText(relationValue);
                    break;
            }
        }
    }

    private boolean check() {
        //检查护理人填写
        String name = nameEdit.getText().toString();
        String time = textTime.getText().toString();
        String households = textHouseholds.getText().toString();
        String relation = textRelation.getText().toString();
        String age = ageEdit.getText().toString();
        String years = yearsEdit.getText().toString();
        String num = numEdit.getText().toString();
        String address = addressEdit.getText().toString();
        if (name.equals("") || time.equals("") || households.equals("") || relation.equals("") || age.equals("") || years.equals("") || num.equals("") || address.equals("")) {
            Toast.makeText(this, "请完善被扶养人信息", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            supporterPerson.setTaskNo(taskNo);
            supporterPerson.setName(name);
            supporterPerson.setBornTime(time);
            supporterPerson.setHouseKindKey(houseKindKey);
            supporterPerson.setHouseKindValue(houseKindValue);
            supporterPerson.setRelationKey(relationKey);
            supporterPerson.setRelationValue(relationValue);
            supporterPerson.setAge(age);
            supporterPerson.setYears(years);
            supporterPerson.setNum(num);
            supporterPerson.setAddress(address);
        }

        return true;
    }

    public void saveData() {

    }
}
