package com.jy.medical.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.LawAdapter;
import com.jy.medical.greendao.entities.ToolData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LawFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private int mPage;
    private List<ToolData>list;
    private LawAdapter adapter;
    private RecyclerView recyclerView;

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
         recyclerView = (RecyclerView) view.findViewById(R.id.law_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add(new ToolData("北京","以前在ListView当中，我们只要修改后数据用Adapter的notifyDatasetChange一下就可以更新界面然而在RecyclerView中还有一些更高级的用法"+i,"北京高级法院北京高级法院北京高级法院北京高级法院北京高级法院北京高级法院","2016-10-9"));
        }
        adapter=new LawAdapter(mContext,list);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
