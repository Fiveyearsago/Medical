package com.jy.medical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.DiagnoseAdapter;
import com.jy.medical.greendao.entities.Diagnose;
import com.jy.medical.greendao.entities.Inquire;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.widget.SwipeBackLayout;
import com.jy.mobile.dto.MtMedicalInjureItemDTO;
import com.jy.mobile.request.QtSearchDisabilityDTO;
import com.jy.mobile.response.SpListDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class SelectDiagnoseActivity extends BaseActivity {
    private String kindCode;
    private String name;
    private int page=1;
    private TextView textTitle;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerView mRecyclerView;
    private DiagnoseAdapter diagnoseAdapter;
    private List<Diagnose>list;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();
    private String taskNo;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_diagnose;
    }

    @Override
    public void initParams(Bundle parms) {
        kindCode=parms.getString("kindCode");
        name=parms.getString("name");
        taskNo=parms.getString("taskNo");
    }

    @Override
    public void initView() {
        MedicalApplication.getInstance().addActivity(this);
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setThirdNavState(findViewById(R.id.title_head_third),"头部",R.drawable.nav_image_selector,R.drawable.serach_selector);
        textTitle= (TextView) findViewById(R.id.page_third_head_text);
        ptrClassicFrameLayout= (PtrClassicFrameLayout) findViewById(R.id.diagnose_recyclerView_frame);
        mRecyclerView= (RecyclerView) findViewById(R.id.diagnose_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        diagnoseAdapter=new DiagnoseAdapter(this,list,taskNo);
        mAdapter=new RecyclerAdapterWithHF(diagnoseAdapter);
        mRecyclerView.setAdapter(mAdapter);
        textTitle.setText(name);
        initRecyclerView();
        requestData(page,false);
    }

    private void bindData() {

    }

    private void initRecyclerView() {
        ptrClassicFrameLayout.setLoadMoreEnable(true);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        //刷新数据源
                        requestData(page,true);
                    }
                }, 1500);
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //加载下页数据
                        //TODO
                        ++page;
                        requestData(page,false);

//                        Toast.makeText(SelectDiagnoseActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }

    private void requestData(int page, final boolean flag) {
        QtSearchDisabilityDTO qtSearchVehicleDTO = new QtSearchDisabilityDTO();
        qtSearchVehicleDTO.setKindCode(kindCode);
        qtSearchVehicleDTO.setSearchCode("");
        qtSearchVehicleDTO.setPageNo(page);
        qtSearchVehicleDTO.setPageSize(20);
        Gson gson = new Gson();
        String data = gson.toJson(qtSearchVehicleDTO);
        ServerApiUtils.sendToServer(data, "002022", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    if (flag){
                        list.clear();
                        ptrClassicFrameLayout.refreshComplete();
                    }else {
                        ptrClassicFrameLayout.loadMoreComplete(true);
                    }
                    String data = response.getData();
                    SpListDTO spListDTO = responseGson.fromJson(data, SpListDTO.class);
                    List<MtMedicalInjureItemDTO> injureItemList= spListDTO.getInjureItemList();
                    List<Diagnose> diagnoseList=new ArrayList<Diagnose>();
                    diagnoseList.clear();
                    for (int i = 0; i < injureItemList.size(); i++) {
                        MtMedicalInjureItemDTO mtMedicalInjureItemDTO=injureItemList.get(i);
                        Diagnose diagnose=new Diagnose(mtMedicalInjureItemDTO.getId(),mtMedicalInjureItemDTO.getItemCode(),mtMedicalInjureItemDTO.getItemCnName());
                        diagnoseList.add(diagnose);
                    }
                    list.addAll(diagnoseList);
                    mAdapter.notifyDataSetChanged();
                    Log.i("list",list.size()+"");
                    bindData();
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
            case R.id.page_third_head_image:
                finish();
                break;
            case R.id.page_third_head_collect:
                //搜索诊断
                Intent intent = new Intent(this, SearchDiagnoseActivity.class);
                intent.putExtra("kindCode", kindCode);
                intent.putExtra("taskNo", taskNo);
                startActivity(intent);
                break;
        }
    }
}
