package com.jy.medical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.BaseFragmentPagerAdapter;
import com.jy.medical.controller.JsonToBean;
import com.jy.medical.fragment.BodyFragment;
import com.jy.medical.fragment.DiagnoseFragment;
import com.jy.medical.fragment.FollowDetailFragment;
import com.jy.medical.fragment.FollowRecordFragment;
import com.jy.medical.greendao.entities.CategoryData;
import com.jy.medical.greendao.manager.CategoryDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.widget.CustomViewpager;
import com.jy.medical.widget.SwipeBackLayout;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.request.QTSearchCityOrCateInjureDTO;
import com.jy.mobile.response.SpListDTO;
import com.jy.mobile.response.SpRecieveTaskDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class AddDiagnoseActivity extends BaseActivity {

    private CustomViewpager viewPager;
    private SegmentTabLayout segmentTabLayout;
    private BaseFragmentPagerAdapter adapter;
    private String taskNo;
    private List<Fragment> fragmentList;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_diagnose;
    }

    @Override
    public void initParams(Bundle parms) {
        if (parms!=null)
        taskNo=parms.getString("taskNo");
    }

    @Override
    public void initView() {
        setStatusBarTint();
//        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        MedicalApplication.getInstance().addActivity(this);
        View headView=findViewById(R.id.title_head_tab);
        headView.findViewById(R.id.page_head_tab_image).setOnClickListener(this);
//        headView.findViewById(R.id.page_head_tab_search).setOnClickListener(this);
        headView.findViewById(R.id.page_head_tab_search).setVisibility(View.GONE);
        segmentTabLayout = (SegmentTabLayout) headView.findViewById(R.id.segmentTabLayout);
        viewPager = (CustomViewpager) findViewById(R.id.viewPager);
        fragmentList = new ArrayList<>();
        Bundle bundle=new Bundle();
        bundle.putString("taskNo",taskNo);
        BodyFragment bodyFragment= BodyFragment.newInstance();
        DiagnoseFragment diagnoseFragment= DiagnoseFragment.newInstance();
        bodyFragment.setArguments(bundle);
        diagnoseFragment.setArguments(bundle);
        fragmentList.add(bodyFragment);
        fragmentList.add(diagnoseFragment);
        adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(),
                this,new String[]{"人体图","诊断列表"},fragmentList);
        viewPager.setAdapter(adapter);
        segmentTabLayout.setTabData(new String[]{"人体图","诊断列表"});
        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                viewPager.setCurrentItem(position);

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                viewPager.resetHeight(position);
                segmentTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
        requestHPData();
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentList.clear();
        fragmentList = null;
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_tab_image:
                MedicalApplication.getInstance().finishActivity(this);
//                finish();
                break;
            case R.id.page_head_tab_search:
                //搜索诊断
                Intent intent = new Intent(this, SearchDiagnoseActivity.class);
                intent.putExtra("kindCode", "");
                intent.putExtra("taskNo", taskNo);
                startActivity(intent);
                break;
        }
    }

    public void requestData(){
        QTSearchCityOrCateInjureDTO qtSearchCityOrCateInjureDTO=new QTSearchCityOrCateInjureDTO();
        qtSearchCityOrCateInjureDTO.setSearchCode("");
        Gson gson = new Gson();
        String data = gson.toJson(qtSearchCityOrCateInjureDTO);
        ServerApiUtils.sendToServer(data, "002030", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    SpListDTO spListDTO = responseGson.fromJson(data, SpListDTO.class);
                    Log.i("msUserDTO", spListDTO.toString());
                    List<DictKEYValueDTO> dictKEYValueDTOs=spListDTO.getDictList();
                    List<CategoryData> categoryDataList=new ArrayList<CategoryData>();
                    for (int i = 0; i < dictKEYValueDTOs.size(); i++) {
                        DictKEYValueDTO dictKEYValueDTO=dictKEYValueDTOs.get(i);
                        categoryDataList.add(new CategoryData(dictKEYValueDTO.getKey(),dictKEYValueDTO.getValue(),dictKEYValueDTO.getTypeCode()));
                    }
                    CategoryDataManager  categoryDataManager= DaoUtils.getCategoryDataInstance();
                    categoryDataManager.insertData(categoryDataList);
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
    public void requestHPData(){
        QTSearchCityOrCateInjureDTO qtSearchCityOrCateInjureDTO = new QTSearchCityOrCateInjureDTO();
        qtSearchCityOrCateInjureDTO.setSearchCode("");
        Gson gson = new Gson();
        String data = gson.toJson(qtSearchCityOrCateInjureDTO);
        ServerApiUtils.sendToServer(data, "002029", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    JsonUtil.saveHPData(data);
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
            MedicalApplication.getInstance().finishActivity(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
