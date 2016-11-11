package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.R;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.request.QTSearchCityOrCateInjureDTO;
import com.jy.mobile.request.QtSearchHosptialDTO;
import com.jy.mobile.response.SpListDTO;

import org.xutils.common.Callback;

import java.util.List;

public class SelectHospitalActivity extends BaseActivity {


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_hospital;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        getHospitalData();

    }

    @Override
    public void widgetClick(View v) {

    }

    public void  getHospitalData() {
        QtSearchHosptialDTO qtSearchHosptialDTO = new QtSearchHosptialDTO();
        qtSearchHosptialDTO.setDealLocalCode("F4896B94148240FC9EFCDB9B5E2A17EB");
        qtSearchHosptialDTO.setHospitalName("");
        qtSearchHosptialDTO.setPageNo(1);
        qtSearchHosptialDTO.setPageSize(20);
        qtSearchHosptialDTO.setFlag("1");

        Gson gson = new Gson();
        String data = gson.toJson(qtSearchHosptialDTO);
        ServerApiUtils.sendToServer(data, "002016", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson=new Gson();
                Response response=responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    SpListDTO spListDTO= responseGson.fromJson(data, SpListDTO.class);
                    List<HosptialDTO> hosptialList =spListDTO.getHosptialList();
                    List<DictKEYValueDTO> dictList=spListDTO.getDictList();
                    JsonUtil.saveHospitalData(hosptialList,dictList);
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
