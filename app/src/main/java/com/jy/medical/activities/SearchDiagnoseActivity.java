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

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.DiagnoseAdapter;
import com.jy.medical.adapter.HospitalAdapter;
import com.jy.medical.adapter.SearchAdapter;
import com.jy.medical.dao.RequestServerImpl;
import com.jy.medical.greendao.entities.Diagnose;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.SearchData;
import com.jy.medical.greendao.manager.DiagnoseManager;
import com.jy.medical.greendao.manager.HospitalDataManager;
import com.jy.medical.greendao.manager.SearchDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.SwipeBackLayout;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.dto.MtMedicalInjureItemDTO;
import com.jy.mobile.response.SpListDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

public class SearchDiagnoseActivity extends BaseActivity implements TextView.OnEditorActionListener, Callback.CommonCallback<String>, SearchAdapter.SCallBack {
    private SearchAdapter searchAdapter;
    private RecyclerView recordRecyclerView;
    private List<SearchData> searchDataList;
    private View recordView;
    private ClearEditText cleanableEditText;
    private ImageButton deleteRecordImage;
    private RecyclerView mRecyclerView;
    private DiagnoseAdapter diagnoseAdapter;
    private List<Diagnose> list;
    Handler handler = new Handler();
    private int page = 0;
    private String taskNo;
    private String flag = "";
    private String kindCode = "";
    private View blankLayout;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_diagnose;
    }

    @Override
    public void initParams(Bundle parms) {

        taskNo = parms.getString("taskNo");
//        flag=parms.getString("flag");
        kindCode = parms.getString("kindCode");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        MedicalApplication.getInstance().addActivity(this);
        setSearchTitle(findViewById(R.id.title_head), "取消");
        blankLayout = findViewById(R.id.blank_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.hospital_recyclerView);
        list = new ArrayList<>();
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
        diagnoseAdapter = new DiagnoseAdapter(this, list, taskNo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(diagnoseAdapter);
    }

    public void initRecordData() {
        SearchDataManager searchDataManager = DaoUtils.getSearchDataInstance();
        searchDataList = searchDataManager.getData("3");
        searchAdapter = new SearchAdapter(this, searchDataList, this);
        recordRecyclerView.setAdapter(searchAdapter);
        if (searchDataList.size() == 0) {
            recordView.setVisibility(View.GONE);
        } else {
            recordView.setVisibility(View.VISIBLE);
        }
    }

    public void insertRecordData(String text) {
        SearchDataManager searchDataManager = DaoUtils.getSearchDataInstance();
        searchDataManager.insertSingleData(new SearchData("3", text));
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
        searchDataManager.deleteData("3");
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
        list.clear();
        String text = cleanableEditText.getText().toString().trim();
        if (!text.equals("")) {
            //执行搜索操作
            RequestServerImpl.getDiagnoseData(kindCode, text, 1, 20, this);
            insertRecordData(text);
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onSuccess(String result) {
        //请求数据成功
//        Log.i("result", result);
        Gson responseGson = new Gson();
        Response response = responseGson.fromJson(result, Response.class);
        String data = response.getData();
        SpListDTO spListDTO = responseGson.fromJson(data, SpListDTO.class);
        List<MtMedicalInjureItemDTO> injureItemList = spListDTO.getInjureItemList();
        if (injureItemList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            blankLayout.setVisibility(View.VISIBLE);
            recordView.setVisibility(View.GONE);
            return;
        }
        List<Diagnose> diagnoseList = new ArrayList<Diagnose>();
        diagnoseList.clear();
        for (int i = 0; i < injureItemList.size(); i++) {
            MtMedicalInjureItemDTO mtMedicalInjureItemDTO = injureItemList.get(i);
            Diagnose diagnose = new Diagnose(mtMedicalInjureItemDTO.getId(), mtMedicalInjureItemDTO.getItemCode(), mtMedicalInjureItemDTO.getItemCnName());
            diagnoseList.add(diagnose);
        }
        list.addAll(diagnoseList);
        diagnoseAdapter.notifyDataSetChanged();
        recordView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        blankLayout.setVisibility(View.GONE);
//        diagnoseAdapter = new DiagnoseAdapter(this, list,taskNo);
//        mRecyclerView.setAdapter(diagnoseAdapter);
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
