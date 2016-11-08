package com.jy.medical.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.FollowEditActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowRecordFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private int mPage;
    private static FollowRecordFragment followRecordFragment;

    public static FollowRecordFragment newInstance() {
        if (followRecordFragment==null)
         followRecordFragment = new FollowRecordFragment();
        return followRecordFragment;
    }

    public FollowRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPage = getArguments().getInt(ARGS_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow_record,container,false);
        view.findViewById(R.id.record_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FollowEditActivity.class));
            }
        });
        return view;
    }

}
