package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.DepartmentAdapter;
import com.jy.medical.greendao.entities.MedicalDepartment;
import com.jy.medical.greendao.manager.MedicalDepartmentManager;
import com.jy.medical.greendao.util.DaoUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectDepartmentsActivity extends BaseActivity implements DepartmentAdapter.NotifyCallBack {
    private DepartmentAdapter departmentAdapter;
    private List<MedicalDepartment> list;
    private RecyclerView recyclerView;
    private List<MedicalDepartment>checkedList;
    private TextView countText;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_departments;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "选择科室", true, "确定");
        countText= (TextView) findViewById(R.id.page_head_button);
        recyclerView= (RecyclerView) findViewById(R.id.department_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        checkedList=new ArrayList<>();
        initRecyclerData();
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                //点击确定，存储选择科室数据

                break;
        }

    }
    public  void initRecyclerData(){
        MedicalDepartmentManager medicalDepartmentManager= DaoUtils.getDepartmentInstance();
        list=medicalDepartmentManager.getDataList();
        departmentAdapter=new DepartmentAdapter(this,list,this);
        recyclerView.setAdapter(departmentAdapter);
    }

    @Override
    public void changeHeadInfo(MedicalDepartment medicalDepartment,boolean isChecked) {
        //适配器item点击事件通知页面刷新UI回调
        if (isChecked){
            if (!checkedList.contains(medicalDepartment)){
                checkedList.add(medicalDepartment);
            }
        }else {
            if (checkedList.contains(medicalDepartment)){
                checkedList.remove(medicalDepartment);
            }
        }
        if(checkedList.size()>0){
            countText.setText("确定"+"("+checkedList.size()+")");
        }else {
            countText.setText("确定");
        }
    }
}