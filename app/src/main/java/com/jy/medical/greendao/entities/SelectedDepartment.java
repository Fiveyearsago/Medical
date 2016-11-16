package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/16.
 */
@Entity
public class SelectedDepartment {
    @Id
    Long id;
    private String taskNo;
    private String hospitalId;
    private  String departmentId;

    public SelectedDepartment(String taskNo, String hospitalId, String departmentId) {
        this.taskNo = taskNo;
        this.hospitalId = hospitalId;
        this.departmentId = departmentId;
    }

    @Generated(hash = 1633099072)
    public SelectedDepartment(Long id, String taskNo, String hospitalId,
            String departmentId) {
        this.id = id;
        this.taskNo = taskNo;
        this.hospitalId = hospitalId;
        this.departmentId = departmentId;
    }

    @Generated(hash = 1267794612)
    public SelectedDepartment() {
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

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
