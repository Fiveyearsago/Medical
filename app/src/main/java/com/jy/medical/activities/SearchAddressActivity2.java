package com.jy.medical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.jy.medical.R;
import com.jy.medical.adapter.AddressAdapter;
import com.jy.medical.adapter.SearchAdapter;
import com.jy.medical.greendao.entities.AddressData;
import com.jy.medical.greendao.entities.SearchData;
import com.jy.medical.greendao.manager.SearchDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchAddressActivity2 extends BaseActivity implements TextView.OnEditorActionListener, AddressAdapter.ACallBack, SearchAdapter.SCallBack {
    private SearchAdapter searchAdapter;
    private RecyclerView recordRecyclerView;
    private List<SearchData> searchDataList;
    private View recordView;
    private ClearEditText cleanableEditText;
    private ImageButton deleteRecordImage;
    private RecyclerView mRecyclerView;
    private List<AddressData> addressDataList;
    private AddressAdapter adapter;
    Handler handler = new Handler();
    private String cityName;
    private int index = 0;
    private InputtipsQuery inputquery;
    private Inputtips inputTips;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_address;
    }

    @Override
    public void initParams(Bundle parms) {
        cityName = parms.getString("cityName");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setSearchTitle(findViewById(R.id.title_head), "取消");
        mRecyclerView = (RecyclerView) findViewById(R.id.hospital_recyclerView);
        addressDataList = new ArrayList<>();
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
        cleanableEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initRecyclerView() {
        adapter = new AddressAdapter(this, addressDataList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    public void initRecordData() {
        SearchDataManager searchDataManager = DaoUtils.getSearchDataInstance();
        searchDataList = searchDataManager.getData("2");
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
        searchDataManager.insertSingleData(new SearchData("2", text));
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
        searchDataManager.deleteData("2");
        initRecordData();
    }

    public void searchText() {
        String text = cleanableEditText.getText().toString().trim();
        if (!text.equals("")) {
            //执行搜索操作
            inputquery = new InputtipsQuery(text, cityName);
            inputquery.setCityLimit(true);
            inputTips = new Inputtips(this, inputquery);
            inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                @Override
                public void onGetInputtips(List<Tip> list, int i) {
                    if (i == 1000) {
                        addressDataList.clear();
                        for (int j = 0; j < list.size(); j++) {
                            Log.i("suggest", list.get(j).getName() + "  " + list.get(j).getAddress());
                            Tip tip = list.get(j);
//                                Log.i("address",)
                            if (tip.getPoint() != null && !tip.getAddress().equals(""))
                                addressDataList.add(new AddressData(tip.getName(), tip.getDistrict() + tip.getAddress(), tip.getPoint().getLatitude(), tip.getPoint().getLongitude()));
                        }
                        adapter.setData(addressDataList);
                        adapter.notifyDataSetChanged();

                    }

                }
            });
            inputTips.requestInputtipsAsyn();
            insertRecordData(text);
//            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchText();
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void changeSearch(int index) {
        Intent intent = new Intent();
        intent.putExtra("lat", addressDataList.get(index).getLatitude());
        intent.putExtra("long", addressDataList.get(index).getLongitude());
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void search(int sIndex) {
        cleanableEditText.setText(searchDataList.get(sIndex).getSearchText());
        cleanableEditText.setSelection(cleanableEditText.getText().length());
        recordView.setVisibility(View.GONE);
        searchText();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
}
