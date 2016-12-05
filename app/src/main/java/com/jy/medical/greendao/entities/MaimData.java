package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/12/3.
 */
@Entity
public class MaimData {
    @Id
    Long id;
    private String taskNo;
    private String approvalDepartmentKey;
    private String approvalDepartmentValue;
    private String approvalPerson;
    private String payCoefficient;
    private String describe;

    private String remark;
    private String completeStatus;
    private String commitFlag;

    public MaimData(String taskNo, String approvalDepartmentKey, String approvalDepartmentValue, String approvalPerson, String payCoefficient,String describe,String remark, String completeStatus, String commitFlag) {
        this.taskNo = taskNo;
        this.approvalDepartmentKey = approvalDepartmentKey;
        this.approvalDepartmentValue = approvalDepartmentValue;
        this.approvalPerson = approvalPerson;
        this.payCoefficient = payCoefficient;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.describe = describe;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 1579999497)
    public MaimData(Long id, String taskNo, String approvalDepartmentKey, String approvalDepartmentValue, String approvalPerson, String payCoefficient, String describe, String remark, String completeStatus,
            String commitFlag) {
        this.id = id;
        this.taskNo = taskNo;
        this.approvalDepartmentKey = approvalDepartmentKey;
        this.approvalDepartmentValue = approvalDepartmentValue;
        this.approvalPerson = approvalPerson;
        this.payCoefficient = payCoefficient;
        this.describe = describe;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 1550904902)
    public MaimData() {
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

    public String getApprovalDepartmentKey() {
        return this.approvalDepartmentKey;
    }

    public void setApprovalDepartmentKey(String approvalDepartmentKey) {
        this.approvalDepartmentKey = approvalDepartmentKey;
    }

    public String getApprovalDepartmentValue() {
        return this.approvalDepartmentValue;
    }

    public void setApprovalDepartmentValue(String approvalDepartmentValue) {
        this.approvalDepartmentValue = approvalDepartmentValue;
    }

    public String getApprovalPerson() {
        return this.approvalPerson;
    }

    public void setApprovalPerson(String approvalPerson) {
        this.approvalPerson = approvalPerson;
    }

    public String getPayCoefficient() {
        return this.payCoefficient;
    }

    public void setPayCoefficient(String payCoefficient) {
        this.payCoefficient = payCoefficient;
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
