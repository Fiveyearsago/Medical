package com.jy.medical.greendao.entities;

import java.io.Serializable;

/**
 * Created by songran on 16/10/14.
 */

public class PlatformData implements Serializable {
    private String peopleName;
    private String time;
    private String tag;
    private String reportNum;

    private String claimId;
    private String taskNo;
    private String phoneNum;
    private String taskType;
    private String commitFlag;
    private String isDoingFlag;

    public PlatformData(String claimId, String taskNo,String taskType, String peopleName, String time, String tag, String reportNum,  String phoneNum, String commitFlag,String isDoingFlag) {
        this.peopleName = peopleName;
        this.time = time;
        this.tag = tag;
        this.reportNum = reportNum;
        this.claimId = claimId;
        this.taskNo = taskNo;
        this.phoneNum = phoneNum;
        this.taskType = taskType;
        this.commitFlag = commitFlag;
        this.isDoingFlag = isDoingFlag;
    }

    public String getIsDoingFlag() {
        return isDoingFlag;
    }

    public void setIsDoingFlag(String isDoingFlag) {
        this.isDoingFlag = isDoingFlag;
    }

    public String getCommitFlag() {
        return commitFlag;
    }

    public void setCommitFlag(String commitFlag) {
        this.commitFlag = commitFlag;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getReportNum() {
        return reportNum;
    }

    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }
    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }
}
