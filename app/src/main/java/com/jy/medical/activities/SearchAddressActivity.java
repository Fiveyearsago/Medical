package com.jy.medical.activities;

import android.content.Intent;
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

public class SearchAddressActivity extends BaseActivity implements TextView.OnEditorActionListener, AddressAdapter.ACallBack, OnGetGeoCoderResultListener, SearchAdapter.SCallBack {
    private SearchAdapter searchAdapter;
    private RecyclerView recordRecyclerView;
    private List<SearchData> searchDataList;
    private View recordView;
    private ClearEditText cleanableEditText;
    private ImageButton deleteRecordImage;
    private RecyclerView mRecyclerView;
    private List<AddressData> list;
    private AddressAdapter adapter;
    Handler handler = new Handler();
    private SuggestionSearch mSuggestionSearch = SuggestionSearch.newInstance();
    private GeoCoder mSearch;
    private String cityName;
    private int index = 0;

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
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
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
        adapter = new AddressAdapter(this, list, this);
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String text = cleanableEditText.getText().toString().trim();
            if (!text.equals("")) {
                //执行搜索操作
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(cleanableEditText.getText().toString())
                        .city(cityName));
                insertRecordData(text);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
        return false;
    }


    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
                return;
                //未找到相关结果
            }
            //获取在线建议检索结果
            recordView.setVisibility(View.GONE);
            List<SuggestionResult.SuggestionInfo> suggestionInfoList = res.getAllSuggestions();
            list.clear();
            for (int i = 0; i < suggestionInfoList.size(); i++) {
                SuggestionResult.SuggestionInfo s = suggestionInfoList.get(i);
                if (s.pt != null)
                    list.add(new AddressData(s.key, "", s.pt.latitude, s.pt.longitude));
            }
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude())));
            index = 0;

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }

    @Override
    public void changeSearch(int index) {
        Intent intent = new Intent();
        intent.putExtra("lat", list.get(index).getLatitude());
        intent.putExtra("long", list.get(index).getLongitude());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        Log.i("address1", geoCodeResult.getAddress());


    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        LatLng l = new LatLng(reverseGeoCodeResult.getLocation().latitude, reverseGeoCodeResult.getLocation().longitude);
        Log.i("address", reverseGeoCodeResult.getAddress());
        if (index < list.size())
            list.get(index).setText(reverseGeoCodeResult.getAddress());
        else return;
        if (index == list.size() - 1) {
            adapter.setData(list);
            adapter.notifyDataSetChanged();
            return;
        } else {
            index++;
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(new LatLng(list.get(index).getLatitude(), list.get(index).getLongitude())));
        }
    }

    @Override
    public void search(int sIndex) {
        cleanableEditText.setText(searchDataList.get(sIndex).getSearchText());
        cleanableEditText.setSelection(cleanableEditText.getText().length());
        recordView.setVisibility(View.GONE);
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword(cleanableEditText.getText().toString())
                .city(cityName));
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
}
