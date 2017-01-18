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
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrNoRefreshHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.MaimGradeAdapter;
import com.jy.medical.adapter.SearchAdapter;
import com.jy.medical.greendao.entities.MaimGradeData;
import com.jy.medical.greendao.entities.SearchData;
import com.jy.medical.greendao.manager.SearchDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.widget.ClearEditText;
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchMaimActivity extends BaseActivity implements TextView.OnEditorActionListener, SearchAdapter.SCallBack {
    private SearchAdapter searchAdapter;
    private RecyclerView recordRecyclerView;
    private List<SearchData> searchDataList;
    private View recordView;
    private ClearEditText cleanableEditText;
    private ImageButton deleteRecordImage;
    private RecyclerView mRecyclerView;
    private List<MaimGradeData> maimDatas;
    private MaimGradeAdapter maimAdapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();
    private int mPage = 0;
    private String taskNo;
    private String flag = "";
    private View blankLayout;
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_maim;
    }

    @Override
    public void initParams(Bundle parms) {

        taskNo = parms.getString("taskNo");
//        flag=parms.getString("flag");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        MedicalApplication.getInstance().addActivity(this);
        setSearchTitle(findViewById(R.id.title_head), "取消");
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.recyclerView_frame);

        blankLayout = findViewById(R.id.blank_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.hospital_recyclerView);
        maimDatas = new ArrayList<>();
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
        ptrClassicFrameLayout.setLoadMoreEnable(true);
        ptrClassicFrameLayout.setPullToRefresh(false);
        ptrClassicFrameLayout.setPtrHandler(new PtrNoRefreshHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPage = 1;

                    }
                }, 1000);
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
                        mPage++;
                        searchData();
                    }
                }, 1000);
            }
        });
    }

    private void initRecyclerView() {
        maimAdapter = new MaimGradeAdapter(this, maimDatas, taskNo);
        mAdapter = new RecyclerAdapterWithHF(maimAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void initRecordData() {
        SearchDataManager searchDataManager = DaoUtils.getSearchDataInstance();
        searchDataList = searchDataManager.getData("4");
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
        searchDataManager.insertSingleData(new SearchData("4", text));
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
        searchDataManager.deleteData("4");
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
            requestData(mPage, text);
            insertRecordData(text);
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void requestData(int page, String searchText) {
        ServerApiUtils.requestMaimGradeData(taskNo, page, "", searchText, new ServerApiUtils.MCallBack() {
            @Override
            public void getDataSuccess(List<MaimGradeData> maimGradeDataList, int pageTotal) {
                Log.i("pageTotal", maimGradeDataList.size() + "    " + pageTotal + "");
                maimDatas.addAll(maimGradeDataList);
                maimAdapter.setData(maimDatas);
                maimAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
                recordView.setVisibility(View.GONE);
                if (0 == maimGradeDataList.size()) {
                    ptrClassicFrameLayout.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    blankLayout.setVisibility(View.VISIBLE);
                } else {
                    ptrClassicFrameLayout.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    blankLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void getDataFailed() {

            }
        });

    }


    @Override
    public void search(int sIndex) {
        cleanableEditText.setText(searchDataList.get(sIndex).getSearchText().toString());
        cleanableEditText.setSelection(searchDataList.get(sIndex).getSearchText().length());
        searchData();
    }
}
