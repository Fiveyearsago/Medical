package com.jy.medical.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.jy.medical.adapter.SearchAdapter;
import com.jy.medical.dao.RequestServerImpl;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.SearchData;
import com.jy.medical.greendao.manager.HospitalDataManager;
import com.jy.medical.greendao.manager.SearchDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.widget.CleanableEditText;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.response.SpListDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class SearchHospitalActivity extends BaseActivity implements TextView.OnEditorActionListener, Callback.CommonCallback<String> {
    private SearchAdapter searchAdapter;
    private RecyclerView recordRecyclerView;
    private List<SearchData> searchDataList;
    private View recordView;
    private CleanableEditText cleanableEditText;
    private ImageButton deleteRecordImage;
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
        return R.layout.activity_search_hospital;
    }

    @Override
    public void initParms(Bundle parms) {
        taskNo=parms.getString("taskNo");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setSearchTitle(findViewById(R.id.title_head), "取消");
        ptrClassicFrameLayout= (PtrClassicFrameLayout) findViewById(R.id.hospital_recyclerView_frame);
        mRecyclerView= (RecyclerView) findViewById(R.id.hospital_recyclerView);
        hospitalDatas=new ArrayList<>();
        initRecyclerView();
        cleanableEditText = (CleanableEditText) findViewById(R.id.page_head_search_editText);
        recordView = findViewById(R.id.record_layout);
        deleteRecordImage = (ImageButton) findViewById(R.id.delete_record_image);
        deleteRecordImage.setOnClickListener(this);
        recordRecyclerView = (RecyclerView) findViewById(R.id.record_recyclerView);
        recordRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recordRecyclerView.setLayoutManager(layoutManager);
        searchDataList = new ArrayList<>();
        initRecordData();
        cleanableEditText.setOnEditorActionListener(this);
    }

    private void initRecyclerView() {
        hospitalAdapter= new HospitalAdapter(this,hospitalDatas,taskNo);
        mAdapter = new RecyclerAdapterWithHF(hospitalAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
//                ptrClassicFrameLayout.autoRefresh(true);
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
//                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLoadMoreEnable(true);
                        ptrClassicFrameLayout.setPullToRefresh(false);
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
                        Toast.makeText(SearchHospitalActivity.this, "load more complete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }

    public void initRecordData() {
        SearchDataManager searchDataManager = DaoUtils.getSearchDataInstance();
        searchDataList = searchDataManager.getData("1");
        searchAdapter = new SearchAdapter(this, searchDataList);
        recordRecyclerView.setAdapter(searchAdapter);
        if (searchDataList.size()== 0) {
            recordView.setVisibility(View.GONE);
        }else {
            recordView.setVisibility(View.VISIBLE);
        }
    }

    public void insertRecordData(String text) {
        SearchDataManager searchDataManager = DaoUtils.getSearchDataInstance();
        searchDataManager.insertSingleData(new SearchData("1", text));
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_search_button:
                finish();
                break;
            case R.id.delete_record_image:
                //删除历史记录
                deleteRecordData();
                break;
        }
    }

    private void deleteRecordData() {
        SearchDataManager searchDataManager = DaoUtils.getSearchDataInstance();
        searchDataManager.deleteData("1");
        initRecordData();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String text = cleanableEditText.getText().toString().trim();
            if (!text.equals("")) {
                //执行搜索操作
                RequestServerImpl.getHospitalData("F4896B94148240FC9EFCDB9B5E2A17EB",text,1,20,"1",this);
                insertRecordData(text);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
        return false;
    }

    @Override
    public void onSuccess(String result) {
        //请求数据成功
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
        recordView.setVisibility(View.GONE);
        HospitalDataManager hospitalDataManager=DaoUtils.getHospitalInstance();
        hospitalDatas.clear();
        hospitalDatas=hospitalDataManager.getData();
        hospitalAdapter = new HospitalAdapter(this, hospitalDatas,taskNo);
        mAdapter = new RecyclerAdapterWithHF(hospitalAdapter);
        mRecyclerView.setAdapter(mAdapter);
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

}
