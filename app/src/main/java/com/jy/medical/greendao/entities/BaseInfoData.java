package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/23.
 */

@Entity
public class BaseInfoData {
    @Id
    Long id;
    private String taskNo;
    private String address;
    private String time;
    private String detailInfo;
    private String remark;
    private String completeStatus;
    private String commitFlag;

    public BaseInfoData(String taskNo, String address, String time, String detailInfo, String remark, String completeStatus, String commitFlag) {
        this.taskNo = taskNo;
        this.address = address;
        this.time = time;
        this.detailInfo = detailInfo;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 825014444)
    public BaseInfoData(Long id, String taskNo, String address, String time, String detailInfo, String remark, String completeStatus, String commitFlag) {
        this.id = id;
        this.taskNo = taskNo;
        this.address = address;
        this.time = time;
        this.detailInfo = detailInfo;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 44955454)
    public BaseInfoData() {
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetailInfo() {
        return this.detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
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
