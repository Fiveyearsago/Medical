package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/2.
 */
@Entity
public class TaskBeanData {
    @Id
    private Long id;

    private String taskNo;
    private String claimId;
    private String taskType;
    private String taskState;
    private String taskName;
    private String injureName;
    private String injureId;
    private String dispatchDate;
    private String commitFlag;
    private String isDoingFlag;
    @Generated(hash = 705498071)
    public TaskBeanData(Long id, String taskNo, String claimId, String taskType, String taskState, String taskName, String injureName, String injureId, String dispatchDate, String commitFlag,
            String isDoingFlag) {
        this.id = id;
        this.taskNo = taskNo;
        this.claimId = claimId;
        this.taskType = taskType;
        this.taskState = taskState;
        this.taskName = taskName;
        this.injureName = injureName;
        this.injureId = injureId;
        this.dispatchDate = dispatchDate;
        this.commitFlag = commitFlag;
        this.isDoingFlag = isDoingFlag;
    }



    public TaskBeanData(String taskNo, String claimId, String taskType, String taskState, String taskName, String injureName, String injureId, String dispatchDate, String commitFlag,String isDoingFlag) {
        this.taskNo = taskNo;
        this.claimId = claimId;
        this.taskType = taskType;
        this.taskState = taskState;
        this.taskName = taskName;
        this.injureName = injureName;
        this.injureId = injureId;
        this.dispatchDate = dispatchDate;
        this.commitFlag = commitFlag;
        this.isDoingFlag = isDoingFlag;
    }

    @Generated(hash = 740133766)
    public TaskBeanData() {
    }
    public String getCommitFlag() {
        return commitFlag;
    }

    public void setCommitFlag(String commitFlag) {
        this.commitFlag = commitFlag;
    }
    public String getTaskNo() {
        return taskNo;
    }

    public void settTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getInjureName() {
        return injureName;
    }

    public void setInjureName(String injureName) {
        this.injureName = injureName;
    }

    public String getInjureId() {
        return injureId;
    }

    public void setInjureId(String injureId) {
        this.injureId = injureId;
    }

    public String getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }



    public String getIsDoingFlag() {
        return this.isDoingFlag;
    }



    public void setIsDoingFlag(String isDoingFlag) {
        this.isDoingFlag = isDoingFlag;
    }
}
