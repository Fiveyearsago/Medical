package com.jy.medical.util;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.MedicalVisit;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.greendao.entities.SelectedDiagnose;
import com.jy.medical.greendao.entities.SelectedHospital;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.CategoryDataManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.MedicalVisitManager;
import com.jy.medical.greendao.manager.NursingDataManager;
import com.jy.medical.greendao.manager.SelectedDiagnoseManager;
import com.jy.medical.greendao.manager.SelectedHospitalManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.mobile.dto.DisabilityDescrDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.dto.NursePersonDTO;
import com.jy.mobile.request.QTInspectAccidentInfoDTO;
import com.jy.mobile.request.QTInspectHospitalInfoDTO;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songran on 16/11/24.
 */

public class CommitUtil {
    public interface CommitCallBack {
        void commitSuccess();

        void commitFailed();
    }

    public static void commitBaseInfo(final Context context, String taskNo, final CommitCallBack commitCallBack){
        final BaseInfoDataManager baseInfoDataManager = DaoUtils.getBaseInfoDataInstance();
        ContactManager contactManager=DaoUtils.getContactInstance();
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        List<ContactData> contactDataList = new ArrayList<>();
        List<TaskPhoto> pictureList = new ArrayList<>();
        final BaseInfoData baseInfoData = baseInfoDataManager.getData(taskNo);
        if (baseInfoData == null) {
            Toast toast= Toast.makeText(context, "请完善必填信息", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
        if (baseInfoData.getAddress().equals("") || baseInfoData.getTime().equals("") ||baseInfoData.getDetailInfo().equals("") ||  baseInfoData.getCompleteStatus().equals("")) {
            Toast toast= Toast.makeText(context, "请完善必填信息", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        contactDataList=contactManager.selectAllContact(taskNo);
        if (pictureList.size() == 0 || contactDataList.size() == 0) {
            Toast toast= Toast.makeText(context, "请完善必填信息", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
        QTInspectAccidentInfoDTO qtInspectAccidentInfoDTO = new QTInspectAccidentInfoDTO();
        qtInspectAccidentInfoDTO.setTaskNo(taskNo);
        qtInspectAccidentInfoDTO.setAddress(baseInfoData.getAddress());
        qtInspectAccidentInfoDTO.setContactPerson("宋冉");
        qtInspectAccidentInfoDTO.setContactTel("18612235095");
        qtInspectAccidentInfoDTO.setRemark(baseInfoData.getRemark());
        qtInspectAccidentInfoDTO.setAccidentDate(baseInfoData.getTime());
        qtInspectAccidentInfoDTO.setUserCode("0131002498");
        Gson gson = new Gson();
        String data = gson.toJson(qtInspectAccidentInfoDTO);
        ServerApiUtils.sendToServer(data, "002005", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    baseInfoData.setCommitFlag("1");
                    baseInfoDataManager.insertSingleData(baseInfoData);
                    commitCallBack.commitSuccess();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                commitCallBack.commitFailed();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public static void commitMedical(final Context context, String taskNo, final CommitCallBack commitCallBack) {
        List<SelectedHospital> selectedHospitals = new ArrayList<>();
        List<NursingData> nursingDataList = new ArrayList<>();
        List<SelectedDiagnose> selectedDiagnoseList = new ArrayList<>();
        List<TaskPhoto> pictureList = new ArrayList<>();
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        SelectedHospitalManager selectedHospitalManager = DaoUtils.getSelectedHospitaInstance();
        SelectedDiagnoseManager selectedDiagnoseManager = DaoUtils.getSelectedDiagnoseInstance();
        NursingDataManager nursingDataManager = DaoUtils.getNursingDataInstance();
        final MedicalVisitManager medicalVisitManager = DaoUtils.getMedicalVisitInstance();
        final MedicalVisit medicalVisit = medicalVisitManager.getData(taskNo);
        if (medicalVisit == null) {
            Toast toast= Toast.makeText(context, "请完善必填信息", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
        if (medicalVisit.getMedicalFee().equals("") || medicalVisit.getCompleteStatus().equals("")) {
            Toast toast= Toast.makeText(context, "请完善必填信息", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
        selectedHospitals = selectedHospitalManager.getDataList(taskNo);
        selectedDiagnoseList = selectedDiagnoseManager.getDataList(taskNo);
        nursingDataList = nursingDataManager.getDataList(taskNo);
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        if (selectedHospitals.size() == 0 || selectedDiagnoseList.size() == 0 || nursingDataList.size() == 0 || pictureList.size() == 0) {
            Toast toast= Toast.makeText(context, "请完善必填信息", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
        QTInspectHospitalInfoDTO qtInspectHospitalInfoDTO = new QTInspectHospitalInfoDTO();
        qtInspectHospitalInfoDTO.setTaskNo(taskNo);
        qtInspectHospitalInfoDTO.setFeePass(Double.parseDouble(medicalVisit.getMedicalFee()));
        qtInspectHospitalInfoDTO.setFinishFlag(medicalVisit.getCompleteStatus());
        qtInspectHospitalInfoDTO.setRemark(medicalVisit.getRemark());
        qtInspectHospitalInfoDTO.setUserCode("0131002498");
        HosptialDTO hosptialDTO = new HosptialDTO();
        SelectedHospital selectedHospital = selectedHospitals.get(0);
        hosptialDTO.setHospitalProperty(selectedHospital.getHospitalName());
        hosptialDTO.setHospitalDepartment(selectedHospital.getDepartmentId());
        hosptialDTO.setHospitalId(selectedHospital.getHospitalId());
        qtInspectHospitalInfoDTO.setHospital(hosptialDTO);
        List<DisabilityDescrDTO> dList = new ArrayList<>();
        for (int i = 0; i < selectedDiagnoseList.size(); i++) {
            SelectedDiagnose selectDiagnose = selectedDiagnoseList.get(i);
            DisabilityDescrDTO d = new DisabilityDescrDTO();
            d.setId(selectDiagnose.getDiagnoseId());
            d.setDisabilityCode(selectDiagnose.getDiagnoseCode());
            d.setDisabilityDescr(selectDiagnose.getDiagnoseName());
            d.setOperation(selectDiagnose.getTreatmentMode());
            dList.add(d);
        }
        qtInspectHospitalInfoDTO.setDisabList(dList);

        List<NursePersonDTO> nList = new ArrayList<>();
        for (int i = 0; i < nursingDataList.size(); i++) {
            NursePersonDTO n = new NursePersonDTO();
            NursingData nursingData = nursingDataList.get(i);
            n.setNurseDayCount(Integer.parseInt(nursingData.getDays()));
            n.setNurseName(nursingData.getName());
            n.setNurseIndustryName(nursingData.getIdValue());
            n.setNurseIndustryCode(nursingData.getIdKey());
            n.setNurseDailyReceipts(Double.parseDouble(nursingData.getFee()));
            nList.add(n);

        }
        qtInspectHospitalInfoDTO.setNurseList(nList);
        Gson gson = new Gson();
        String data = gson.toJson(qtInspectHospitalInfoDTO);
        ServerApiUtils.sendToServer(data, "002007", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                Gson responseGson = new Gson();
                Response response = responseGson.fromJson(result, Response.class);
                if (response != null && "1".equals(response.getResponseCode())) {
                    String data = response.getData();
                    Log.i("ResponseCode", response.getResponseCode());
                    //标记已提交
                    medicalVisit.setCommitFlag("1");
                    medicalVisitManager.insertSingleData(medicalVisit);
                    commitCallBack.commitSuccess();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                commitCallBack.commitFailed();
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
