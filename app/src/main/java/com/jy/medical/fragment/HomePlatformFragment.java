package com.jy.medical.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.R;
import com.jy.medical.adapter.PlatformAdapter;
import com.jy.medical.controller.JsonToBean;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.greendao.manager.ClaimManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.request.QtRecieveTaskDTO;
import com.jy.mobile.response.SpRecieveTaskDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePlatformFragment extends Fragment implements View.OnClickListener {
    public static final String ARGS_PAGE = "args_page";
    private static Context mContext;
    private RecyclerView platformRecycler;
    private List<PlatformData> list;
    private PlatformAdapter adapter;
    private TextView platformTitleText;
    private ImageView platformInfoImage;
    private TextView platformAllTextView;
    private int mYear, mMonth, mDay;
    private Map<String, String> map;
    private List<RadioButton> radioList;
    private RadioGroup radioGroup;
    private ClaimManager claimManager = DaoUtils.getClaimInstance();


    public static HomePlatformFragment newInstance(int page, Context context) {
        mContext=context;
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        HomePlatformFragment fragment = new HomePlatformFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public HomePlatformFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_platform,container,false);
        initDateData(view);
        View navView = view.findViewById(R.id.navView);
        platformRecycler = (RecyclerView) view.findViewById(R.id.platform_recycler);
        platformRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        platformRecycler.setLayoutManager(layoutManager);
        list = new ArrayList<>();

        list = claimManager.selectAllData();
        adapter = new PlatformAdapter(mContext, list);
        platformRecycler.setAdapter(adapter);
        requestData();
        return view;
    }
    public void initDateData(View contentView) {
        radioList = new ArrayList<>();
        View viewLayout = contentView.findViewById(R.id.date_layout);
        View view = viewLayout.findViewById(R.id.platform_radioGroup);
        radioGroup = (RadioGroup) viewLayout.findViewById(R.id.platform_radioGroup);

        radioList.add((RadioButton) view.findViewById(R.id.radio1));
        radioList.add((RadioButton) view.findViewById(R.id.radio2));
        radioList.add((RadioButton) view.findViewById(R.id.radio3));
        radioList.add((RadioButton) view.findViewById(R.id.radio4));
        radioList.add((RadioButton) view.findViewById(R.id.radio5));
        radioList.add((RadioButton) view.findViewById(R.id.radio6));
        radioList.add((RadioButton) view.findViewById(R.id.radio7));
        radioGroup.check(radioList.get(3).getId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("checkedId", checkedId + "");
            }
        });

        map = new HashMap<>();
        //设置日期
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR); //获取当前年份
        mMonth = c.get(Calendar.MONTH);//获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码

        for (int i = -3; i <= 3; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            Log.i("Day", calendar.get(Calendar.DAY_OF_MONTH) + "");
            map.put("" + calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.DAY_OF_MONTH));
            radioList.get(i + 3).setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
        }
        radioList.get(3).setText("今");
    }
    private void requestData() {
        QtRecieveTaskDTO qtRecieveTaskDTO = new QtRecieveTaskDTO();
//        qtRecieveTaskDTO.setUserId("000111");
        qtRecieveTaskDTO.setUserId("0131002498");
        Gson gson = new Gson();
        String data = gson.toJson(qtRecieveTaskDTO);
        ServerApiUtils.sendToServer(data, "002001", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    SpRecieveTaskDTO spRecieveTaskDTO = responseGson.fromJson(data, SpRecieveTaskDTO.class);
                    Log.i("msUserDTO", spRecieveTaskDTO.toString());
                    List<ClaimDTO> claimDTOList = spRecieveTaskDTO.getClaimList();
                    JsonToBean.ClaimDTOToBean(claimDTOList);
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
    @Override
    public void onClick(View v) {

    }
}
