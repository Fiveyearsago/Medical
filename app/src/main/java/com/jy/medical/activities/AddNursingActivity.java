package com.jy.medical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.CleanableEditText;
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

public class AddNursingActivity extends BaseActivity {
    private String taskNo;
    private NursingDataManager nursingDataManager;
    CleanableEditText name, days, fee;
    TextView textView;

    private String nameString = "";
    private String daysString = "";
    private String feeString = "";
    private String idKey = "";
    private String idValue = "";
    private String idTypeCode = "";
    private Long id;


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_nursing;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo = parms.getString("taskNo");
        nameString = parms.getString("nameString") == null ? "" : parms.getString("nameString");
        daysString = parms.getString("daysString") == null ? "" : parms.getString("daysString");
        feeString = parms.getString("feeString") == null ? "" : parms.getString("feeString");
        idKey = parms.getString("idKey") == null ? "" : parms.getString("idKey");
        idValue = parms.getString("idValue") == null ? "" : parms.getString("idValue");
        idTypeCode = parms.getString("idTypeCode") == null ? "" : parms.getString("idTypeCode");
        id = parms.getLong("Id") > 0 ? parms.getLong("Id") : 0;
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "添加护理人", true, "保存");
        nursingDataManager = DaoUtils.getNursingDataInstance();
        name = (CleanableEditText) findViewById(R.id.nursing_name);
        days = (CleanableEditText) findViewById(R.id.nursing_days);
        fee = (CleanableEditText) findViewById(R.id.nursing_fee);
        textView = (TextView) findViewById(R.id.nursing_id);
        textView.setOnClickListener(this);
        if (!nameString.equals("")) {
            name.setText(nameString);
        }
        if (!daysString.equals("")) {
            days.setText(daysString);
        }
        if (!feeString.equals("")) {
            fee.setText(feeString);
        }
        if (!idValue.equals("")) {
            textView.setText(idValue);
        }

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.nursing_id:
                Intent intent = new Intent(this, SelectCategoryActivity.class);
                intent.putExtra("kindCode", "D117");
                intent.putExtra("title", "护理人身份");
                startActivityForResult(intent, 0x10);
                break;
            case R.id.page_head_button:
                //保存护理人
                if (!check()) {
                    return;
                }
                NursingData nursingData = new NursingData(taskNo, name.getText().toString(), days.getText().toString(), fee.getText().toString(), idKey, idValue, idTypeCode);
                if (nameString.equals("")) {
                    if (!nursingDataManager.isExist(nursingData)) {
                        nursingDataManager.insertSingleData(nursingData);
                    } else {
                        ToastUtil.showToast(this, "此护理人已存在");
                        return;
                    }
                } else {
                    nursingData.setId(id);
                    nursingDataManager.updateData(nursingData);
                }
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x10) {
                textView.setText(data.getStringExtra("value"));
                idValue = data.getStringExtra("value");
                idKey = data.getStringExtra("key");
                idTypeCode = data.getStringExtra("requestKind");
            }
        }
    }

    private boolean check() {

        //检查护理人填写
        String nursingName = name.getText().toString();
        String nursingDays = days.getText().toString();
        String nursingFee = fee.getText().toString();
        if (nursingName.equals("") || nursingDays.equals("") || nursingFee.equals("") || textView.getText().toString().equals("")) {
            Toast.makeText(this, "请完善联系人信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void saveData() {

    }
}
