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
import com.jy.medical.adapter.PlatformAdapter;
import com.jy.medical.entities.PlatformData;
import com.jy.medical.entities.ToolData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlatformFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private int mPage;
    private List<PlatformData>list;
    private PlatformAdapter adapter;
    private RecyclerView recyclerView;

    public static PlatformFragment newInstance(int page, Context context) {
        mContext=context;
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        PlatformFragment fragment = new PlatformFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public PlatformFragment() {
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
            list.add(new PlatformData("任务"+i,"2016-10-9","完成","C201610141120"));
        }
        adapter=new PlatformAdapter(mContext,list);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
