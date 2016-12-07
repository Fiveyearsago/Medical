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
import com.jy.medical.activities.SearchHospitalActivity;
import com.jy.medical.adapter.MaimGradeAdapter;
import com.jy.medical.adapter.PlatformAdapter;
import com.jy.medical.greendao.entities.Diagnose;
import com.jy.medical.greendao.entities.MaimGradeData;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.mobile.dto.DisabilityDescrDTO;
import com.jy.mobile.dto.MtMedicalInjureItemDTO;
import com.jy.mobile.request.QtSearchDisabilityDTO;
import com.jy.mobile.response.SpListDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaimGradeFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private int mPage;
    private String gradeCode="";
    private List<MaimGradeData> list;
    private MaimGradeAdapter adapter;
    private RecyclerView recyclerView;
    private String taskNo;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();

    public static MaimGradeFragment newInstance(int page, Context context) {
        mContext = context;
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        Log.i("ARGS_PAGE",page+"");
        MaimGradeFragment fragment = new MaimGradeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public MaimGradeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int num = getArguments().getInt(ARGS_PAGE);
        Bundle data = getArguments();
        taskNo=data.getString("taskNo");
        gradeCode=data.getString("gradeCode");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.maim_grade_content, container, false);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.hospital_recyclerView_frame);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        adapter = new MaimGradeAdapter(mContext, list, taskNo);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        recyclerView.setAdapter(mAdapter);
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
                        mPage = 1;
                        //刷新数据源
                        list.clear();
                        requestData(mPage, true);
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
                        requestData(mPage,  false);
                    }
                }, 1000);
            }
        });

        return view;
    }

    private void requestData(int page, final boolean flag) {
        Log.i("gradeCode",gradeCode);
        ServerApiUtils.requestMaimGradeData(taskNo, page, gradeCode, "", new ServerApiUtils.MCallBack() {
            @Override
            public void getDataSuccess(List<MaimGradeData> maimGradeDataList, int pageTotal) {
                Log.i("list.size()", list.size() + "");

                if (0 == maimGradeDataList.size() || pageTotal == list.size()) {
                    ptrClassicFrameLayout.loadMoreComplete(true);
                    ptrClassicFrameLayout.refreshComplete();
                    Toast.makeText(getActivity(), "无更多数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                list.addAll(maimGradeDataList);
                adapter.setData(list);
                adapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
                if (flag) {
                    ptrClassicFrameLayout.refreshComplete();
//                    Toast.makeText(getActivity(), "已完成刷新", Toast.LENGTH_SHORT).show();
                } else {
                    ptrClassicFrameLayout.loadMoreComplete(true);
//                    Toast.makeText(getActivity(), "已加载完成", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void getDataFailed() {

            }
        });

    }
}
