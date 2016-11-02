package com.jy.medical.entities;

/**
 * Created by songran on 16/11/2.
 */

public  class TaskBean {
    private String id;
    private String claimId;
    private String taskType;
    private String taskState;
    private String taskName;
    private String injureName;
    private String injureId;
    private String dispatchDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
