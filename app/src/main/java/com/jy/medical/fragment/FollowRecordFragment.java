package com.jy.medical.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowRecordFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static FollowRecordFragment newInstance() {
        Bundle args = new Bundle();

//        args.putInt(ARGS_PAGE, page);
        FollowRecordFragment fragment = new FollowRecordFragment();
//        fragment.setArguments(args);
        return fragment;
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
//        TextView textView = (TextView) view.findViewById(R.id.text_page);
//        textView.setText("第"+mPage+"页");
        return view;
    }

}