package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/11.
 */
@Entity
public class HospitalData {

    /**
     * hospitalId : 16187BF7227B4744AC46C12404C363DF
     * hospitalName : 吉林市龙潭区金珠卫生院
     * hospitalTypeCode : 医院机构
     * hospitalLevel : 01
     * hospitalAddress : 吉林市龙潭区105乡道
     * hospitalTel : 0432-63011042
     * hospitalProperty : 01
     * hospitalCode : 220203-0014
     */
    @Id
    Long id;
    private String hospitalId;
    private String hospitalName;
    private String hospitalTypeCode;
    private String hospitalLevel;
    private String hospitalAddress;
    private String hospitalTel;
    private String hospitalProperty;
    private String hospitalCode;

    public HospitalData(String hospitalId, String hospitalName, String hospitalTypeCode, String hospitalLevel, String hospitalAddress, String hospitalTel, String hospitalProperty, String hospitalCode) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalTypeCode = hospitalTypeCode;
        this.hospitalLevel = hospitalLevel;
        this.hospitalAddress = hospitalAddress;
        this.hospitalTel = hospitalTel;
        this.hospitalProperty = hospitalProperty;
        this.hospitalCode = hospitalCode;
    }

    @Generated(hash = 897417289)
    public HospitalData(Long id, String hospitalId, String hospitalName, String hospitalTypeCode, String hospitalLevel, String hospitalAddress, String hospitalTel, String hospitalProperty,
            String hospitalCode) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalTypeCode = hospitalTypeCode;
        this.hospitalLevel = hospitalLevel;
        this.hospitalAddress = hospitalAddress;
        this.hospitalTel = hospitalTel;
        this.hospitalProperty = hospitalProperty;
        this.hospitalCode = hospitalCode;
    }

    @Generated(hash = 1481852890)
    public HospitalData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalTypeCode() {
        return hospitalTypeCode;
    }

    public void setHospitalTypeCode(String hospitalTypeCode) {
        this.hospitalTypeCode = hospitalTypeCode;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalTel() {
        return hospitalTel;
    }

    public void setHospitalTel(String hospitalTel) {
        this.hospitalTel = hospitalTel;
    }

    public String getHospitalProperty() {
        return hospitalProperty;
    }

    public void setHospitalProperty(String hospitalProperty) {
        this.hospitalProperty = hospitalProperty;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
}
