package com.jy.medical.util;

import com.jy.medical.greendao.entities.CityData;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.HumanParts;
import com.jy.medical.greendao.entities.LawData;
import com.jy.medical.greendao.entities.MedicalDepartment;
import com.jy.medical.greendao.manager.CityDataManager;
import com.jy.medical.greendao.manager.HospitalDataManager;
import com.jy.medical.greendao.manager.HumanPartsManager;
import com.jy.medical.greendao.manager.MedicalDepartmentManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.mobile.dto.DictKEYValueDTO;
import com.jy.mobile.dto.HosptialDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songran on 16/11/10.
 */

public class JsonUtil {

    public static void saveCityData(String result) {
        List<CityData> list = new ArrayList<>();
        try {
//            JSONObject jsonObject = new JSONObject(result);
//            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                list.add(new CityData(object.get("aid").toString(),object.get("pid").toString(), object.get("code").toString(), object.get("name").toString(), object.get("level").toString()));
            }
            CityDataManager cityDataManager = DaoUtils.getCityDataInstance();
            cityDataManager.insertData(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static void saveHPData(String result) {
        List<HumanParts> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                list.add(new HumanParts(object.get("aid").toString(), object.get("code").toString(), object.get("name").toString()));
            }
            HumanPartsManager humanPartsManager = DaoUtils.getHumanPartsInstance();
            humanPartsManager.insertData(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static List<LawData> changeToList(String result) {
        List<LawData> list = new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("lawsList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                list.add(new LawData(object.get("lawId").toString(), object.get("lawShortName").toString(),object.optString("lawOrder").toString(), object.get("lawFullName").toString(), object.get("lawFullContent").toString(), object.get("createUserId").toString(), object.get("createDate").toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //保存下载医院信息
    public static List<HospitalData> saveHospitalData(List<HosptialDTO> hosptialList, List<DictKEYValueDTO> dictList) {
        List<HospitalData> hospitalDatas=new ArrayList<>();
        List<MedicalDepartment> medicalDepartments=new ArrayList<>();
        for (int i = 0; i < hosptialList.size(); i++) {
            HosptialDTO hospitalDTO=hosptialList.get(i);
            hospitalDatas.add(new HospitalData(hospitalDTO.getHospitalId(),hospitalDTO.getHospitalName(),hospitalDTO.getHospitalTypeCode(),hospitalDTO.getHospitalLevel(),hospitalDTO.getHospitalAddress(),hospitalDTO.getHospitalTel(),hospitalDTO.getHospitalProperty(),hospitalDTO.getHospitalCode()));
        }
        for (int i = 0; i < dictList.size(); i++) {
            DictKEYValueDTO dictDTO=dictList.get(i);
            medicalDepartments.add(new MedicalDepartment(dictDTO.getKey(),dictDTO.getValue()));
        }
        HospitalDataManager hospitalManager=DaoUtils.getHospitalInstance();
        MedicalDepartmentManager departmentManager=DaoUtils.getDepartmentInstance();
        hospitalManager.insertData(hospitalDatas);
        departmentManager.insertData(medicalDepartments);
        return hospitalDatas;
    }
}
