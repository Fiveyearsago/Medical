package com.jy.medical.controller;

import com.jy.medical.greendao.entities.ClaimBeanData;
import com.jy.medical.greendao.entities.TaskBeanData;
import com.jy.medical.greendao.manager.ClaimManager;
import com.jy.medical.greendao.manager.TaskManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.mobile.dto.ClaimDTO;
import com.jy.mobile.dto.FlowDTO;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songran on 16/11/4.
 */

public class JsonToBean {
    public static ClaimManager claimManager=DaoUtils.getClaimInstance();
    public static TaskManager taskManager=DaoUtils.getTaskInstance();
    public static void ClaimDTOToBean(@NotNull List<ClaimDTO> claimDTOList) {
        List<ClaimBeanData> claimBearList = new ArrayList<>();
        for (int i = 0; i < claimDTOList.size(); i++) {
            ClaimDTO claimDTO = claimDTOList.get(i);
            String claimId = claimDTO.getClaimId();
            String reportNo = claimDTO.getReportNo();
            String reportDate = claimDTO.getReportDate();
            String insuredName = claimDTO.getInsuredName();
            String plateNo = claimDTO.getPlateNo();
            String dangerDate = claimDTO.getDangerDate();
            String mobilePhone = claimDTO.getMobilePhone();
            String companyId = claimDTO.getCompanyId();
            String companyName = claimDTO.getCompanyName();
            String bPolicyNo = claimDTO.getBPolicyNo();
            String fPolicyNo = claimDTO.getFPolicyNo();
            String createDate = claimDTO.getCreateDate();
            String lockFlag = claimDTO.getLockFlag();
            ClaimBeanData claimBeanData = new ClaimBeanData(null,claimId, reportNo, reportDate, insuredName, plateNo, dangerDate, mobilePhone, companyId, companyName, bPolicyNo, fPolicyNo, createDate,
                    lockFlag);
            claimBearList.add(claimBeanData);
            if (claimDTO.getTaskList() != null) {
                List<TaskBeanData> taskList = new ArrayList<>();

                for (int j = 0; j < claimDTO.getTaskList().size(); j++) {
                    FlowDTO flowDTO = claimDTO.getTaskList().get(j);
                    String taskNo = flowDTO.getTaskNo();
                    String taskType = flowDTO.getTaskType();
                    String taskState = flowDTO.getTaskState();
                    String taskName = flowDTO.getTaskName();
                    String injureName = flowDTO.getInjureName();
                    String injureId = flowDTO.getInjureId();
                    String dispatchDate = flowDTO.getDispatchDate();
                    //保存到数据库

                    taskList.add(new TaskBeanData(taskNo, claimId, taskType,
                            taskState, taskName, injureName, injureId,
                            dispatchDate,"0"));

                }
                taskManager.insertData(taskList);
            }


        }
//        DataController.insertClaimBean(claimBearList);

        claimManager.insertData(claimBearList);

    }
}
