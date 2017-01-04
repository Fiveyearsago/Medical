package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 17/1/3.
 */

@Entity
public class Inquire {
    @Id
    Long id;
    private String name;
    private String phoneNum;
    private String taskNo;
    private String peopleIdentity;
    private String peopleIdentityValue;

    public Inquire(String name, String phoneNum, String taskNo, String peopleIdentity, String peopleIdentityValue) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.taskNo = taskNo;
        this.peopleIdentity = peopleIdentity;
        this.peopleIdentityValue = peopleIdentityValue;
    }

    @Generated(hash = 1134560215)
    public Inquire(Long id, String name, String phoneNum, String taskNo, String peopleIdentity,
                   String peopleIdentityValue) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.taskNo = taskNo;
        this.peopleIdentity = peopleIdentity;
        this.peopleIdentityValue = peopleIdentityValue;
    }

    @Generated(hash = 1995995278)
    public Inquire() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getPeopleIdentity() {
        return peopleIdentity;
    }

    public void setPeopleIdentity(String peopleIdentity) {
        this.peopleIdentity = peopleIdentity;
    }

    public String getPeopleIdentityValue() {
        return peopleIdentityValue;
    }

    public void setPeopleIdentityValue(String peopleIdentityValue) {
        this.peopleIdentityValue = peopleIdentityValue;
    }
}
