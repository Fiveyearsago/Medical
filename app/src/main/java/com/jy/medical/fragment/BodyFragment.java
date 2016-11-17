package com.jy.medical.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;

/**
 * Created by songran on 16/11/17.
 */

public class BodyFragment extends Fragment{
    private static BodyFragment bodyFragment;
    public static BodyFragment newInstance() {
        if (bodyFragment==null)
            bodyFragment = new BodyFragment();
        return bodyFragment;
    }

    public BodyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Bundle data = getArguments();//获得从activity中传递过来的值
        View view = inflater.inflate(R.layout.fragment_body,container,false);
        return view;
    }
}
