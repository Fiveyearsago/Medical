package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by songran on 16/11/23.
 */

@Entity
public class DeathData {
    @Id
    Long id;
    private String taskNo;
    private String deathReason;
    private String participation;
    private String deathAddress;
    private String deathTime;
    private String remark;
    private String completeStatus;
    private String commitFlag;

    public DeathData(String taskNo, String deathReason, String participation, String deathAddress, String deathTime, String remark, String completeStatus, String commitFlag) {
        this.taskNo = taskNo;
        this.deathReason = deathReason;
        this.participation = participation;
        this.deathAddress = deathAddress;
        this.deathTime = deathTime;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 2060784804)
    public DeathData(Long id, String taskNo, String deathReason, String participation, String deathAddress, String deathTime, String remark, String completeStatus, String commitFlag) {
        this.id = id;
        this.taskNo = taskNo;
        this.deathReason = deathReason;
        this.participation = participation;
        this.deathAddress = deathAddress;
        this.deathTime = deathTime;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 1660184554)
    public DeathData() {
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

    public String getDeathReason() {
        return this.deathReason;
    }

    public void setDeathReason(String deathReason) {
        this.deathReason = deathReason;
    }

    public String getParticipation() {
        return this.participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }

    public String getDeathAddress() {
        return this.deathAddress;
    }

    public void setDeathAddress(String deathAddress) {
        this.deathAddress = deathAddress;
    }

    public String getDeathTime() {
        return this.deathTime;
    }

    public void setDeathTime(String deathTime) {
        this.deathTime = deathTime;
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
