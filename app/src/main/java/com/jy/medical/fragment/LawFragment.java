package com.jy.medical.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.R;
import com.jy.medical.activities.SelectDiagnoseActivity;
import com.jy.medical.adapter.LawAdapter;
import com.jy.medical.greendao.entities.CategoryData;
import com.jy.medical.greendao.entities.Diagnose;
import com.jy.medical.greendao.entities.LawData;
import com.jy.medical.greendao.entities.ToolData;
import com.jy.medical.greendao.manager.CategoryDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.dto.LawsDTO;
import com.jy.mobile.dto.MtMedicalInjureItemDTO;
import com.jy.mobile.request.QTSearchCityOrCateInjureDTO;
import com.jy.mobile.request.QtLawsDTO;
import com.jy.mobile.request.QtSearchDisabilityDTO;
import com.jy.mobile.response.SpListDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LawFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private int mPage;
    private List<LawData>list;
    private LawAdapter adapter;
    private RecyclerView recyclerView;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerAdapterWithHF mAdapter;
    private Handler handler = new Handler();


    public static LawFragment newInstance(int page,Context context) {
        mContext=context;
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        LawFragment fragment = new LawFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public LawFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.law_content,container,false);
        ptrClassicFrameLayout= (PtrClassicFrameLayout) view.findViewById(R.id.diagnose_recyclerView_frame);
         recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        adapter=new LawAdapter(mContext,list);
        mAdapter=new RecyclerAdapterWithHF(adapter);
        recyclerView.setAdapter(mAdapter);
        initRecyclerView();
        requestData(1,false);
        return view;
    }
    private void initRecyclerView() {
        ptrClassicFrameLayout.setLoadMoreEnable(true);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPage = 1;
                        //刷新数据源
                        requestData(mPage,true);
                        ptrClassicFrameLayout.refreshComplete();
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
                        ++mPage;
                        requestData(mPage,false);

                    }
                }, 1000);
            }
        });
    }
    public void requestData(int page, final boolean flag){
        QtLawsDTO qtLawsDTO=new QtLawsDTO();
        qtLawsDTO.setPageNo(page);
        qtLawsDTO.setPageSize(10);
        Gson gson = new Gson();
        String data = gson.toJson(qtLawsDTO);
        ServerApiUtils.sendToServer(data, "002002", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
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
                    Log.i("ResponseCode", response.getResponseCode());
                    List<LawData> newDataList=JsonUtil.changeToList(data);
                    list.addAll(newDataList);
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }else {
                    if (flag){
                        ptrClassicFrameLayout.refreshComplete();
                    }else {
                        ptrClassicFrameLayout.loadMoreComplete(true);

                    }
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
