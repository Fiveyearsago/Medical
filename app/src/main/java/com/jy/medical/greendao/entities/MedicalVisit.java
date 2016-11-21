package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/16.
 */
@Entity
public class MedicalVisit {
    @Id
    Long id;
    private String taskNo;
    private String medicalFee;
    private String remark;
    private String completeStatus;
    private String commitFlag;

    public MedicalVisit(String taskNo, String medicalFee, String remark, String completeStatus, String commitFlag) {
        this.taskNo = taskNo;
        this.medicalFee = medicalFee;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 866985587)
    public MedicalVisit(Long id, String taskNo, String medicalFee, String remark, String completeStatus,
            String commitFlag) {
        this.id = id;
        this.taskNo = taskNo;
        this.medicalFee = medicalFee;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 2047523780)
    public MedicalVisit() {
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

    public String getMedicalFee() {
        return this.medicalFee;
    }

    public void setMedicalFee(String medicalFee) {
        this.medicalFee = medicalFee;
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
