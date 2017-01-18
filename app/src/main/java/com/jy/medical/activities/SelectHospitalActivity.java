package com.jy.medical.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.PtrNoRefreshHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.HospitalAdapter;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.manager.HospitalDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.SPUtils;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.SwipeBackLayout;
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
    private int page = 1;
    private String taskNo;
    private TextView cityText;
    private String flag="";
    private String dealLocalCode="";

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_hospital;
    }

    @Override
    public void initParams(Bundle parms) {
        taskNo=parms.getString("taskNo");
        flag=parms.getString("flag");
    }
    @Override
    public void initView() {

        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        MedicalApplication.getInstance().addActivity(this);
        setLocationSearchState(findViewById(R.id.title_head));
        cityText= (TextView) findViewById(R.id.page_head_button);
        cityText.setText(SPUtils.get(this,"cityName","北京市").toString());
        findViewById(R.id.text_search).setOnClickListener(this);
        ptrClassicFrameLayout= (PtrClassicFrameLayout) findViewById(R.id.hospital_recyclerView_frame);
        mRecyclerView= (RecyclerView) findViewById(R.id.hospital_recyclerView);
        hospitalDatas=new ArrayList<>();
        dealLocalCode=SPUtils.get(this,"cityKey","").toString();
        initRecyclerView();
//        getHospitalData(true);
    }
    private void initRecyclerView() {

//        HospitalDataManager hospitalDataManager= DaoUtils.getHospitalInstance();
//        hospitalDatas=hospitalDataManager.getData();
        hospitalAdapter= new HospitalAdapter(this,hospitalDatas,taskNo,flag);
        mAdapter = new RecyclerAdapterWithHF(hospitalAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        ptrClassicFrameLayout.setLoadMoreEnable(true);
        ptrClassicFrameLayout.setPullToRefresh(true);
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
                        page = 1;
                        //刷新数据源
                        hospitalDatas.clear();
                        getHospitalData(true);
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
                        page++;
                        getHospitalData(false);

                    }
                }, 1000);
            }
        });
    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                MedicalApplication.getInstance().finishActivity(this);
//                finish();
                break;
            case R.id.page_head_button:
                Intent intent=new Intent(this,SelectAreaActivity.class);
                startActivityForResult(intent,0x11);
                break;
            case R.id.page_head_text:
                Bundle bundle=new Bundle();
                bundle.putString("taskNo",taskNo);
                bundle.putString("flag",flag);
                startActivity(SearchHospitalActivity.class,bundle);
                break;
            default:
                break;
        }
    }

    public void getHospitalData(final boolean refreshFlag) {
        Log.i("pageNo",page+"");
        ServerApiUtils.requestHospitalData(dealLocalCode, "", page, 20, flag, new ServerApiUtils.RCallBack() {
            @Override
            public void getDataSuccess(List<HospitalData> hospitals,int pageTotal) {
                if (0==hospitals.size()||pageTotal==hospitalDatas.size()){
                    ptrClassicFrameLayout.loadMoreComplete(true);
                    ptrClassicFrameLayout.setNoMoreData();
//                    Toast.makeText(SelectHospitalActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                hospitalDatas.addAll(hospitals);
                hospitalAdapter.setData(hospitalDatas);
                hospitalAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
                if (refreshFlag) {
                    ptrClassicFrameLayout.refreshComplete();
                } else {
                    ptrClassicFrameLayout.loadMoreComplete(true);
                }
            }

            @Override
            public void getDataFailed() {

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case 0x11:
                    cityText.setText(data.getStringExtra("cityName"));
                    dealLocalCode=data.getStringExtra("cityKey");
                    page=1;
                    hospitalDatas.clear();
                    getHospitalData(true);
                    break;
            }
        }
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
