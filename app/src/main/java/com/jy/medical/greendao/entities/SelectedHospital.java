package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/16.
 */
@Entity
public class SelectedHospital {
    @Id
    Long id;
    private  String taskNo;
    private String hospitalId;

    public SelectedHospital(String taskNo, String hospitalId) {
        this.taskNo = taskNo;
        this.hospitalId = hospitalId;
    }

    @Generated(hash = 503556855)
    public SelectedHospital(Long id, String taskNo, String hospitalId) {
        this.id = id;
        this.taskNo = taskNo;
        this.hospitalId = hospitalId;
    }

    @Generated(hash = 1690898615)
    public SelectedHospital() {
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

    public String getHospitalId() {
        return this.hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
