package com.jy.medical.fragment;


import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jy.ah.bus.data.Request;
import com.jy.ah.bus.data.Response;
import com.jy.medical.R;
import com.jy.medical.activities.LoginActivity;
import com.jy.medical.activities.PlatformActivity;
import com.jy.medical.adapter.LawAdapter;
import com.jy.medical.entities.ToolData;
import com.jy.medical.util.NetOperaterUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.medical.widget.CleanableEditText;
import com.jy.mobile.dto.MsUserDTO;
import com.jy.mobile.request.QtLoginDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private int mPage;
    private CleanableEditText editTextAccount,editTextPsw;
    private Button loginBtn;
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
        editTextAccount= (CleanableEditText) accountLayout.findViewById(R.id.editText_account);
//        editTextPsw= (CleanableEditText) accountLayout.findViewById(R.id.editText_psw);
        loginBtn= (Button) accountLayout.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                requestServer();
                startActivity(new Intent(mContext, PlatformActivity.class));
            }
        });
        return accountLayout;
    }

    public void requestServer(){
        QtLoginDTO qtLoginDTO = new QtLoginDTO();
        qtLoginDTO.setUserName("0131002498");
        qtLoginDTO.setPassword("ta8888");
        Gson gson = new Gson();
        String data = gson.toJson(qtLoginDTO);
        ServerApiUtils.sendToServer(data, "001001", PublicString.URL_IFC,new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson=new Gson();
                Response response=responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    MsUserDTO msUserDTO = responseGson.fromJson(data, MsUserDTO.class);
                    Log.i("msUserDTO",msUserDTO.toString());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
