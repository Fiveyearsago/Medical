package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by songran on 16/11/23.
 */

@Entity
public class SupporterData {
    @Id
    Long id;
    private String taskNo;
    private String payCoefficient;
    private String maintenanceFee;
    private String remark;
    private String completeStatus;
    private String commitFlag;

    public SupporterData(String taskNo, String payCoefficient, String maintenanceFee, String remark, String completeStatus, String commitFlag) {
        this.taskNo = taskNo;
        this.payCoefficient = payCoefficient;
        this.maintenanceFee = maintenanceFee;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 446410181)
    public SupporterData(Long id, String taskNo, String payCoefficient, String maintenanceFee, String remark, String completeStatus,
            String commitFlag) {
        this.id = id;
        this.taskNo = taskNo;
        this.payCoefficient = payCoefficient;
        this.maintenanceFee = maintenanceFee;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 747329520)
    public SupporterData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskNo() {
        return this.taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getPayCoefficient() {
        return this.payCoefficient;
    }

    public void setPayCoefficient(String payCoefficient) {
        this.payCoefficient = payCoefficient;
    }

    public String getMaintenanceFee() {
        return this.maintenanceFee;
    }

    public void setMaintenanceFee(String maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompleteStatus() {
        return this.completeStatus;
    }

    public void setCompleteStatus(String completeStatus) {
        this.completeStatus = completeStatus;
    }

    public String getCommitFlag() {
        return this.commitFlag;
    }

    public void setCommitFlag(String commitFlag) {
        this.commitFlag = commitFlag;
    }
    
}
