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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.PlatformAdapter;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.greendao.manager.ClaimManager;
import com.jy.medical.greendao.util.DaoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlatformFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private List<PlatformData> list;
    private PlatformAdapter adapter;
    private RecyclerView recyclerView;
    private static PlatformFragment platformFragment;
    private String taskType = "";
    private int taskFlag = 0;
    private ClaimManager claimManager = DaoUtils.getClaimInstance();
    private static int mPage;
    private List<RadioButton> radioList = new ArrayList<>();
    private RadioGroup radioGroup;
    private View radioGroupLayout;
    private String[] texts1 = new String[]{"全部", "3天后超时", "5天后超时", "7天后超时", "30天后超时"};
    private String[] texts2 = new String[]{"全部", "已超时3天", "已超时5天", "已超时7天", "已超时30天"};
    private View rootView;

    public static PlatformFragment newInstance(int page, Context context) {
        mContext = context;
        mPage = page;
//        if (platformFragment==null)
//        platformFragment = new PlatformFragment();

        return new PlatformFragment();
    }

    public PlatformFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskType = getArguments().getString("taskType");
        taskFlag = getArguments().getInt("taskFlag");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.platform_content, null);
        radioGroupLayout = rootView.findViewById(R.id.platform_radioGroup_layout);

        radioGroup = (RadioGroup) radioGroupLayout.findViewById(R.id.platform_radioGroup);
        radioList.add((RadioButton) rootView.findViewById(R.id.radio1));
        radioList.add((RadioButton) rootView.findViewById(R.id.radio2));
        radioList.add((RadioButton) rootView.findViewById(R.id.radio3));
        radioList.add((RadioButton) rootView.findViewById(R.id.radio4));
        radioList.add((RadioButton) rootView.findViewById(R.id.radio5));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        setTaskTypeData(taskType);
                        break;
                    case R.id.radio2:
                        setTaskTypeData(taskType);
                        break;
                    case R.id.radio3:
                        setTaskTypeData(taskType);
                        break;
                    case R.id.radio4:
                        setTaskTypeData(taskType);
                        break;
                    case R.id.radio5:
                        setTaskTypeData(taskType);
                        break;
                }
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        adapter = new PlatformAdapter(mContext, list);
        recyclerView.setAdapter(adapter);
        radioList.get(0).setChecked(true);
        setFilterData(taskType, taskFlag);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void setFilterData(String taskType, int taskFlag) {
        this.taskType = taskType;
        this.taskFlag = taskFlag;
        switch (taskFlag) {
            case 0:
                if (radioGroupLayout != null)
                radioGroupLayout.setVisibility(View.GONE);
                list = claimManager.selectAllData(taskType, taskFlag);
                break;
            case 1:
                setRadioGroupText1();
                if (radioGroupLayout != null)
                    radioGroupLayout.setVisibility(View.VISIBLE);
                setTaskTypeData(taskType);
                break;
            case 2:
                setRadioGroupText2();
                if (radioGroupLayout != null)
                radioGroupLayout.setVisibility(View.VISIBLE);
                setTaskTypeData(taskType);
//                radioList.get(0).setChecked(true);
                break;
            case 3:
                if (radioGroupLayout != null)
                radioGroupLayout.setVisibility(View.GONE);
                list = claimManager.selectAllData(taskType, taskFlag);
                break;
        }
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    public void setTaskTypeData(String taskType) {
        int position = 0;
        for (int i = 0; i < radioList.size(); i++) {
            if (radioGroup.getCheckedRadioButtonId() == radioList.get(i).getId())
                position = i;
        }
        if (taskFlag == 2)
            list = claimManager.selectTaskTypeData2(taskType, position);
        if (taskFlag == 1)
            list = claimManager.selectTaskTypeData1(taskType, position);
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    private void setRadioGroupText1() {
        for (int i = 0; i < radioList.size(); i++) {
            radioList.get(i).setText(texts1[i]);
        }
    }

    private void setRadioGroupText2() {
        for (int i = 0; i < radioList.size(); i++) {
            radioList.get(i).setText(texts2[i]);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != rootView) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }
}
