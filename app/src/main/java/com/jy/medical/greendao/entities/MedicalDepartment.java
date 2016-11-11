package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/11.
 */
@Entity
public class MedicalDepartment {

    /**
     * key : 5C5DB59156E942C4973E404D737AC91E
     * value : 医学影像科
     */
    @Id
    Long id;
    private String key;
    private String value;


    public MedicalDepartment(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Generated(hash = 898286383)
    public MedicalDepartment(Long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    @Generated(hash = 1306372074)
    public MedicalDepartment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
