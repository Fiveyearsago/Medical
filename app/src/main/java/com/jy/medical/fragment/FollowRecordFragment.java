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
import com.jy.medical.activities.MedicalVisitsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowRecordFragment extends Fragment {
    private static FollowRecordFragment followRecordFragment;

    public static FollowRecordFragment newInstance() {
        if (followRecordFragment==null)
         followRecordFragment = new FollowRecordFragment();
        return followRecordFragment;
    }

    public FollowRecordFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Bundle data = getArguments();//获得从activity中传递过来的值
        View view = inflater.inflate(R.layout.fragment_follow_record,container,false);
        view.findViewById(R.id.record_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), FollowEditActivity.class);
                Intent intent=new Intent(getActivity(), MedicalVisitsActivity.class);
                intent.putExtra("taskNo",data.getString("taskNo"));
                startActivity(intent);
            }
        });
        return view;
    }

}
