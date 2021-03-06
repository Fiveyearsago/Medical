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
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

public class SelectCategoryActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<CategoryData>categoryDataList;
    private CategoryAdapter categoryAdapter;
    private String kindCode="";
    private String requestKind="";
    private String title = "";

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_category;
    }

    @Override
    public void initParams(Bundle parms) {
        if (parms!=null){
            kindCode=parms.getString("kindCode");
            requestKind=parms.getString("requestKind")==null?"":parms.getString("requestKind");
            title = parms.getString("title") == null ? "" : parms.getString("title");
        }
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, title, false, "");
        recyclerView= (RecyclerView) findViewById(R.id.category_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryDataList=new ArrayList<>();
        CategoryDataManager categoryDataManager= DaoUtils.getCategoryDataInstance();
        categoryDataList=categoryDataManager.getDataList(kindCode);
        categoryAdapter=new CategoryAdapter(this,categoryDataList,requestKind);
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
