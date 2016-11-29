package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jy.medical.R;
import com.jy.medical.adapter.CategoryAdapter;
import com.jy.medical.greendao.entities.CategoryData;
import com.jy.medical.greendao.manager.CategoryDataManager;
import com.jy.medical.greendao.util.DaoUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectCategoryActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<CategoryData>categoryDataList;
    private CategoryAdapter categoryAdapter;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_category;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "选择行业", false, "");
        recyclerView= (RecyclerView) findViewById(R.id.category_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryDataList=new ArrayList<>();
        CategoryDataManager categoryDataManager= DaoUtils.getCategoryDataInstance();
        categoryDataList=categoryDataManager.getDataList("D110");
        categoryAdapter=new CategoryAdapter(this,categoryDataList);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_tab_image:
                finish();
                break;
        }
    }
}
