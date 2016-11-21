package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/18.
 */
@Entity
public class NursingData {
    @Id
    Long id;
    private String taskNo;
    private String name;
    private String days;
    private String fee;
    private String idKey;
    private String idValue;
    private String idTypeCode;

    public NursingData(String taskNo, String name, String days, String fee, String idKey, String idValue, String idTypeCode) {
        this.taskNo = taskNo;
        this.name = name;
        this.days = days;
        this.fee = fee;
        this.idKey = idKey;
        this.idValue = idValue;
        this.idTypeCode = idTypeCode;
    }

    @Generated(hash = 1956309850)
    public NursingData(Long id, String taskNo, String name, String days, String fee, String idKey, String idValue,
            String idTypeCode) {
        this.id = id;
        this.taskNo = taskNo;
        this.name = name;
        this.days = days;
        this.fee = fee;
        this.idKey = idKey;
        this.idValue = idValue;
        this.idTypeCode = idTypeCode;
    }

    @Generated(hash = 1336002585)
    public NursingData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return this.days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getFee() {
        return this.fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getIdKey() {
        return this.idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getIdValue() {
        return this.idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public String getIdTypeCode() {
        return this.idTypeCode;
    }

    public void setIdTypeCode(String idTypeCode) {
        this.idTypeCode = idTypeCode;
    }

    public String getTaskNo() {
        return this.taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }
}
