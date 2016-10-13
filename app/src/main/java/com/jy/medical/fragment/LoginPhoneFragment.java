package com.jy.medical.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.adapter.LawAdapter;
import com.jy.medical.entities.ToolData;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPhoneFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private int mPage;
    private List<ToolData>list;
    private LawAdapter adapter;
    private RecyclerView recyclerView;

    public static LoginPhoneFragment newInstance(int page, Context context) {
        mContext=context;
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        LoginPhoneFragment fragment = new LoginPhoneFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public LoginPhoneFragment() {
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
        final View accountPhoneLayout = inflater.inflate(R.layout.login_phone,container,false);
        return accountPhoneLayout;
    }

}
