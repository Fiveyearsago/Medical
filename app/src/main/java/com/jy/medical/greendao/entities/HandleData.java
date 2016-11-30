package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by songran on 16/11/23.
 */

@Entity
public class HandleData {
    @Id
    Long id;
    private String taskNo;
    private String handleName;
    private String handleTime;
    private String handleResult;
    private String remark;
    private String completeStatus;
    private String commitFlag;

    public HandleData(String taskNo, String handleName, String handleTime, String handleResult, String remark, String completeStatus, String commitFlag) {
        this.taskNo = taskNo;
        this.handleName = handleName;
        this.handleTime = handleTime;
        this.handleResult = handleResult;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 723195394)
    public HandleData(Long id, String taskNo, String handleName, String handleTime, String handleResult, String remark, String completeStatus,
            String commitFlag) {
        this.id = id;
        this.taskNo = taskNo;
        this.handleName = handleName;
        this.handleTime = handleTime;
        this.handleResult = handleResult;
        this.remark = remark;
        this.completeStatus = completeStatus;
        this.commitFlag = commitFlag;
    }

    @Generated(hash = 1556909182)
    public HandleData() {
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

    public String getHandleName() {
        return this.handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public String getHandleTime() {
        return this.handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getHandleResult() {
        return this.handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
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
