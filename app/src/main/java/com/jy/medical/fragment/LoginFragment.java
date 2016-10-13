package com.jy.medical.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.activities.LoginActivity;
import com.jy.medical.adapter.LawAdapter;
import com.jy.medical.entities.ToolData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private int mPage;
    private TextInputEditText editTextAccount,editTextPsw;
    public static LoginFragment newInstance(int page, Context context) {
        mContext=context;
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public LoginFragment() {
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
        final View accountLayout = inflater.inflate(R.layout.login_account,container,false);
        editTextAccount= (TextInputEditText) accountLayout.findViewById(R.id.editText_account);
        editTextPsw= (TextInputEditText) accountLayout.findViewById(R.id.editText_psw);
        editTextAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()==0){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return accountLayout;
    }

}
