package com.jy.medical.dao;

import android.util.Log;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.util.JsonUtil;
import com.jy.medical.util.PublicString;
import com.jy.medical.util.ServerApiUtils;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.request.QtSearchDisabilityDTO;
import com.jy.mobile.request.QtSearchHosptialDTO;
import com.jy.mobile.response.SpListDTO;

import org.xutils.common.Callback;

import java.util.List;

/**
 * Created by songran on 16/11/14.
 */

public class RequestServerImpl {
    public static void getHospitalData(String dealLocalCode,String hospitalName,int pageNo,int pageSize,String flag,Callback.CommonCallback<String> callBack) {
        QtSearchHosptialDTO qtSearchHosptialDTO = new QtSearchHosptialDTO();
        qtSearchHosptialDTO.setDealLocalCode(dealLocalCode);
        qtSearchHosptialDTO.setHospitalName(hospitalName);
        qtSearchHosptialDTO.setPageNo(pageNo);
        qtSearchHosptialDTO.setPageSize(pageSize);
        qtSearchHosptialDTO.setFlag(flag);
        Gson gson = new Gson();
        String data = gson.toJson(qtSearchHosptialDTO);
        ServerApiUtils.sendToServer(data, "002016", PublicString.URL_IFC, callBack);
    }

    public static void getDiagnoseData(String kindCode, String searchCode, int pageNo, int pageSize, Callback.CommonCallback<String> callBack) {
        QtSearchDisabilityDTO qtSearchVehicleDTO = new QtSearchDisabilityDTO();
        qtSearchVehicleDTO.setKindCode(kindCode);
        qtSearchVehicleDTO.setSearchCode(searchCode);
        qtSearchVehicleDTO.setPageNo(pageNo);
        qtSearchVehicleDTO.setPageSize(20);
        Gson gson = new Gson();
        String data = gson.toJson(qtSearchVehicleDTO);
        ServerApiUtils.sendToServer(data, "002022", PublicString.URL_IFC, callBack);
    }
}
