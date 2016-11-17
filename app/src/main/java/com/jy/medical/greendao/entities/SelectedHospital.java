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
    private String hospitalName;
    private String departmentId;
    private String departmentName;

    public SelectedHospital(String taskNo, String hospitalId, String hospitalName, String departmentId, String departmentName) {
        this.taskNo = taskNo;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public SelectedHospital(String taskNo, String hospitalId) {
        this.taskNo = taskNo;
        this.hospitalId = hospitalId;
    }

    @Generated(hash = 805868994)
    public SelectedHospital(Long id, String taskNo, String hospitalId, String hospitalName, String departmentId,
            String departmentName) {
        this.id = id;
        this.taskNo = taskNo;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
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

    public String getHospitalName() {
        return this.hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
