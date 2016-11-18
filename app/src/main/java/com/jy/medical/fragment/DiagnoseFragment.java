package com.jy.medical.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.HumanPartsAdapter;
import com.jy.medical.greendao.entities.HumanParts;
import com.jy.medical.greendao.manager.HumanPartsManager;
import com.jy.medical.greendao.util.DaoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songran on 16/11/17.
 */

public class DiagnoseFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<HumanParts> humanPartsList;
    private HumanPartsAdapter humanPartsAdapter;
    private static DiagnoseFragment diagnoseFragment;

    public static DiagnoseFragment newInstance() {
        if (diagnoseFragment == null)
            diagnoseFragment = new DiagnoseFragment();
        return diagnoseFragment;
    }

    public DiagnoseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Bundle data = getArguments();//获得从activity中传递过来的值
        View view = inflater.inflate(R.layout.fragment_diagnose, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        humanPartsList = new ArrayList<>();
        HumanPartsManager humanPartsManager = DaoUtils.getHumanPartsInstance();
        humanPartsList = humanPartsManager.getDataList();
        humanPartsAdapter=new HumanPartsAdapter(getActivity(),humanPartsList,data.getString("taskNo"));
        recyclerView.setAdapter(humanPartsAdapter);
        return view;
    }
}
