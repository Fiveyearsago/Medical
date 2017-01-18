package com.jy.medical.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.HospitalAdapter;
import com.jy.medical.adapter.SearchAdapter;
import com.jy.medical.dao.RequestServerImpl;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.SearchData;
import com.jy.medical.greendao.manager.SearchDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.SwipeBackLayout;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.response.SpListDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class SearchHospitalActivity extends BaseActivity implements Callback.CommonCallback<String>, SearchAdapter.SCallBack, TextView.OnEditorActionListener {
    private SearchAdapter searchAdapter;
    private RecyclerView recordRecyclerView;
    private List<SearchData> searchDataList;
    private View recordView;
    private ClearEditText cleanableEditText;
    private ImageButton deleteRecordImage;
    private RecyclerView mRecyclerView;
    private List<HospitalData>hospitalDatas;
    private HospitalAdapter hospitalAdapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();
    private int page = 0;
    private String taskNo;
    private String flag="";
    private View blankLayout;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_hospital;
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
        setSearchTitle(findViewById(R.id.title_head), "取消");
        blankLayout = findViewById(R.id.blank_layout);
        mRecyclerView= (RecyclerView) findViewById(R.id.hospital_recyclerView);
        hospitalDatas=new ArrayList<>();
        initRecyclerView();
        cleanableEditText = (ClearEditText) findViewById(R.id.page_head_search_editText);
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
        hospitalAdapter= new HospitalAdapter(this,hospitalDatas,taskNo,flag);
        mAdapter = new RecyclerAdapterWithHF(hospitalAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void initRecordData() {
        SearchDataManager searchDataManager = DaoUtils.getSearchDataInstance();
        searchDataList = searchDataManager.getData(flag);
        searchAdapter = new SearchAdapter(this, searchDataList, this);
        recordRecyclerView.setAdapter(searchAdapter);
        if (searchDataList.size()== 0) {
            recordView.setVisibility(View.GONE);
        }else {
            recordView.setVisibility(View.VISIBLE);
        }
    }

    public void insertRecordData(String text) {
        SearchDataManager searchDataManager = DaoUtils.getSearchDataInstance();
        searchDataManager.insertSingleData(new SearchData(flag, text));
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
            searchData();

        }
        return false;
    }

    public void searchData() {
        String text = cleanableEditText.getText().toString().trim();
        if (!text.equals("")) {
            //执行搜索操作
            RequestServerImpl.getHospitalData("F4896B94148240FC9EFCDB9B5E2A17EB", text, 1, 20, "1", this);
            insertRecordData(text);
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @Override
    public void onSuccess(String result) {
        //请求数据成功
        Log.i("result", result);
        Gson responseGson = new Gson();
        Response response = responseGson.fromJson(result, Response.class);
        if (response != null && "1".equals(response.getResponseCode())) {
            String data = response.getData();
            hospitalDatas.clear();
            Log.i("ResponseCode", response.getResponseCode());
            SpListDTO spListDTO = responseGson.fromJson(data, SpListDTO.class);
            List<HosptialDTO> hosptialList = spListDTO.getHosptialList();
            List<DictKEYValueDTO> dictList = spListDTO.getDictList();
            hospitalDatas = JsonUtil.saveHospitalData(hosptialList, dictList);
        }
        recordView.setVisibility(View.GONE);

        hospitalAdapter = new HospitalAdapter(this, hospitalDatas,taskNo,flag);
        mAdapter = new RecyclerAdapterWithHF(hospitalAdapter);
        mRecyclerView.setAdapter(mAdapter);
        if (hospitalDatas.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            blankLayout.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            blankLayout.setVisibility(View.GONE);
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

    @Override
    public void search(int sIndex) {
        cleanableEditText.setText(searchDataList.get(sIndex).getSearchText().toString());
        cleanableEditText.setSelection(searchDataList.get(sIndex).getSearchText().length());
        searchData();
    }
}
