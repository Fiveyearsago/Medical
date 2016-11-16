package com.jy.medical.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.R;
import com.jy.medical.adapter.HospitalAdapter;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.manager.HospitalDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.request.QTSearchCityOrCateInjureDTO;
import com.jy.mobile.request.QtSearchHosptialDTO;
import com.jy.mobile.response.SpListDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class SelectHospitalActivity extends BaseActivity {
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerView mRecyclerView;
    private List<HospitalData>hospitalDatas;
    private HospitalAdapter hospitalAdapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();
    private int page = 0;
    private String taskNo;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_hospital;
    }

    @Override
    public void initParms(Bundle parms) {
        taskNo=parms.getString("taskNo");
    }
    @Override
    public void initView() {
//        getHospitalData();
        setStatusBarTint();
        setNavState(findViewById(R.id.title_head_second), "选择医院");
        findViewById(R.id.text_search).setOnClickListener(this);
        ptrClassicFrameLayout= (PtrClassicFrameLayout) findViewById(R.id.hospital_recyclerView_frame);
        mRecyclerView= (RecyclerView) findViewById(R.id.hospital_recyclerView);
        hospitalDatas=new ArrayList<>();
        initRecyclerView();
    }
    private void initRecyclerView() {

        HospitalDataManager hospitalDataManager= DaoUtils.getHospitalInstance();
        hospitalDatas=hospitalDataManager.getData();
        hospitalAdapter= new HospitalAdapter(this,hospitalDatas,taskNo);
        mAdapter = new RecyclerAdapterWithHF(hospitalAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        //刷新数据源
                        //hospitalDatas.clear();
                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLoadMoreEnable(true);
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
                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.loadMoreComplete(true);
                        page++;
                        Toast.makeText(SelectHospitalActivity.this, "load more complete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_second_image:
                finish();
                break;
            case R.id.nav_layout:
                startActivity(SelectAreaActivity.class);
                break;
            case R.id.text_search:
                startActivity(SearchHospitalActivity.class);
                break;
            default:
                break;
        }
    }

    public void getHospitalData() {
        QtSearchHosptialDTO qtSearchHosptialDTO = new QtSearchHosptialDTO();
        qtSearchHosptialDTO.setDealLocalCode("F4896B94148240FC9EFCDB9B5E2A17EB");
        qtSearchHosptialDTO.setHospitalName("");
        qtSearchHosptialDTO.setPageNo(1);
        qtSearchHosptialDTO.setPageSize(20);
        qtSearchHosptialDTO.setFlag("1");
        Gson gson = new Gson();
        String data = gson.toJson(qtSearchHosptialDTO);
        ServerApiUtils.sendToServer(data, "002016", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    SpListDTO spListDTO = responseGson.fromJson(data, SpListDTO.class);
                    List<HosptialDTO> hosptialList = spListDTO.getHosptialList();
                    List<DictKEYValueDTO> dictList = spListDTO.getDictList();
                    JsonUtil.saveHospitalData(hosptialList, dictList);
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
}
