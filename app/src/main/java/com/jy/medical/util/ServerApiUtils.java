package com.jy.medical.util;

import android.util.Log;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Request;
import com.jy.ah.bus.data.Response;
import com.jy.medical.controller.JsonToBean;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.MaimGradeData;
import com.jy.medical.greendao.entities.MedicalDepartment;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.dto.DisabilityDescrDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.request.QtRecieveTaskDTO;
import com.jy.mobile.request.QtSearchDisabilityDTO;
import com.jy.mobile.request.QtSearchHosptialDTO;
import com.jy.mobile.response.SpListDTO;
import com.jy.mobile.response.SpRecieveTaskDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songran on 16/10/27.
 */

public class ServerApiUtils {
    public interface RCallBack{
        void getDataSuccess(List<HospitalData> hospitalDatas,int pageTotal);
        void getDataFailed();
    }
    public interface MCallBack{
        void getDataSuccess(List<MaimGradeData> maimGradeDataList,int pageTotal);
        void getDataFailed();
    }
    public static void requestTaskData() {
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
    public static void requestMaimGradeData(final String taskNo, int page, String gradeCode, String searchCode, final MCallBack mCallBack) {
        QtSearchDisabilityDTO qtSearchVehicleDTO = new QtSearchDisabilityDTO();
        qtSearchVehicleDTO.setGadeCode(gradeCode);
        qtSearchVehicleDTO.setSearchCode(searchCode);
        qtSearchVehicleDTO.setPageNo(page);
        qtSearchVehicleDTO.setPageSize(20);
        Gson gson = new Gson();
        String data = gson.toJson(qtSearchVehicleDTO);
        ServerApiUtils.sendToServer(data, "002023", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    SpListDTO spListDTO = responseGson.fromJson(data, SpListDTO.class);
                    int pageTotal=spListDTO.getPageTotal();

                    List<DisabilityDescrDTO> disabilityDescrDTOs= spListDTO.getDisabList();
                    List<MaimGradeData> maimGradeDatas=new ArrayList<>();

                    for (int i = 0; i < disabilityDescrDTOs.size(); i++) {
                        DisabilityDescrDTO disabilityDescrDTO=disabilityDescrDTOs.get(i);
                        MaimGradeData maimGradeData=new MaimGradeData(taskNo,disabilityDescrDTO.getId(),disabilityDescrDTO.getDisabilityGradeId(),disabilityDescrDTO.getDisabilityCode(),disabilityDescrDTO.getDisabilityDescr(),disabilityDescrDTO.getDisabilityScale());
                        maimGradeDatas.add(maimGradeData);
                    }
                    mCallBack.getDataSuccess(maimGradeDatas,pageTotal);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mCallBack.getDataFailed();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public static void requestHospitalData(String dealLocalCode, String searchName, int pageNo, int pageSize, String flag, final RCallBack rCallBack){
        QtSearchHosptialDTO qtSearchHosptialDTO = new QtSearchHosptialDTO();
        qtSearchHosptialDTO.setDealLocalCode(dealLocalCode);
        qtSearchHosptialDTO.setHospitalName(searchName);
        qtSearchHosptialDTO.setPageNo(pageNo);
        qtSearchHosptialDTO.setPageSize(pageSize);
        qtSearchHosptialDTO.setFlag(flag);
        Gson gson = new Gson();
        String data = gson.toJson(qtSearchHosptialDTO);
        sendToServer(data, "002016", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {

                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    SpListDTO spListDTO = responseGson.fromJson(data, SpListDTO.class);
                    int pageTotal=spListDTO.getPageTotal();
                    List<HosptialDTO> hosptialList = spListDTO.getHosptialList();
                    List<DictKEYValueDTO> dictList = spListDTO.getDictList();
//                    JsonUtil.saveHospitalData(hosptialList, dictList);
                    List<HospitalData> hospitalDatas=new ArrayList<>();
                    if (hosptialList.size()>0)
                    Log.i("name",hosptialList.get(0).getHospitalName());
                    for (int i = 0; i < hosptialList.size(); i++) {
                        HosptialDTO hospitalDTO=hosptialList.get(i);
                        hospitalDatas.add(new HospitalData(hospitalDTO.getHospitalId(),hospitalDTO.getHospitalName(),hospitalDTO.getHospitalTypeCode(),hospitalDTO.getHospitalLevel(),hospitalDTO.getHospitalAddress(),hospitalDTO.getHospitalTel(),hospitalDTO.getHospitalProperty(),hospitalDTO.getHospitalCode()));
                    }
                    rCallBack.getDataSuccess(hospitalDatas,pageTotal);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                rCallBack.getDataFailed();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    public static void sendToServer(String data,String requestCode,String url,Callback.CommonCallback<String> callback){
        Request request = new Request();
        request.setRequestCode(requestCode);
        request.setData(data);
        Gson gson = new Gson();
        NetOperaterUtil.getResponse(url,gson.toJson(request),callback);

    }
}
