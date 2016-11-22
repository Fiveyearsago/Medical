package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jy.ah.bus.data.Response;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.CityAdapter;
import com.jy.medical.adapter.ProvinceAdapter;
import com.jy.medical.controller.JsonToBean;
import com.jy.medical.greendao.entities.CityData;
import com.jy.medical.greendao.manager.CityDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.dto.RegionOrCateInjureDTO;
import com.jy.mobile.request.QTSearchCityOrCateInjureDTO;
import com.jy.mobile.request.QtRecieveTaskDTO;
import com.jy.mobile.response.SpRecieveTaskDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择城市列表
 */
public class SelectAreaActivity extends BaseActivity implements ProvinceAdapter.LCallBack {
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private RecyclerView provinceRecycler;
    private RecyclerView cityRecycler;
    private List<CityData> provinceList;
    private List<CityData> cityList;


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_area;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        MedicalApplication.getInstance().addActivity(this);
        setTitleState(findViewById(R.id.title_head), true, "选择区域", false, "");
//        getCityData();
        provinceList=new ArrayList<>();
        cityList=new ArrayList<>();
        provinceRecycler= (RecyclerView) findViewById(R.id.provinceRecycler);
        cityRecycler= (RecyclerView) findViewById(R.id.cityRecycler);
        provinceRecycler.setHasFixedSize(true);
        cityRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        provinceRecycler.setLayoutManager(layoutManager);
        cityRecycler.setLayoutManager(layoutManager1);
        CityDataManager cityDataManager= DaoUtils.getCityDataInstance();
        provinceList=cityDataManager.selectAllProvince();
        cityList=cityDataManager.selectAllCity(provinceList.get(0).getPid());
        provinceAdapter=new ProvinceAdapter(this,provinceList,this);
        provinceRecycler.setAdapter(provinceAdapter);
        cityAdapter=new CityAdapter(this,cityList);
        cityRecycler.setAdapter(cityAdapter);

    }

    @Override
    public void widgetClick(View v) {

    }

    public void getCityData() {
        QTSearchCityOrCateInjureDTO qtSearchCityOrCateInjureDTO = new QTSearchCityOrCateInjureDTO();
        qtSearchCityOrCateInjureDTO.setSearchCode("");
        Gson gson = new Gson();
        String data = gson.toJson(qtSearchCityOrCateInjureDTO);
        ServerApiUtils.sendToServer(data, "002024", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson=new Gson();
                Response response=responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    JsonUtil.saveCityData(data);

                    Log.i("ResponseCode", response.getResponseCode());
//                    List<RegionOrCateInjureDTO> datas= responseGson.fromJson(data, List<RegionOrCateInjureDTO>.class);
//                    Log.i("msUserDTO",spRecieveTaskDTO.toString());
//                    List<ClaimDTO> claimDTOList=spRecieveTaskDTO.getClaimList();
//                    JsonToBean.ClaimDTOToBean(claimDTOList);

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
    public void changeCityData(int position) {
        //选择省份后刷新城市列别相数据
        String pId=provinceList.get(position).getAid();
        CityDataManager cityDataManager=DaoUtils.getCityDataInstance();
        cityList=cityDataManager.selectAllCity(pId);
        cityAdapter=new CityAdapter(this,cityList);
        cityRecycler.setAdapter(cityAdapter);

    }
}
