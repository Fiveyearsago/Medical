package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/12/6.
 */
@Entity
public class MaimGradeData {
    @Id
   Long id;
    String taskNo;
    String dId;
    String disabilityGradeId;
    String disabilityCode;
    String disabilityDescr;
    String disabilityScale;

    public MaimGradeData(String taskNo, String dId, String disabilityGradeId, String disabilityCode, String disabilityDescr, String disabilityScale) {
        this.taskNo = taskNo;
        this.dId = dId;
        this.disabilityGradeId = disabilityGradeId;
        this.disabilityCode = disabilityCode;
        this.disabilityDescr = disabilityDescr;
        this.disabilityScale = disabilityScale;
    }

    @Generated(hash = 31735165)
    public MaimGradeData(Long id, String taskNo, String dId, String disabilityGradeId, String disabilityCode, String disabilityDescr,
            String disabilityScale) {
        this.id = id;
        this.taskNo = taskNo;
        this.dId = dId;
        this.disabilityGradeId = disabilityGradeId;
        this.disabilityCode = disabilityCode;
        this.disabilityDescr = disabilityDescr;
        this.disabilityScale = disabilityScale;
    }

    @Generated(hash = 1805765542)
    public MaimGradeData() {
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

    public String getDId() {
        return this.dId;
    }

    public void setDId(String dId) {
        this.dId = dId;
    }

    public String getDisabilityGradeId() {
        return this.disabilityGradeId;
    }

    public void setDisabilityGradeId(String disabilityGradeId) {
        this.disabilityGradeId = disabilityGradeId;
    }

    public String getDisabilityCode() {
        return this.disabilityCode;
    }

    public void setDisabilityCode(String disabilityCode) {
        this.disabilityCode = disabilityCode;
    }

    public String getDisabilityDescr() {
        return this.disabilityDescr;
    }

    public void setDisabilityDescr(String disabilityDescr) {
        this.disabilityDescr = disabilityDescr;
    }

    public String getDisabilityScale() {
        return this.disabilityScale;
    }

    public void setDisabilityScale(String disabilityScale) {
        this.disabilityScale = disabilityScale;
    }
}
