package com.jy.medical.util;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.jy.ah.bus.data.Response;
import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.DeathData;
import com.jy.medical.greendao.entities.DelayData;
import com.jy.medical.greendao.entities.EarningData;
import com.jy.medical.greendao.entities.HandleData;
import com.jy.medical.greendao.entities.HouseholdData;
import com.jy.medical.greendao.entities.Inquire;
import com.jy.medical.greendao.entities.MaimData;
import com.jy.medical.greendao.entities.MaimGradeData;
import com.jy.medical.greendao.entities.MedicalVisit;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.greendao.entities.SelectedDiagnose;
import com.jy.medical.greendao.entities.SelectedHospital;
import com.jy.medical.greendao.entities.SupporterData;
import com.jy.medical.greendao.entities.SupporterPerson;
import com.jy.medical.greendao.entities.TaskBeanData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.CategoryDataManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.DeathDataManager;
import com.jy.medical.greendao.manager.DelayDataManager;
import com.jy.medical.greendao.manager.EarningDataManager;
import com.jy.medical.greendao.manager.HandleDataManager;
import com.jy.medical.greendao.manager.HouseholdDataManager;
import com.jy.medical.greendao.manager.InquireManager;
import com.jy.medical.greendao.manager.MaimDataManager;
import com.jy.medical.greendao.manager.MaimGradeDataManager;
import com.jy.medical.greendao.manager.MedicalVisitManager;
import com.jy.medical.greendao.manager.NursingDataManager;
import com.jy.medical.greendao.manager.SelectedDiagnoseManager;
import com.jy.medical.greendao.manager.SelectedHospitalManager;
import com.jy.medical.greendao.manager.SupporterDataManager;
import com.jy.medical.greendao.manager.SupporterPersonManager;
import com.jy.medical.greendao.manager.TaskManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.mobile.dto.DisabilityDescrDTO;
import com.jy.mobile.dto.HosptialDTO;
import com.jy.mobile.dto.InspectDependentItemDTO;
import com.jy.mobile.dto.NursePersonDTO;
import com.jy.mobile.request.QTDisabilityDTO;
import com.jy.mobile.request.QTInspectAccidentInfoDTO;
import com.jy.mobile.request.QTInspectDeadDTO;
import com.jy.mobile.request.QTInspectDependentInfoDTO;
import com.jy.mobile.request.QTInspectHospitalInfoDTO;
import com.jy.mobile.request.QTInspectHouseholdDTO;
import com.jy.mobile.request.QTInspectInComeDTO;
import com.jy.mobile.request.QtAccidentAddressDTO;
import com.jy.mobile.request.QtCompenstateDTO;

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

    public static void commitSupporterInfo(Context context, final String taskNo, final CommitCallBack commitCallBack) {
        final SupporterDataManager supporterDataManager = DaoUtils.getSupporterDataInstance();
        final SupporterData supporterData = supporterDataManager.getData(taskNo);
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        SupporterPersonManager supporterPersonManager = DaoUtils.getSupporterPersonInstance();
        List<TaskPhoto> pictureList = new ArrayList<>();
        List<SupporterPerson> supporterPersonList = new ArrayList<>();
        supporterPersonList = supporterPersonManager.selectAllContact(taskNo);
        if (supporterPersonList.size() == 0) {
            ToastUtil.showToast(context, "请添加被扶养人");
            return;
        }
        if (supporterData == null) {
            ToastUtil.showToast(context, "请完善必填信息");
            return;
        }
        if (supporterData.getPayCoefficient().equals("")) {
            ToastUtil.showToast(context, "请填写赔偿系数");
            return;
        }
        if (Double.parseDouble(supporterData.getPayCoefficient()) >= 100) {
            ToastUtil.showToast(context, "赔偿系数不能大于或等于100");
            return;
        }
        if (supporterData.getMaintenanceFee().equals("")) {
            ToastUtil.showToast(context, "请填写抚养费金额");
            return;
        }
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        if (pictureList.size() == 0) {
            ToastUtil.showToast(context, "请添加照片");
            return;
        }
        if (supporterData.getCompleteStatus().equals("")) {
            ToastUtil.showToast(context, "请填写完成情况");
            return;
        }
        final List<SupporterPerson> finalSupporterPersonList = supporterPersonList;
        AlertView mAlertView = new AlertView("提示", "提交后不能进行修改，是否提交？", "否", new String[]{"是"}, null, context, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position1) {
                if (position1 == 0) {
                    //提交信息
                    QTInspectDependentInfoDTO qtDisabilityDTO = new QTInspectDependentInfoDTO();
                    qtDisabilityDTO.setTaskNo(supporterData.getTaskNo());
                    qtDisabilityDTO.setFinishFlag(supporterData.getCompleteStatus());
                    qtDisabilityDTO.setUserCode("0131002498");
                    qtDisabilityDTO.setRemark(supporterData.getRemark());
                    qtDisabilityDTO.setDependentSum(Double.parseDouble(supporterData.getMaintenanceFee()));
                    qtDisabilityDTO.setPercent(supporterData.getPayCoefficient());
                    List<InspectDependentItemDTO> dependentItemList = new ArrayList<InspectDependentItemDTO>();
                    for (int i = 0; i < finalSupporterPersonList.size(); i++) {
                        SupporterPerson s = finalSupporterPersonList.get(i);
                        InspectDependentItemDTO dto = new InspectDependentItemDTO();
                        dto.setDependentName(s.getName());
                        dto.setDependentBirthday(s.getBornTime());
                        dto.setDependentAddr(s.getAddress());
                        dto.setDependentCount(Integer.parseInt(s.getNum()));
                        dto.setDependentYearTotal(Double.parseDouble(s.getYears()));
                        dto.setDependentAge(Integer.parseInt(s.getAge()));
                        dto.setVictimRelationCode(s.getRelationKey());
                        dto.setVictimRelationName(s.getRelationValue());
                        dto.setResidenceNatureCode(s.getHouseKindKey());
                        dto.setResidenceNatureName(s.getHouseKindValue());
                    }
                    Gson gson = new Gson();
                    String data = gson.toJson(qtDisabilityDTO);
                    ServerApiUtils.sendToServer(data, "002012", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("result", result);
                            Gson responseGson = new Gson();
                            Response response = responseGson.fromJson(result, Response.class);
                            if (response != null && "1".equals(response.getResponseCode())) {
                                String data = response.getData();
                                Log.i("ResponseCode", response.getResponseCode());
                                setCommitFlag(taskNo);
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


        }).setCancelable(true).setOnDismissListener(null);
        mAlertView.show();

    }

    public static void commitDeathInfo(Context context, final String taskNo, final CommitCallBack commitCallBack) {
        final DeathDataManager deathDataManager = DaoUtils.getDeathDataInstance();
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        final DeathData deathData = deathDataManager.getData(taskNo);
        List<TaskPhoto> pictureList = new ArrayList<>();
        if (deathData == null) {
            ToastUtil.showToast(context, "请完善必填信息");
            return;
        }
        if (deathData.getDeathTime().equals("")) {
            ToastUtil.showToast(context, "请填写死亡时间");
            return;
        }
        if (deathData.getDeathAddress().equals("")) {
            ToastUtil.showToast(context, "请填写死亡地点");
            return;
        }
        if (deathData.getDeathReason().equals("")) {
            ToastUtil.showToast(context, "请填写死亡原因");
            return;
        }
        if (deathData.getParticipation().equals("")) {
            ToastUtil.showToast(context, "请填写参与度");
            return;
        }
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        if (pictureList.size() == 0) {
            ToastUtil.showToast(context, "请添加照片");
            return;
        }
        if (deathData.getCompleteStatus().equals("")) {
            ToastUtil.showToast(context, "请填写完成情况");
            return;
        }
        AlertView mAlertView = new AlertView("提示", "提交后不能进行修改，是否提交？", "否", new String[]{"是"}, null, context, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position1) {
                if (position1 == 0) {
                    //提交信息
                    QTInspectDeadDTO qtDisabilityDTO = new QTInspectDeadDTO();
                    qtDisabilityDTO.setTaskNo(deathData.getTaskNo());
                    qtDisabilityDTO.setFinishFlag(deathData.getCompleteStatus());
                    qtDisabilityDTO.setUserCode("0131002498");
                    qtDisabilityDTO.setRemark(deathData.getRemark());
                    qtDisabilityDTO.setDeadTime(TimeUtil.getTimeWithSecondsString(deathData.getDeathTime()));
                    qtDisabilityDTO.setAddress(deathData.getDeathAddress());
                    qtDisabilityDTO.setReason(deathData.getDeathReason());
                    qtDisabilityDTO.setDutyPercent(deathData.getParticipation());
                    Gson gson = new Gson();
                    String data = gson.toJson(qtDisabilityDTO);
                    ServerApiUtils.sendToServer(data, "002011", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("result", result);
                            Gson responseGson = new Gson();
                            Response response = responseGson.fromJson(result, Response.class);
                            if (response != null && "1".equals(response.getResponseCode())) {
                                String data = response.getData();
                                Log.i("ResponseCode", response.getResponseCode());
                                setCommitFlag(taskNo);
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


        }).setCancelable(true).setOnDismissListener(null);
        mAlertView.show();

    }

    public static void commitMaimInfo(Context context, final String taskNo, final CommitCallBack commitCallBack) {
        final MaimDataManager maimDataManager = DaoUtils.getMaimDataInstance();
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        MaimGradeDataManager maimGradeDataManager = DaoUtils.getMaimGradeDataInstance();
        final List<MaimGradeData> maimGradeDataList;
        List<TaskPhoto> pictureList = new ArrayList<>();
        final MaimData maimData = maimDataManager.getData(taskNo);
        if (maimData == null) {
            ToastUtil.showToast(context, "请完善必填信息");
            return;
        }
        if (maimData.getApprovalDepartmentValue().equals("")) {
            ToastUtil.showToast(context, "请填写鉴定机构");
            return;
        }
        if (maimData.getApprovalPerson().equals("")) {
            ToastUtil.showToast(context, "请填写鉴定人");
            return;
        }
        if (maimData.getPayCoefficient().equals("")) {
            ToastUtil.showToast(context, "请填写赔偿系数");
            return;
        }
        if (Double.parseDouble(maimData.getPayCoefficient()) >= 100) {
            ToastUtil.showToast(context, "赔偿系数不能大于或等于100");
            return;
        }
        if (maimData.getDescribe().equals("")) {
            ToastUtil.showToast(context, "请填写伤残描述");
            return;
        }
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        if (pictureList.size() == 0) {
            ToastUtil.showToast(context, "请添加照片");
            return;
        }
        maimGradeDataList = maimGradeDataManager.getDataList(taskNo);
        if (maimGradeDataList.size() == 0) {
            ToastUtil.showToast(context, "请添加伤残等级");
            return;
        }
        if (maimData.getCompleteStatus().equals("")) {
            ToastUtil.showToast(context, "请填写完成情况");
            return;
        }

        AlertView mAlertView = new AlertView("提示", "提交后不能进行修改，是否提交？", "否", new String[]{"是"}, null, context, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position1) {
                if (position1 == 0) {
                    //提交信息
                    QTDisabilityDTO qtDisabilityDTO = new QTDisabilityDTO();
                    qtDisabilityDTO.setTaskNo(maimData.getTaskNo());
                    qtDisabilityDTO.setFinishFlag(maimData.getCompleteStatus());
                    qtDisabilityDTO.setUserCode("0131002498");
                    qtDisabilityDTO.setRemark(maimData.getRemark());
                    qtDisabilityDTO.setDisabilityPersonName(maimData.getApprovalPerson());
                    qtDisabilityDTO.setDisabilityRatioFormula(maimData.getPayCoefficient());
                    qtDisabilityDTO.setDisabilityIdentifyOrgId(maimData.getApprovalDepartmentKey());
                    qtDisabilityDTO.setDisabilityIdentifyOrgName(maimData.getApprovalDepartmentValue());
                    qtDisabilityDTO.setIdentifyRemarkInfo(maimData.getDescribe());
                    List<DisabilityDescrDTO> disabilityItemList = new ArrayList<DisabilityDescrDTO>();
                    for (int i = 0; i < maimGradeDataList.size(); i++) {
                        MaimGradeData m = maimGradeDataList.get(i);
                        DisabilityDescrDTO disabilityDescrDTO = new DisabilityDescrDTO();
                        disabilityDescrDTO.setDisabilityCode(m.getDisabilityCode());
                        disabilityDescrDTO.setDisabilityDescr(m.getDisabilityDescr());
                        disabilityDescrDTO.setDisabilityGradeId(m.getDisabilityGradeId());
                        disabilityItemList.add(disabilityDescrDTO);
                    }
                    qtDisabilityDTO.setDisabilityItemList(disabilityItemList);
                    Gson gson = new Gson();
                    String data = gson.toJson(qtDisabilityDTO);
                    ServerApiUtils.sendToServer(data, "002013", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("result", result);
                            Gson responseGson = new Gson();
                            Response response = responseGson.fromJson(result, Response.class);
                            if (response != null && "1".equals(response.getResponseCode())) {
                                String data = response.getData();
                                Log.i("ResponseCode", response.getResponseCode());
                                setCommitFlag(taskNo);
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


        }).setCancelable(true).setOnDismissListener(null);
        mAlertView.show();

    }

    public static void commitHouseHoldsInfo(Context context, final String taskNo, final CommitCallBack commitCallBack) {
        final HouseholdDataManager householdDataManager = DaoUtils.getHouseholdDataInstance();
        InquireManager inquireManager = DaoUtils.getInquireManagerInstance();
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        List<Inquire> contactDataList = new ArrayList<>();
        List<TaskPhoto> pictureList = new ArrayList<>();
        final HouseholdData householdData = householdDataManager.getData(taskNo);
        if (householdData == null) {
            ToastUtil.showToast(context, "请完善必填信息");
            return;
        }
        if (householdData.getHouseholdCityValue().equals("")) {
            ToastUtil.showToast(context, "请填写户籍地信息");
            return;
        }
        if (householdData.getHouseholdTypeValue().equals("")) {
            ToastUtil.showToast(context, "请填写户籍类型");
            return;
        }
        if (householdData.getHouseholdLiveValue().equals("")) {
            ToastUtil.showToast(context, "请填写是否户籍地居住");
            return;
        }
        if (householdData.getFatherLiveValue().equals("")) {
            ToastUtil.showToast(context, "请填写父亲是否健在");
            return;
        }
        if (householdData.getMotherLiveValue().equals("")) {
            ToastUtil.showToast(context, "请填写母亲是否健在");
            return;
        }
        if (householdData.getChildrenNum().equals("")) {
            ToastUtil.showToast(context, "请填写子女人数");
            return;
        }
        if (householdData.getBrotherNum().equals("")) {
            ToastUtil.showToast(context, "请填写兄弟姐妹人数");
            return;
        }
        if (householdData.getAddress().equals("")) {
            ToastUtil.showToast(context, "请填写居住地址");
            return;
        }
        if (householdData.getStartTime().equals("")) {
            ToastUtil.showToast(context, "请填写居住开始时间");
            return;
        }
        if (householdData.getEndTime().equals("")) {
            ToastUtil.showToast(context, "请填写居住结束时间");
            return;
        }
        if (householdData.getYears().equals("")) {
            ToastUtil.showToast(context, "请填写连续居住年限");
            return;
        }
        contactDataList = inquireManager.selectAllContact(taskNo);
        if (contactDataList.size() == 0) {
            ToastUtil.showToast(context, "请填写联系被询问人");
            return;
        }
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        if (pictureList.size() == 0) {
            ToastUtil.showToast(context, "请添加照片");
            return;
        }
        if (householdData.getCompleteValue().equals("")) {
            ToastUtil.showToast(context, "请填写完成情况");
            return;
        }
        final List<Inquire> finalContactDataList = contactDataList;
        AlertView mAlertView = new AlertView("提示", "提交后不能进行修改，是否提交？", "否", new String[]{"是"}, null, context, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position1) {
                if (position1 == 0) {
                    //提交信息
                    QTInspectHouseholdDTO qtInspectHouseholdDTO = new QTInspectHouseholdDTO();
                    qtInspectHouseholdDTO.setTaskNo(householdData.getTaskNo());
                    qtInspectHouseholdDTO.setFinishFlag(householdData.getCompleteKey());
                    qtInspectHouseholdDTO.setUserCode("0131002498");
                    qtInspectHouseholdDTO.setRemark(householdData.getRemark());
                    qtInspectHouseholdDTO.setAddress(householdData.getAddress());
                    qtInspectHouseholdDTO.setHousehold(householdData.getHouseholdCityValue());
                    qtInspectHouseholdDTO.setHouseholdType(householdData.getHouseholdTypeKey());
                    qtInspectHouseholdDTO.setFatherExt(householdData.getFatherLiveKey());
                    qtInspectHouseholdDTO.setMatherExt(householdData.getMotherLiveKey());
                    qtInspectHouseholdDTO.setSonCount(Integer.parseInt(householdData.getChildrenNum()));
                    qtInspectHouseholdDTO.setBratherCount(Integer.parseInt(householdData.getBrotherNum()));

                    qtInspectHouseholdDTO.setInsider(finalContactDataList.get(0).getName());
                    qtInspectHouseholdDTO.setInsiderPhone(finalContactDataList.get(0).getPhoneNum());
                    qtInspectHouseholdDTO.setInsiderIdentity(finalContactDataList.get(0).getPeopleIdentity());
                    qtInspectHouseholdDTO.setAddressBeTrue(householdData.getHouseholdLiveKey());
                    qtInspectHouseholdDTO.setLiveStartDate(householdData.getStartTime());
                    qtInspectHouseholdDTO.setLiveEndDate(householdData.getEndTime());


                    Gson gson = new Gson();
                    String data = gson.toJson(qtInspectHouseholdDTO);
                    ServerApiUtils.sendToServer(data, "002010", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("result", result);
                            Gson responseGson = new Gson();
                            Response response = responseGson.fromJson(result, Response.class);
                            if (response != null && "1".equals(response.getResponseCode())) {
                                String data = response.getData();
                                Log.i("ResponseCode", response.getResponseCode());
                                setCommitFlag(taskNo);
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


        }).setCancelable(true).setOnDismissListener(null);
        mAlertView.show();

    }

    public static void commitDelayInfo(Context context, final String taskNo, final CommitCallBack commitCallBack) {
        final DelayDataManager delayDataManager = DaoUtils.getDelayDataInstance();
        ContactManager contactManager = DaoUtils.getContactInstance();
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        List<ContactData> contactDataList = new ArrayList<>();
        List<TaskPhoto> pictureList = new ArrayList<>();
        final DelayData delayData = delayDataManager.getData(taskNo);
        if (delayData == null) {
            ToastUtil.showToast(context, "请完善必填信息");
            return;
        }
        if (delayData.getJobStatusValue().equals("")) {
            ToastUtil.showToast(context, "请填写在职情况");
            return;
        }
        if (delayData.getRestDays().equals("")) {
            ToastUtil.showToast(context, "请填写误工天数");
            return;
        }
        if (delayData.getMoneyReduce().equals("")) {
            ToastUtil.showToast(context, "请填写误工费");
            return;
        }

        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        if (pictureList.size() == 0) {
            ToastUtil.showToast(context, "请添加照片");
            return;
        }
        if (delayData.getCompleteStatusValue().equals("")) {
            ToastUtil.showToast(context, "请填写完成情况");
            return;
        }
        if (delayData.getJobStatusValue().equals("在职")) {
            if (delayData.getIndustryValue().equals("")) {
                ToastUtil.showToast(context, "请填写行业信息");
                return;
            }
            if (delayData.getCompanyName().equals("")) {
                ToastUtil.showToast(context, "请填写单位名称");
                return;
            }
            if (delayData.getCompanyAddress().equals("")) {
                ToastUtil.showToast(context, "请填写单位地址");
                return;
            }

            if (delayData.getEntryTime().equals("")) {
                ToastUtil.showToast(context, "请填写入职时间");
                return;
            }
            if (delayData.getAgreementValue().equals("")) {
                ToastUtil.showToast(context, "请填写劳务合同信息");
                return;
            }
            if (delayData.getSocialValue().equals("")) {
                ToastUtil.showToast(context, "请填写社保信息");
                return;
            }
            if (delayData.getIncomeFormValue().equals("")) {
                ToastUtil.showToast(context, "请填写收入发放形式");
                return;
            }
            if (delayData.getMonthlyIncome().equals("")) {
                ToastUtil.showToast(context, "请填月收入");
                return;
            }
            contactDataList = contactManager.selectAllContact(taskNo);
            if (contactDataList.size() == 0) {
                ToastUtil.showToast(context, "请填写联系人");
                return;
            }
        } else if (delayData.getJobStatusValue().equals("离职")) {
            if (delayData.getLeaveTime().equals("")) {
                ToastUtil.showToast(context, "请填写离职时间");
                return;
            }
        }

        AlertView mAlertView = new AlertView("提示", "提交后不能进行修改，是否提交？", "否", new String[]{"是"}, null, context, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position1) {
                if (position1 == 0) {
                    //提交信息
                    QTInspectInComeDTO qtInspectInComeDTO = new QTInspectInComeDTO();
                    qtInspectInComeDTO.setTaskNo(delayData.getTaskNo());
                    qtInspectInComeDTO.setFinishFlag(delayData.getCompleteStatusKey());
                    qtInspectInComeDTO.setUserCode("0131002498");
                    qtInspectInComeDTO.setActualDays(Integer.parseInt(delayData.getRestDays()));
                    qtInspectInComeDTO.setReduceSalary(Double.parseDouble(delayData.getMoneyReduce()));
                    qtInspectInComeDTO.setIncumbency(delayData.getJobStatusKey());
                    qtInspectInComeDTO.setRemark(delayData.getRemark());


                    if (delayData.getJobStatusKey().equals("0")) {
                        qtInspectInComeDTO.setOnWorkDate(delayData.getEntryTime());
                        qtInspectInComeDTO.setWorkUnit(delayData.getCompanyName());
                        qtInspectInComeDTO.setWorkUnitAddress(delayData.getCompanyAddress());
                        qtInspectInComeDTO.setUnitContact("宋冉");
                        qtInspectInComeDTO.setUnitContactPhone("18612235095");
                        qtInspectInComeDTO.setWorkStation(delayData.getIndustryValue());
                        qtInspectInComeDTO.setContract(delayData.getAgreementKey());
                        qtInspectInComeDTO.setSocialSecurity(delayData.getSocialKey());
                        qtInspectInComeDTO.setMonthSalary(Double.parseDouble(delayData.getMonthlyIncome()));
                        qtInspectInComeDTO.setIncomeWay(delayData.getIncomeFormKey());
                        qtInspectInComeDTO.setOffWorkDate("");
                    } else {
                        qtInspectInComeDTO.setOnWorkDate("");
                        qtInspectInComeDTO.setOffWorkDate(delayData.getLeaveTime());
                        qtInspectInComeDTO.setWorkUnit("");
                        qtInspectInComeDTO.setWorkUnitAddress("");
                        qtInspectInComeDTO.setUnitContact("");
                        qtInspectInComeDTO.setUnitContactPhone("");
                        qtInspectInComeDTO.setWorkStation("");
                        qtInspectInComeDTO.setContract("");
                        qtInspectInComeDTO.setSocialSecurity("");
                        qtInspectInComeDTO.setMonthSalary(0.0);
                        qtInspectInComeDTO.setIncomeWay("");
                    }


                    Gson gson = new Gson();
                    String data = gson.toJson(qtInspectInComeDTO);
                    ServerApiUtils.sendToServer(data, "002009", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("result", result);
                            Gson responseGson = new Gson();
                            Response response = responseGson.fromJson(result, Response.class);
                            if (response != null && "1".equals(response.getResponseCode())) {
                                String data = response.getData();
                                Log.i("ResponseCode", response.getResponseCode());
                                delayData.setCommitFlag("1");
                                delayDataManager.insertSingleData(delayData);
                                setCommitFlag(taskNo);
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


        }).setCancelable(true).setOnDismissListener(null);
        mAlertView.show();

    }

    public static void commitEarningInfo(Context context, final String taskNo, final CommitCallBack commitCallBack) {
        final EarningDataManager delayDataManager = DaoUtils.getEarningDataInstance();
        ContactManager contactManager = DaoUtils.getContactInstance();
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        List<ContactData> contactDataList = new ArrayList<>();
        List<TaskPhoto> pictureList = new ArrayList<>();
        final EarningData delayData = delayDataManager.getData(taskNo);
        if (delayData == null) {
            ToastUtil.showToast(context, "请完善必填信息");
            return;
        }
        if (delayData.getJobStatusValue().equals("")) {
            ToastUtil.showToast(context, "请填写在职情况");
            return;
        }

        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        if (pictureList.size() == 0) {
            ToastUtil.showToast(context, "请添加照片");
            return;
        }
        if (delayData.getCompleteStatusValue().equals("")) {
            ToastUtil.showToast(context, "请填写完成情况");
            return;
        }
        if (delayData.getJobStatusValue().equals("在职")) {
            if (delayData.getIndustryValue().equals("")) {
                ToastUtil.showToast(context, "请填写行业信息");
                return;
            }
            if (delayData.getCompanyName().equals("")) {
                ToastUtil.showToast(context, "请填写单位名称");
                return;
            }
            if (delayData.getCompanyAddress().equals("")) {
                ToastUtil.showToast(context, "请填写单位地址");
                return;
            }

            if (delayData.getEntryTime().equals("")) {
                ToastUtil.showToast(context, "请填写入职时间");
                return;
            }
            if (delayData.getAgreementValue().equals("")) {
                ToastUtil.showToast(context, "请填写劳务合同信息");
                return;
            }
            if (delayData.getSocialValue().equals("")) {
                ToastUtil.showToast(context, "请填写社保信息");
                return;
            }
            if (delayData.getIncomeFormValue().equals("")) {
                ToastUtil.showToast(context, "请填写收入发放形式");
                return;
            }
            if (delayData.getMonthlyIncome().equals("")) {
                ToastUtil.showToast(context, "请填月收入");
                return;
            }
            contactDataList = contactManager.selectAllContact(taskNo);
            if (contactDataList.size() == 0) {
                ToastUtil.showToast(context, "请填写联系人");
                return;
            }
        } else if (delayData.getJobStatusValue().equals("离职")) {
            if (delayData.getLeaveTime().equals("")) {
                ToastUtil.showToast(context, "请填写离职时间");
                return;
            }
        }

        AlertView mAlertView = new AlertView("提示", "提交后不能进行修改，是否提交？", "否", new String[]{"是"}, null, context, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position1) {
                if (position1 == 0) {
                    //提交信息
                    QTInspectInComeDTO qtInspectInComeDTO = new QTInspectInComeDTO();
                    qtInspectInComeDTO.setTaskNo(delayData.getTaskNo());
                    qtInspectInComeDTO.setFinishFlag(delayData.getCompleteStatusKey());
                    qtInspectInComeDTO.setUserCode("0131002498");
                    qtInspectInComeDTO.setIncumbency(delayData.getJobStatusKey());
                    qtInspectInComeDTO.setRemark(delayData.getRemark());


                    if (delayData.getJobStatusKey().equals("0")) {
                        qtInspectInComeDTO.setOnWorkDate(delayData.getEntryTime());
                        qtInspectInComeDTO.setWorkUnit(delayData.getCompanyName());
                        qtInspectInComeDTO.setWorkUnitAddress(delayData.getCompanyAddress());
                        qtInspectInComeDTO.setUnitContact("宋冉");
                        qtInspectInComeDTO.setUnitContactPhone("18612235095");
                        qtInspectInComeDTO.setWorkStation(delayData.getIndustryValue());
                        qtInspectInComeDTO.setContract(delayData.getAgreementKey());
                        qtInspectInComeDTO.setSocialSecurity(delayData.getSocialKey());
                        qtInspectInComeDTO.setMonthSalary(Double.parseDouble(delayData.getMonthlyIncome()));
                        qtInspectInComeDTO.setIncomeWay(delayData.getIncomeFormKey());
                        qtInspectInComeDTO.setOffWorkDate("");
                    } else {
                        qtInspectInComeDTO.setOnWorkDate("");
                        qtInspectInComeDTO.setOffWorkDate(delayData.getLeaveTime());
                        qtInspectInComeDTO.setWorkUnit("");
                        qtInspectInComeDTO.setWorkUnitAddress("");
                        qtInspectInComeDTO.setUnitContact("");
                        qtInspectInComeDTO.setUnitContactPhone("");
                        qtInspectInComeDTO.setWorkStation("");
                        qtInspectInComeDTO.setContract("");
                        qtInspectInComeDTO.setSocialSecurity("");
                        qtInspectInComeDTO.setMonthSalary(0.0);
                        qtInspectInComeDTO.setIncomeWay("");
                    }


                    Gson gson = new Gson();
                    String data = gson.toJson(qtInspectInComeDTO);
                    ServerApiUtils.sendToServer(data, "002008", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("result", result);
                            Gson responseGson = new Gson();
                            Response response = responseGson.fromJson(result, Response.class);
                            if (response != null && "1".equals(response.getResponseCode())) {
                                String data = response.getData();
                                Log.i("ResponseCode", response.getResponseCode());
                                delayData.setCommitFlag("1");
                                delayDataManager.insertSingleData(delayData);
                                setCommitFlag(taskNo);
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


        }).setCancelable(true).setOnDismissListener(null);
        mAlertView.show();

    }

    public static void commitHandleInfo(final Context context, final String taskNo, final CommitCallBack commitCallBack) {
        final HandleDataManager handleDataManager = DaoUtils.getHandleDataInstance();
        ContactManager contactManager = DaoUtils.getContactInstance();
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        List<ContactData> contactDataList = new ArrayList<>();
        List<TaskPhoto> pictureList = new ArrayList<>();
        final HandleData handleData = handleDataManager.getData(taskNo);
        if (handleData == null) {
            ToastUtil.showToast(context, "请完善必填信息");
            return;
        }

        if (handleData.getHandleName().equals("")) {
            ToastUtil.showToast(context, "请填写事故处理人");
            return;
        }
        if (handleData.getHandleTime().equals("")) {
            ToastUtil.showToast(context, "请填写处理时间");
            return;
        }
        contactDataList = contactManager.selectAllContact(taskNo);
        if (contactDataList.size() == 0) {
            ToastUtil.showToast(context, "请填写联系人");
            return;
        }
        if (handleData.getHandleResult().equals("")) {
            ToastUtil.showToast(context, "请填写处理结果");
            return;
        }
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        if (pictureList.size() == 0) {
            ToastUtil.showToast(context, "请选择照片");
            return;
        }
        if (handleData.getCompleteStatus().equals("")) {
            ToastUtil.showToast(context, "请填写完成情况");
            return;
        }
        AlertView mAlertView = new AlertView("提示", "提交后不能进行修改，是否提交？", "否", new String[]{"是"}, null, context, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position1) {
                if (position1 == 0) {
                    //提交信息
                    QTInspectAccidentInfoDTO qtInspectAccidentInfoDTO = new QTInspectAccidentInfoDTO();
                    qtInspectAccidentInfoDTO.setTaskNo(handleData.getTaskNo());
                    qtInspectAccidentInfoDTO.setAddress(handleData.getHandleResult());
                    qtInspectAccidentInfoDTO.setContactPerson("宋冉");
                    qtInspectAccidentInfoDTO.setContactTel("18612235095");
                    qtInspectAccidentInfoDTO.setRemark(handleData.getRemark());
                    qtInspectAccidentInfoDTO.setAccidentDate(handleData.getHandleTime());
                    qtInspectAccidentInfoDTO.setFinishFlag(handleData.getCompleteStatus());
                    qtInspectAccidentInfoDTO.setUserCode("0131002498");
                    Gson gson = new Gson();
                    String data = gson.toJson(qtInspectAccidentInfoDTO);
                    ServerApiUtils.sendToServer(data, "002006", PublicString.URL_IFC, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("result", result);
                            Gson responseGson = new Gson();
                            Response response = responseGson.fromJson(result, Response.class);
                            if (response != null && "1".equals(response.getResponseCode())) {
                                String data = response.getData();
                                Log.i("ResponseCode", response.getResponseCode());
                                handleData.setCommitFlag("1");
                                handleDataManager.insertSingleData(handleData);
                                setCommitFlag(taskNo);
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


        }).setCancelable(true).setOnDismissListener(null);
        mAlertView.show();


    }

    public static void commitBaseInfo(final Context context, final String taskNo, final CommitCallBack commitCallBack) {
        final BaseInfoDataManager baseInfoDataManager = DaoUtils.getBaseInfoDataInstance();
        ContactManager contactManager = DaoUtils.getContactInstance();
        TaskPhotoManager taskPhotoManager = DaoUtils.getTaskPhotoInstance();
        List<ContactData> contactDataList = new ArrayList<>();
        List<TaskPhoto> pictureList = new ArrayList<>();
        final BaseInfoData baseInfoData = baseInfoDataManager.getData(taskNo);
        if (baseInfoData == null) {
            ToastUtil.showToast(context, "请完善必填信息");
            return;
        }
        if (baseInfoData.getAddress().equals("")) {
            ToastUtil.showToast(context, "请填写事故地址");
            return;
        }
        if (baseInfoData.getTime().equals("")) {
            ToastUtil.showToast(context, "请填写事故时间");
            return;
        }
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        contactDataList = contactManager.selectAllContact(taskNo);
        if (contactDataList.size() == 0) {
            ToastUtil.showToast(context, "请填写联系人");
            return;
        }

        if (baseInfoData.getDetailInfo().equals("")) {
            ToastUtil.showToast(context, "请填写事故详细信息");
            return;
        }
        if (pictureList.size() == 0) {
            ToastUtil.showToast(context, "请选择照片");
            return;
        }
        if (baseInfoData.getCompleteStatus().equals("")) {
            ToastUtil.showToast(context, "请填写完成情况");
            return;
        }

        AlertView mAlertView = new AlertView("提示", "提交后不能进行修改，是否提交？", "否", new String[]{"是"}, null, context, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position1) {
                if (position1 == 0) {
                    //提交信息
                    QTInspectAccidentInfoDTO qtInspectAccidentInfoDTO = new QTInspectAccidentInfoDTO();
                    qtInspectAccidentInfoDTO.setTaskNo(taskNo);
                    qtInspectAccidentInfoDTO.setAddress(baseInfoData.getAddress());
                    qtInspectAccidentInfoDTO.setContactPerson("宋冉");
                    qtInspectAccidentInfoDTO.setContactTel("18612235095");
                    qtInspectAccidentInfoDTO.setRemark(baseInfoData.getRemark());
                    qtInspectAccidentInfoDTO.setAccidentDate(baseInfoData.getTime());
                    qtInspectAccidentInfoDTO.setFinishFlag(baseInfoData.getCompleteStatus());
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
                                setCommitFlag(taskNo);
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
        }).setCancelable(true).setOnDismissListener(null);
        mAlertView.show();

    }

    public static void commitMedical(final Context context, final String taskNo, final CommitCallBack commitCallBack) {
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
            ToastUtil.showToast(context, "请完善必填信息");
            return;
        }
        selectedHospitals = selectedHospitalManager.getDataList(taskNo);
        selectedDiagnoseList = selectedDiagnoseManager.getDataList(taskNo);
        nursingDataList = nursingDataManager.getDataList(taskNo);
        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
        if (selectedHospitals.size() == 0) {
            ToastUtil.showToast(context, "请填写医院信息");
            return;
        }
        if (selectedDiagnoseList.size() == 0) {
            ToastUtil.showToast(context, "请填写诊断信息");
            return;
        }
        if (nursingDataList.size() == 0) {
            ToastUtil.showToast(context, "请填写护理人信息");
            return;
        }
        if (medicalVisit.getMedicalFee().equals("")) {
            ToastUtil.showToast(context, "请填写医疗费");
            return;
        }
        if (pictureList.size() == 0) {
            ToastUtil.showToast(context, "请先选择图片");
            return;
        }
        if (medicalVisit.getCompleteStatus().equals("")) {
            ToastUtil.showToast(context, "请选择完成情况");
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
                    setCommitFlag(taskNo);
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

    public static void setCommitFlag(String taskNo) {
        TaskManager taskManager = DaoUtils.getTaskInstance();
        TaskBeanData taskBeanData = taskManager.getData(taskNo);
        if (taskBeanData != null) {
            taskManager.updateCommitFlag(taskNo, "1");
        }
    }
}
