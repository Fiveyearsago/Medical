package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/12/15.
 */
@Entity
public class HouseholdData {
    @Id
    Long id;
    private String taskNo;
    private String householdCityKey;
    private String householdCityValue;
    private String householdTypeKey;
    private String householdTypeValue;
    private String householdLiveKey;
    private String householdLiveValue;
    private String fatherLiveKey;
    private String fatherLiveValue;
    private String motherLiveKey;
    private String motherLiveValue;
    private String childrenNum;
    private String brotherNum;
    private String address;
    private String startTime;
    private String endTime;
    private String years;
    private String completeKey;
    private String completeValue;
    private String remark;

    public HouseholdData(String remark, String taskNo, String householdCityKey, String householdCityValue, String householdTypeKey, String householdTypeValue, String householdLiveKey, String householdLiveValue, String fatherLiveKey, String fatherLiveValue, String motherLiveKey, String motherLiveValue, String childrenNum, String brotherNum, String address, String startTime, String endTime, String years, String completeKey, String completeValue) {
        this.remark = remark;
        this.taskNo = taskNo;
        this.householdCityKey = householdCityKey;
        this.householdCityValue = householdCityValue;
        this.householdTypeKey = householdTypeKey;
        this.householdTypeValue = householdTypeValue;
        this.householdLiveKey = householdLiveKey;
        this.householdLiveValue = householdLiveValue;
        this.fatherLiveKey = fatherLiveKey;
        this.fatherLiveValue = fatherLiveValue;
        this.motherLiveKey = motherLiveKey;
        this.motherLiveValue = motherLiveValue;
        this.childrenNum = childrenNum;
        this.brotherNum = brotherNum;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.years = years;
        this.completeKey = completeKey;
        this.completeValue = completeValue;
    }

    @Generated(hash = 55158529)
    public HouseholdData(Long id, String taskNo, String householdCityKey,
            String householdCityValue, String householdTypeKey,
            String householdTypeValue, String householdLiveKey,
            String householdLiveValue, String fatherLiveKey, String fatherLiveValue,
            String motherLiveKey, String motherLiveValue, String childrenNum,
            String brotherNum, String address, String startTime, String endTime,
            String years, String completeKey, String completeValue, String remark) {
        this.id = id;
        this.taskNo = taskNo;
        this.householdCityKey = householdCityKey;
        this.householdCityValue = householdCityValue;
        this.householdTypeKey = householdTypeKey;
        this.householdTypeValue = householdTypeValue;
        this.householdLiveKey = householdLiveKey;
        this.householdLiveValue = householdLiveValue;
        this.fatherLiveKey = fatherLiveKey;
        this.fatherLiveValue = fatherLiveValue;
        this.motherLiveKey = motherLiveKey;
        this.motherLiveValue = motherLiveValue;
        this.childrenNum = childrenNum;
        this.brotherNum = brotherNum;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.years = years;
        this.completeKey = completeKey;
        this.completeValue = completeValue;
        this.remark = remark;
    }
    @Generated(hash = 1445515779)
    public HouseholdData() {
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
    public String getHouseholdCityKey() {
        return this.householdCityKey;
    }
    public void setHouseholdCityKey(String householdCityKey) {
        this.householdCityKey = householdCityKey;
    }
    public String getHouseholdCityValue() {
        return this.householdCityValue;
    }
    public void setHouseholdCityValue(String householdCityValue) {
        this.householdCityValue = householdCityValue;
    }
    public String getHouseholdTypeKey() {
        return this.householdTypeKey;
    }
    public void setHouseholdTypeKey(String householdTypeKey) {
        this.householdTypeKey = householdTypeKey;
    }
    public String getHouseholdTypeValue() {
        return this.householdTypeValue;
    }
    public void setHouseholdTypeValue(String householdTypeValue) {
        this.householdTypeValue = householdTypeValue;
    }
    public String getHouseholdLiveKey() {
        return this.householdLiveKey;
    }
    public void setHouseholdLiveKey(String householdLiveKey) {
        this.householdLiveKey = householdLiveKey;
    }
    public String getHouseholdLiveValue() {
        return this.householdLiveValue;
    }
    public void setHouseholdLiveValue(String householdLiveValue) {
        this.householdLiveValue = householdLiveValue;
    }
    public String getFatherLiveKey() {
        return this.fatherLiveKey;
    }
    public void setFatherLiveKey(String fatherLiveKey) {
        this.fatherLiveKey = fatherLiveKey;
    }
    public String getFatherLiveValue() {
        return this.fatherLiveValue;
    }
    public void setFatherLiveValue(String fatherLiveValue) {
        this.fatherLiveValue = fatherLiveValue;
    }
    public String getMotherLiveKey() {
        return this.motherLiveKey;
    }
    public void setMotherLiveKey(String motherLiveKey) {
        this.motherLiveKey = motherLiveKey;
    }
    public String getMotherLiveValue() {
        return this.motherLiveValue;
    }
    public void setMotherLiveValue(String motherLiveValue) {
        this.motherLiveValue = motherLiveValue;
    }
    public String getChildrenNum() {
        return this.childrenNum;
    }
    public void setChildrenNum(String childrenNum) {
        this.childrenNum = childrenNum;
    }
    public String getBrotherNum() {
        return this.brotherNum;
    }
    public void setBrotherNum(String brotherNum) {
        this.brotherNum = brotherNum;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getYears() {
        return this.years;
    }
    public void setYears(String years) {
        this.years = years;
    }
    public String getCompleteKey() {
        return this.completeKey;
    }
    public void setCompleteKey(String completeKey) {
        this.completeKey = completeKey;
    }
    public String getCompleteValue() {
        return this.completeValue;
    }
    public void setCompleteValue(String completeValue) {
        this.completeValue = completeValue;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
