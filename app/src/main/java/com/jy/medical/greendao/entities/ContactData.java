package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by songran on 16/11/8.
 */
@Entity
public class ContactData {
    @Id
    Long id;
    private String name;
    private String phoneNum;
    private String taskNo;

    public ContactData(String taskNo, String name, String phoneNum) {
        this.taskNo = taskNo;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    @Generated(hash = 900186384)
    public ContactData(Long id, String name, String phoneNum, String taskNo) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.taskNo = taskNo;
    }

    @Generated(hash = 471005222)
    public ContactData() {
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
